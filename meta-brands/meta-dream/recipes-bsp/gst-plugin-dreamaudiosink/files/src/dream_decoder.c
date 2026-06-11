#ifdef HAVE_CONFIG_H
#include "config.h"
#endif

#define _DEFAULT_SOURCE   /* for swab() in <unistd.h> */
#define _XOPEN_SOURCE 700

#include "dream_decoder.h"
#include "dream_log.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libavformat/avio.h>
#include <libavutil/opt.h>
#include <libavutil/channel_layout.h>
#include <libavutil/samplefmt.h>
#include <libavutil/audio_fifo.h>
#include <libavutil/dict.h>
#include <libavutil/mem.h>
#include <libswresample/swresample.h>
#include <alsa/asoundlib.h>

#define DEC_DBG(...) dream_log("decoder", __VA_ARGS__)

#define SYNCWORD1 0xF872
#define SYNCWORD2 0x4E1F

enum IEC61937 {
    IEC61937_AC3  = 0x01,
    IEC61937_DTS1 = 0x0B,
    IEC61937_DTS2 = 0x0C,
    IEC61937_DTS3 = 0x0D,
    IEC61937_EAC3 = 0x15,
};

#define DIGITAL_RAW_PCM     0
#define DIGITAL_RAW_SPDIF   1
#define DIGITAL_RAW_HDMI    2

#define SPDIF_AC3_BUF_BYTES   6144
#define SPDIF_EAC3_BUF_BYTES  24576

struct DreamDecoder {
    const AVCodec    *codec;
    AVCodecContext   *codec_ctx;
    AVCodecParserContext *parser;   /* NULL for codecs without parser */
    SwrContext       *swr_ctx;
    AVFrame          *frame;
    AVPacket         *avpkt;

    int               codec_id;
    unsigned int      out_sample_rate;
    unsigned int      out_channels;

    uint16_t          spdif[SPDIF_EAC3_BUF_BYTES / 2];
    int               spdif_index;
    int               spdif_count;

    int64_t           last_pts;

    DreamDecoderOutputCallback cb;
    void                       *cb_user;

    /* AAC -> AC3 transcode path. All NULL when not transcoding. */
    int               transcode_aac_to_ac3;
    AVCodecContext   *enc_ctx;
    AVFrame          *enc_frame;
    AVPacket         *enc_pkt;
    AVAudioFifo      *enc_fifo;
    SwrContext       *enc_swr;
    int64_t           enc_next_pts;
    /* AML bitstream-mode sysfs state at start_aac_to_ac3_encoder() time;
     * restored to PCM-safe values in dream_decoder_free so the downstream
     * Live-TV path sees a clean PCM pipeline on channel switch. -1 = not
     * saved (transcode never engaged). */
    int               saved_digital_raw;
    int               saved_digital_codec;

    /* HBR passthrough state (TrueHD, DTS-HD MA). libavformat's spdif muxer
     * handles MAT wrapping (TrueHD) / repetition + start-code framing
     * (DTS-HD) — we feed raw AVPackets in, capture IEC61937 bytes via the
     * custom AVIOContext write callback, and emit at 192k/8ch S16 through
     * the existing PCM callback. dream_alsa opens hw at 192k/8ch and AML
     * kernel routes onto the HDMI HBR IEC60958 carrier. */
    int               hbr_mode;
    AVFormatContext  *spdif_fmt;
    AVIOContext      *spdif_avio;
    AVStream         *spdif_stream;
    int               hbr_header_written;
    uint8_t          *hbr_out_buf;          /* dynamic byte accumulator */
    size_t            hbr_out_size;
    size_t            hbr_out_cap;
    int               hbr_digital_codec;    /* 5=DTS-HD, 7=TrueHD */
};

#define HBR_AVIO_BUFSIZE   (128 * 1024)
#define HBR_OUT_INIT_CAP   (128 * 1024)

static int read_sysfs_int(const char *path)
{
    FILE *f = fopen(path, "r");
    if (!f) return -1;
    int v = -1;
    if (fscanf(f, "%d", &v) != 1) v = -1;
    fclose(f);
    return v;
}

static void write_sysfs_int(const char *path, int v)
{
    FILE *f = fopen(path, "w");
    if (!f) return;
    fprintf(f, "%d", v);
    fclose(f);
}

static int dream_get_digital_raw(void)
{
    int v = read_sysfs_int("/sys/class/audiodsp/digital_raw");
    return v < 0 ? DIGITAL_RAW_PCM : v;
}

static int dream_get_digital_codec(void)
{
    int v = read_sysfs_int("/sys/class/audiodsp/digital_codec");
    return v < 0 ? 0 : v;
}

static void dream_set_digital_codec(int v)
{
    if (dream_get_digital_codec() != v)
        write_sysfs_int("/sys/class/audiodsp/digital_codec", v);
}

/* Read one enigma2 setting key as a string into out (sized at out_sz).
 * Returns 1 if the key was found, 0 otherwise (out untouched on miss). */
static int read_enigma2_setting(const char *key, char *out, size_t out_sz)
{
    FILE *f = fopen("/etc/enigma2/settings", "r");
    if (!f) return 0;
    char line[256];
    const size_t klen = strlen(key);
    int hit = 0;
    while (fgets(line, sizeof(line), f)) {
        if (strncmp(line, key, klen) != 0) continue;
        if (line[klen] != '=') continue;
        const char *val = line + klen + 1;
        char *nl = strchr((char *)val, '\n'); if (nl) *nl = 0;
        snprintf(out, out_sz, "%s", val);
        hit = 1;
        break;
    }
    fclose(f);
    return hit;
}

/* === AAC / EAC3 -> AC3 transcode helpers ============================ */

/* Lightweight read of one enigma2 setting key without bringing in any
 * enigma2 headers. The plugin is plain C and runs in the GStreamer
 * thread; tsparser-side equivalent lives in lib/dvb/tsparser.cpp. */
static int read_aac_transcode_setting(void)
{
    char val[64];
    if (!read_enigma2_setting("config.av.transcodeaac", val, sizeof(val)))
        return 0;
    return strcmp(val, "force_ac3") == 0;
}

static int read_ac3plus_transcode_setting(void)
{
    char val[64];
    if (!read_enigma2_setting("config.av.transcodeac3plus", val, sizeof(val)))
        return 0;
    return strcmp(val, "force_ac3") == 0;
}

/* === HBR (TrueHD / DTS-HD MA) helpers ================================ */

/* Mirror lib/dvb/audiomanager: scan aud_cap for a codec line. If
 * require_192k is 0, just verify the codec is listed at all. If 1, also
 * require "192" in the line's freq portion — that's the EDID signal for
 * true HBR capability. TVs without an AVR typically list DTS-HD up to
 * 96 kHz only (HR / Express, fits standard 48k/2ch IEC958) and lack
 * TrueHD entirely; only AVRs advertise 192 kHz for those codecs. */
static int sink_has_codec(const char *name, int require_192k)
{
    FILE *f = fopen("/sys/class/amhdmitx/amhdmitx0/aud_cap", "r");
    if (!f) return 0;
    char line[256];
    int hit = 0;
    const size_t nlen = strlen(name);
    while (fgets(line, sizeof(line), f)) {
        const char *p = line;
        while (*p == ' ' || *p == '\t') ++p;
        if (strncmp(p, name, nlen) == 0 && (p[nlen] == ',' || p[nlen] == ' ')) {
            if (!require_192k || strstr(line, "192")) hit = 1;
            break;
        }
    }
    fclose(f);
    return hit;
}

/* True when this codec + user setting + sink HBR-cap combo wants HBR
 * passthrough. Defaults err on the side of NO HBR — without an AVR that
 * advertises 192 kHz for the codec, ALSA hw open at 192k/8ch fails
 * (-EINVAL) and the stream goes silent. Falling back to libavcodec PCM
 * decode + standard 48k/2ch output keeps audio audible on TV-only setups. */
static int codec_wants_hbr(int codec_id)
{
    char val[32] = {0};
    if (codec_id == AV_CODEC_ID_TRUEHD) {
        if (read_enigma2_setting("config.av.truehd", val, sizeof(val))
            && strcmp(val, "downmix") == 0)
            return 0;
        return sink_has_codec("TrueHD", 1) || sink_has_codec("MAT", 1);
    }
    if (codec_id == AV_CODEC_ID_DTS) {
        if (!read_enigma2_setting("config.av.dtshd", val, sizeof(val)))
            return 0;
        if (strcmp(val, "downmix") == 0) return 0;
        return sink_has_codec("DTS-HD", 1);
    }
    return 0;
}

static int hbr_write_cb(void *opaque, const uint8_t *buf, int buf_size)
{
    DreamDecoder *d = (DreamDecoder *)opaque;
    size_t need = d->hbr_out_size + (size_t)buf_size;
    if (need > d->hbr_out_cap) {
        size_t ncap = d->hbr_out_cap ? d->hbr_out_cap * 2 : HBR_OUT_INIT_CAP;
        while (ncap < need) ncap *= 2;
        uint8_t *nb = realloc(d->hbr_out_buf, ncap);
        if (!nb) return AVERROR(ENOMEM);
        d->hbr_out_buf = nb;
        d->hbr_out_cap = ncap;
    }
    memcpy(d->hbr_out_buf + d->hbr_out_size, buf, buf_size);
    d->hbr_out_size += buf_size;
    return buf_size;
}

static int start_hbr_muxer(DreamDecoder *d, int codec_id)
{
    enum AVCodecID strm;
    if (codec_id == AV_CODEC_ID_TRUEHD) {
        d->hbr_digital_codec = 7;
        strm = AV_CODEC_ID_TRUEHD;
    } else if (codec_id == AV_CODEC_ID_DTS) {
        d->hbr_digital_codec = 5;   /* DTS-HD */
        strm = AV_CODEC_ID_DTS;
    } else return -1;

    /* Save current sysfs so dream_decoder_free restores PCM-safe values. */
    d->saved_digital_raw   = dream_get_digital_raw();
    d->saved_digital_codec = dream_get_digital_codec();
    write_sysfs_int("/sys/class/audiodsp/digital_raw", DIGITAL_RAW_SPDIF);
    dream_set_digital_codec(d->hbr_digital_codec);
    {
        /* Audio spdif format mixer enum: 5=DTS-HD, 7=TrueHD. */
        snd_ctl_t *ctl = NULL;
        if (snd_ctl_open(&ctl, "hw:0", 0) == 0) {
            snd_ctl_elem_id_t    *id;  snd_ctl_elem_id_alloca(&id);
            snd_ctl_elem_value_t *val; snd_ctl_elem_value_alloca(&val);
            snd_ctl_elem_id_set_interface(id, SND_CTL_ELEM_IFACE_MIXER);
            snd_ctl_elem_id_set_name(id, "Audio spdif format");
            snd_ctl_elem_value_set_id(val, id);
            snd_ctl_elem_value_set_enumerated(val, 0, (unsigned int)d->hbr_digital_codec);
            snd_ctl_elem_write(ctl, val);
            snd_ctl_close(ctl);
        }
    }

    int rc = avformat_alloc_output_context2(&d->spdif_fmt, NULL, "spdif", NULL);
    if (rc < 0 || !d->spdif_fmt) {
        DEC_DBG("HBR: alloc spdif muxer: %d", rc);
        return -1;
    }
    d->spdif_stream = avformat_new_stream(d->spdif_fmt, NULL);
    if (!d->spdif_stream) {
        avformat_free_context(d->spdif_fmt); d->spdif_fmt = NULL;
        return -1;
    }
    d->spdif_stream->codecpar->codec_type   = AVMEDIA_TYPE_AUDIO;
    d->spdif_stream->codecpar->codec_id     = strm;
    d->spdif_stream->codecpar->sample_rate  = 48000;
    av_channel_layout_default(&d->spdif_stream->codecpar->ch_layout,
                              strm == AV_CODEC_ID_TRUEHD ? 8 : 6);

    uint8_t *avio_buf = av_malloc(HBR_AVIO_BUFSIZE);
    if (!avio_buf) {
        avformat_free_context(d->spdif_fmt); d->spdif_fmt = NULL;
        return -1;
    }
    d->spdif_avio = avio_alloc_context(avio_buf, HBR_AVIO_BUFSIZE, 1, d,
                                       NULL, hbr_write_cb, NULL);
    if (!d->spdif_avio) {
        av_free(avio_buf);
        avformat_free_context(d->spdif_fmt); d->spdif_fmt = NULL;
        return -1;
    }
    d->spdif_fmt->pb = d->spdif_avio;
    d->spdif_fmt->flags |= AVFMT_FLAG_CUSTOM_IO;

    AVDictionary *opts = NULL;
    if (strm == AV_CODEC_ID_DTS)
        av_dict_set(&opts, "dtshd_rate", "192000", 0);
    rc = avformat_write_header(d->spdif_fmt, &opts);
    av_dict_free(&opts);
    if (rc < 0) {
        DEC_DBG("HBR: avformat_write_header: %d", rc);
        return -1;
    }
    d->hbr_header_written = 1;
    d->hbr_out_cap = HBR_OUT_INIT_CAP;
    d->hbr_out_buf = malloc(d->hbr_out_cap);
    if (!d->hbr_out_buf) return -1;

    /* Tell the sink to open ALSA at HBR rate. */
    d->out_sample_rate = 192000;
    d->out_channels    = 8;

    DEC_DBG("HBR: start codec=%d (digital_codec=%d) 192k/8ch",
            codec_id, d->hbr_digital_codec);
    return 0;
}

static void stop_hbr_muxer(DreamDecoder *d)
{
    if (d->spdif_fmt && d->hbr_header_written) {
        av_write_trailer(d->spdif_fmt);
        d->hbr_header_written = 0;
    }
    if (d->spdif_avio) {
        uint8_t *live = d->spdif_avio->buffer;
        avio_context_free(&d->spdif_avio);
        if (live) av_free(live);
    }
    if (d->spdif_fmt) {
        avformat_free_context(d->spdif_fmt);
        d->spdif_fmt = NULL;
    }
    d->spdif_stream = NULL;
    free(d->hbr_out_buf);
    d->hbr_out_buf = NULL;
    d->hbr_out_size = 0;
    d->hbr_out_cap  = 0;
}

/* Push one TrueHD / DTS-HD frame through the spdif muxer; emit accumulated
 * IEC61937 bytes via the sink callback (at HBR rate). Output is in 16-byte
 * HBR ALSA frames (8 ch * S16). We only emit whole frames; any byte tail
 * stays in hbr_out_buf for the next push. */
static int hbr_push_packet(DreamDecoder *d, const uint8_t *data, int size)
{
    if (!d->spdif_fmt) return -1;

    AVPacket *pkt = av_packet_alloc();
    if (!pkt) return -1;
    if (av_new_packet(pkt, size) < 0) { av_packet_free(&pkt); return -1; }
    memcpy(pkt->data, data, size);
    pkt->stream_index = d->spdif_stream->index;
    pkt->pts = d->last_pts;
    pkt->dts = d->last_pts;

    int rc = av_write_frame(d->spdif_fmt, pkt);
    av_packet_free(&pkt);
    if (rc < 0) {
        char eb[128]; av_strerror(rc, eb, sizeof(eb));
        DEC_DBG("HBR: av_write_frame: %s", eb);
        return -1;
    }
    avio_flush(d->spdif_avio);

    /* Emit whole 16-byte HBR ALSA frames to the sink. */
    size_t frames = d->hbr_out_size / 16;
    if (frames == 0) return 0;
    size_t bytes  = frames * 16;
    d->cb(DREAM_DECODER_OUTPUT_IEC61937, 192000, 8,
          d->hbr_out_buf, bytes, d->last_pts, d->cb_user);

    /* Shift any tail down. */
    if (bytes < d->hbr_out_size)
        memmove(d->hbr_out_buf, d->hbr_out_buf + bytes,
                d->hbr_out_size - bytes);
    d->hbr_out_size -= bytes;
    return 0;
}

/* Build IEC61937 burst from an encoded AC3 packet and emit through the
 * callback. Mirrors build_spdif_ac3 but takes a plain AVPacket — needed
 * because the encoder produces fresh AVPackets, not the input AVPacket. */
static void emit_iec61937_ac3(DreamDecoder *d, const AVPacket *p)
{
    if (SPDIF_AC3_BUF_BYTES < p->size + 8) return;
    dream_set_digital_codec(2);

    uint16_t *out = d->spdif;
    out[0] = SYNCWORD1;
    out[1] = SYNCWORD2;
    out[2] = (uint16_t)(IEC61937_AC3 | (p->data[5] & 0x07) << 8);
    out[3] = (uint16_t)(p->size * 8);
    swab((const char *)p->data, (char *)(out + 4), p->size);
    memset(out + 4 + p->size / 2, 0, SPDIF_AC3_BUF_BYTES - 8 - p->size);

    d->cb(DREAM_DECODER_OUTPUT_IEC61937, d->out_sample_rate, d->out_channels,
          (const uint8_t *)out, SPDIF_AC3_BUF_BYTES, d->last_pts, d->cb_user);
}

static int drain_encoder_fifo(DreamDecoder *d, int flush)
{
    if (!d->enc_ctx || !d->enc_fifo) return -1;
    const int fsz = d->enc_ctx->frame_size;
    while (av_audio_fifo_size(d->enc_fifo) >= fsz ||
           (flush && av_audio_fifo_size(d->enc_fifo) > 0)) {
        int take = av_audio_fifo_size(d->enc_fifo);
        if (take > fsz) take = fsz;
        av_frame_unref(d->enc_frame);
        d->enc_frame->nb_samples  = fsz;  /* AC3 wants exact frame_size */
        d->enc_frame->format      = AV_SAMPLE_FMT_FLTP;
        d->enc_frame->sample_rate = 48000;
        av_channel_layout_copy(&d->enc_frame->ch_layout, &d->enc_ctx->ch_layout);
        if (av_frame_get_buffer(d->enc_frame, 0) < 0) return -1;
        if (av_audio_fifo_read(d->enc_fifo, (void **)d->enc_frame->data, take) < take)
            return -1;
        if (take < fsz) {
            const int pad_bytes = (fsz - take) * sizeof(float);
            memset(d->enc_frame->data[0] + take * sizeof(float), 0, pad_bytes);
            memset(d->enc_frame->data[1] + take * sizeof(float), 0, pad_bytes);
        }
        d->enc_frame->pts = d->enc_next_pts;
        d->enc_next_pts  += fsz;

        if (avcodec_send_frame(d->enc_ctx, d->enc_frame) < 0) {
            DEC_DBG("transcode: send_frame(AC3) failed");
            continue;
        }
        while (avcodec_receive_packet(d->enc_ctx, d->enc_pkt) == 0) {
            emit_iec61937_ac3(d, d->enc_pkt);
            av_packet_unref(d->enc_pkt);
        }
    }
    return 0;
}

static int feed_encoder_with_pcm(DreamDecoder *d, AVFrame *pcm)
{
    if (!d->enc_ctx || !d->enc_fifo) return -1;
    const int in_rate = pcm->sample_rate ? pcm->sample_rate : d->codec_ctx->sample_rate;
    AVChannelLayout in_chl;
    if (pcm->ch_layout.nb_channels) av_channel_layout_copy(&in_chl, &pcm->ch_layout);
    else if (d->codec_ctx->ch_layout.nb_channels)
        av_channel_layout_copy(&in_chl, &d->codec_ctx->ch_layout);
    else av_channel_layout_default(&in_chl, 2);

    AVChannelLayout out_chl;
    av_channel_layout_copy(&out_chl, &d->enc_ctx->ch_layout);

    if (!d->enc_swr) d->enc_swr = swr_alloc();
    if (!d->enc_swr) return -1;
    swr_close(d->enc_swr);
    av_opt_set_chlayout    (d->enc_swr, "in_chlayout",     &in_chl,     0);
    av_opt_set_int         (d->enc_swr, "in_sample_rate",   in_rate,    0);
    av_opt_set_sample_fmt  (d->enc_swr, "in_sample_fmt",    (enum AVSampleFormat)pcm->format, 0);
    av_opt_set_chlayout    (d->enc_swr, "out_chlayout",    &out_chl,    0);
    av_opt_set_int         (d->enc_swr, "out_sample_rate",  48000,      0);
    av_opt_set_sample_fmt  (d->enc_swr, "out_sample_fmt",   AV_SAMPLE_FMT_FLTP, 0);
    av_opt_set_double      (d->enc_swr, "rematrix_maxval",  1.0,        0);
    if (swr_init(d->enc_swr) < 0) return -1;

    const int out_max = swr_get_out_samples(d->enc_swr, pcm->nb_samples);
    if (out_max <= 0) return 0;

    uint8_t *out[2] = { NULL, NULL };
    int linesize = 0;
    if (av_samples_alloc(out, &linesize, 2, out_max, AV_SAMPLE_FMT_FLTP, 0) < 0) return -1;
    const uint8_t *src[AV_NUM_DATA_POINTERS] = { 0 };
    for (int i = 0; i < AV_NUM_DATA_POINTERS; ++i) src[i] = pcm->extended_data[i];
    int got = swr_convert(d->enc_swr, out, out_max, src, pcm->nb_samples);
    if (got > 0) av_audio_fifo_write(d->enc_fifo, (void **)out, got);
    av_freep(&out[0]);
    return drain_encoder_fifo(d, 0);
}

static int start_aac_to_ac3_encoder(DreamDecoder *d)
{
    const AVCodec *enc = avcodec_find_encoder(AV_CODEC_ID_AC3);
    if (!enc) { DEC_DBG("transcode: AC3 encoder not built into libavcodec"); return -1; }
    d->enc_ctx = avcodec_alloc_context3(enc);
    if (!d->enc_ctx) return -1;
    d->enc_ctx->sample_fmt  = AV_SAMPLE_FMT_FLTP;
    d->enc_ctx->sample_rate = 48000;
    d->enc_ctx->bit_rate    = 192000;
    av_channel_layout_default(&d->enc_ctx->ch_layout, 2);
    if (avcodec_open2(d->enc_ctx, enc, NULL) < 0) {
        DEC_DBG("transcode: avcodec_open2(AC3 enc) failed");
        avcodec_free_context(&d->enc_ctx);
        return -1;
    }
    d->enc_fifo  = av_audio_fifo_alloc(AV_SAMPLE_FMT_FLTP, 2,
                                       d->enc_ctx->frame_size > 0 ? d->enc_ctx->frame_size * 4 : 8192);
    d->enc_pkt   = av_packet_alloc();
    d->enc_frame = av_frame_alloc();
    if (!d->enc_fifo || !d->enc_pkt || !d->enc_frame) {
        DEC_DBG("transcode: encoder alloc failed");
        if (d->enc_fifo)  { av_audio_fifo_free(d->enc_fifo); d->enc_fifo  = NULL; }
        if (d->enc_pkt)   { av_packet_free(&d->enc_pkt); }
        if (d->enc_frame) { av_frame_free(&d->enc_frame); }
        avcodec_free_context(&d->enc_ctx);
        return -1;
    }
    /* Force AML kernel into AC3 bitstream mode for the duration of this
     * decoder instance. Save the original values so dream_decoder_free
     * can restore them — otherwise the next channel's PCM data plays
     * through a kernel still configured for AC3 bitstream and the AVR
     * gets silence/garbage. */
    d->saved_digital_raw   = dream_get_digital_raw();
    d->saved_digital_codec = dream_get_digital_codec();
    write_sysfs_int("/sys/class/audiodsp/digital_raw", DIGITAL_RAW_SPDIF);
    dream_set_digital_codec(2);  /* AC3 */
    d->enc_next_pts = 0;
    DEC_DBG("transcode: AAC -> AC3 active (frame_size=%d, bitrate=%d)",
            d->enc_ctx->frame_size, (int)d->enc_ctx->bit_rate);
    return 0;
}

DreamDecoder *dream_decoder_new(int codec_id,
                                int sample_rate,
                                int channels,
                                const void *extradata,
                                int extradata_size,
                                DreamDecoderOutputCallback cb,
                                void *user_data)
{
    if (!cb) return NULL;

    DreamDecoder *d = calloc(1, sizeof(*d));
    if (!d) return NULL;

    d->codec_id        = codec_id;
    d->out_sample_rate = sample_rate > 0 ? (unsigned int)sample_rate : 48000;
    d->out_channels    = channels > 0 ? (unsigned int)channels : 2;
    d->last_pts        = AV_NOPTS_VALUE;
    d->cb              = cb;
    d->cb_user         = user_data;

    d->codec = avcodec_find_decoder((enum AVCodecID)codec_id);
    if (!d->codec) {
        DEC_DBG("avcodec_find_decoder(%d) failed", codec_id);
        free(d);
        return NULL;
    }

    d->codec_ctx = avcodec_alloc_context3(d->codec);
    if (!d->codec_ctx) {
        DEC_DBG("avcodec_alloc_context3 failed");
        free(d);
        return NULL;
    }

    d->codec_ctx->sample_fmt = AV_SAMPLE_FMT_S16;
    av_channel_layout_default(&d->codec_ctx->ch_layout, 2);

    /* Pass codec_data through to FFmpeg (AAC AudioSpecificConfig etc). */
    if (extradata && extradata_size > 0) {
        d->codec_ctx->extradata = av_mallocz(extradata_size + AV_INPUT_BUFFER_PADDING_SIZE);
        if (!d->codec_ctx->extradata) {
            DEC_DBG("extradata alloc failed");
            avcodec_free_context(&d->codec_ctx);
            free(d);
            return NULL;
        }
        memcpy(d->codec_ctx->extradata, extradata, extradata_size);
        d->codec_ctx->extradata_size = extradata_size;
    }

    /* AC3/EAC3 loudness normalisation against the encoded dialnorm value.
     * -24 dBFS = ATSC A/85 broadcast-loud reference. */
    if (codec_id == AV_CODEC_ID_AC3 || codec_id == AV_CODEC_ID_EAC3) {
        av_opt_set_int(d->codec_ctx, "target_level", -24, AV_OPT_SEARCH_CHILDREN);
        av_opt_set_int(d->codec_ctx, "heavy_compr", 1, AV_OPT_SEARCH_CHILDREN);
    }

    int rc = avcodec_open2(d->codec_ctx, d->codec, NULL);
    if (rc < 0) {
        DEC_DBG("avcodec_open2 failed: %d", rc);
        avcodec_free_context(&d->codec_ctx);
        free(d);
        return NULL;
    }

    d->frame = av_frame_alloc();
    d->avpkt = av_packet_alloc();
    if (!d->frame || !d->avpkt) {
        DEC_DBG("av_frame/packet_alloc failed");
        dream_decoder_free(d);
        return NULL;
    }

    /* No av_parser path right now. AC3/EAC3/DTS/AAC arrive framed via
     * gstreamer's audioparsers (a52parse/dcaparse/aacparse with
     * framed=true caps); routing them through our own av_parser just
     * corrupted their decoder state (test5.mkv cut after ~1 s). TrueHD
     * has no gst mlpparse in this build, but libavcodec's mlp_parser
     * rejected every matroska block ("mlpparse: Parity check failed")
     * — the demuxer hands out blocks that aren't aligned to MLP access
     * units. Send raw blocks straight to the decoder and live with the
     * "Stream parameters not seen" warnings until major sync appears. */
    d->parser = NULL;

    DEC_DBG("init codec=%s (%s) rate=%u ch=%u",
            avcodec_get_name((enum AVCodecID)codec_id),
            d->codec->long_name ? d->codec->long_name : "?",
            d->out_sample_rate, d->out_channels);

    /* AAC / EAC3 -> AC3 transcode if the user picked "Convert to AC3"
     * in Audio Settings AND the codec matches. Failure is non-fatal —
     * falls back to normal PCM decode + dream_alsa. */
    d->saved_digital_raw   = -1;
    d->saved_digital_codec = -1;
    {
        const int aac_force  = (codec_id == AV_CODEC_ID_AAC
                             || codec_id == AV_CODEC_ID_AAC_LATM)
                            && read_aac_transcode_setting();
        const int eac3_force = (codec_id == AV_CODEC_ID_EAC3)
                            && read_ac3plus_transcode_setting();
        if ((aac_force || eac3_force) && start_aac_to_ac3_encoder(d) == 0)
            d->transcode_aac_to_ac3 = 1;
    }

    /* HBR passthrough for TrueHD / DTS-HD MA. Set up the spdif muxer +
     * sysfs upfront; dream_decoder_decode then bypasses the decoder and
     * routes raw packets straight through the muxer. Failure falls back
     * to PCM decode through libavcodec. */
    if (codec_wants_hbr(codec_id)) {
        if (start_hbr_muxer(d, codec_id) == 0)
            d->hbr_mode = 1;
        else
            stop_hbr_muxer(d);   /* cleanup whatever was partly set up */
    }

    return d;
}

void dream_decoder_free(DreamDecoder *d)
{
    if (!d) return;
    /* Flush HBR muxer trailer before sysfs restore so the muxer's last
     * MAT padding burst makes it to the sink. */
    if (d->hbr_mode) stop_hbr_muxer(d);
    /* Flush any pending PCM through the encoder before tearing it down so
     * the final IEC61937 burst makes it to the sink. */
    if (d->transcode_aac_to_ac3) drain_encoder_fifo(d, 1);
    if (d->enc_ctx)   avcodec_free_context(&d->enc_ctx);
    if (d->enc_frame) av_frame_free(&d->enc_frame);
    if (d->enc_pkt)   av_packet_free(&d->enc_pkt);
    if (d->enc_fifo)  { av_audio_fifo_free(d->enc_fifo); d->enc_fifo = NULL; }
    if (d->enc_swr)   swr_free(&d->enc_swr);
    /* Restore bitstream-mode sysfs to whatever the rest of the system
     * expects. Without this the next AC3 channel's PCM gets played as
     * AC3 bitstream → AVR mutes / TV plays static. Also reset the
     * spdif format mixer to 2CH PCM in case it was bumped. */
    if (d->saved_digital_raw >= 0) {
        write_sysfs_int("/sys/class/audiodsp/digital_raw",   d->saved_digital_raw);
        write_sysfs_int("/sys/class/audiodsp/digital_codec", d->saved_digital_codec);
        snd_ctl_t *ctl = NULL;
        if (snd_ctl_open(&ctl, "hw:0", 0) == 0) {
            snd_ctl_elem_id_t    *id;  snd_ctl_elem_id_alloca(&id);
            snd_ctl_elem_value_t *val; snd_ctl_elem_value_alloca(&val);
            snd_ctl_elem_id_set_interface(id, SND_CTL_ELEM_IFACE_MIXER);
            snd_ctl_elem_id_set_name(id, "Audio spdif format");
            snd_ctl_elem_value_set_id(val, id);
            snd_ctl_elem_value_set_enumerated(val, 0, 0);  /* 2 CH PCM */
            snd_ctl_elem_write(ctl, val);
            snd_ctl_close(ctl);
        }
    }

    if (d->parser)    { av_parser_close(d->parser); d->parser = NULL; }
    if (d->codec_ctx) avcodec_free_context(&d->codec_ctx);
    if (d->frame)     av_frame_free(&d->frame);
    if (d->avpkt)     av_packet_free(&d->avpkt);
    if (d->swr_ctx)   swr_free(&d->swr_ctx);
    free(d);
}

static int build_spdif_ac3(DreamDecoder *d, const AVPacket *p)
{
    if (SPDIF_AC3_BUF_BYTES < p->size + 8) return -1;

    dream_set_digital_codec(2);

    uint16_t *out = d->spdif;
    out[0] = SYNCWORD1;
    out[1] = SYNCWORD2;
    out[2] = (uint16_t)(IEC61937_AC3 | (p->data[5] & 0x07) << 8);
    out[3] = (uint16_t)(p->size * 8);

    swab((const char *)p->data, (char *)(out + 4), p->size);
    memset(out + 4 + p->size / 2, 0, SPDIF_AC3_BUF_BYTES - 8 - p->size);

    d->cb(DREAM_DECODER_OUTPUT_IEC61937, d->out_sample_rate, d->out_channels,
          (const uint8_t *)out, SPDIF_AC3_BUF_BYTES, d->last_pts, d->cb_user);
    return 0;
}

static int build_spdif_eac3(DreamDecoder *d, const AVPacket *p)
{
    if (SPDIF_EAC3_BUF_BYTES < d->spdif_index + p->size + 8) return -1;

    int repeat = 1;
    int bsid = p->data[5] >> 3;
    if (bsid > 10 && (p->data[4] & 0xc0) != 0xc0) {
        static const uint8_t eac3_repeat[4] = { 6, 3, 2, 1 };
        repeat = eac3_repeat[(p->data[4] & 0x30) >> 4];
    }

    dream_set_digital_codec(4);

    uint16_t *out = d->spdif;
    swab((const char *)p->data, (char *)(out + 4 + d->spdif_index), p->size);
    d->spdif_index += p->size;
    if (++d->spdif_count < repeat) return 0;

    out[0] = SYNCWORD1;
    out[1] = SYNCWORD2;
    out[2] = IEC61937_EAC3;
    out[3] = (uint16_t)(d->spdif_index * 8);
    memset(out + 4 + d->spdif_index / 2, 0, SPDIF_EAC3_BUF_BYTES - 8 - d->spdif_index);

    d->cb(DREAM_DECODER_OUTPUT_IEC61937, d->out_sample_rate, d->out_channels,
          (const uint8_t *)out, SPDIF_EAC3_BUF_BYTES, d->last_pts, d->cb_user);

    d->spdif_index = 0;
    d->spdif_count = 0;
    return 0;
}

static int build_spdif_dts(DreamDecoder *d, const AVPacket *p)
{
    uint8_t nbs  = (uint8_t)(((p->data[4] & 0x01) << 6) | ((p->data[5] >> 2) & 0x3f));
    int bsid, burst_sz;
    switch (nbs) {
        case 0x07: bsid = 0x0a;          burst_sz = 1024; break;
        case 0x0f: bsid = IEC61937_DTS1; burst_sz = 2048; break;
        case 0x1f: bsid = IEC61937_DTS2; burst_sz = 4096; break;
        case 0x3f: bsid = IEC61937_DTS3; burst_sz = 8192; break;
        default:
            bsid = 0x00;
            if (nbs < 5) nbs = 127;
            burst_sz = (nbs + 1) * 32 * 2 + 2;
            break;
    }

    if (burst_sz < p->size + 8) return -1;

    dream_set_digital_codec(1);

    uint16_t *out = d->spdif;
    out[0] = SYNCWORD1;
    out[1] = SYNCWORD2;
    out[2] = (uint16_t)bsid;
    out[3] = (uint16_t)(p->size * 8);
    out[4] = 0x7FFE;
    out[5] = 0x8001;

    swab((const char *)p->data, (char *)(out + 4), p->size);
    memset(out + 4 + p->size / 2, 0, burst_sz - 8 - p->size);

    d->cb(DREAM_DECODER_OUTPUT_IEC61937, d->out_sample_rate, d->out_channels,
          (const uint8_t *)out, (size_t)burst_sz, d->last_pts, d->cb_user);
    return 0;
}

static int passthrough_for_codec(DreamDecoder *d, const AVPacket *p)
{
    switch (d->codec_id) {
        case AV_CODEC_ID_AC3:  return build_spdif_ac3(d, p);
        case AV_CODEC_ID_EAC3: return build_spdif_eac3(d, p);
        case AV_CODEC_ID_DTS:  return build_spdif_dts(d, p);
        default:               return -2;  /* fall back to PCM */
    }
}

static int emit_pcm_from_frame(DreamDecoder *d)
{
    const int in_rate  = d->frame->sample_rate ? d->frame->sample_rate
                                                : d->codec_ctx->sample_rate;
    const int out_rate = (int)d->out_sample_rate;
    const enum AVSampleFormat in_fmt  = (enum AVSampleFormat)d->frame->format;
    const enum AVSampleFormat out_fmt = AV_SAMPLE_FMT_S16;

    AVChannelLayout in_chl;
    if (d->frame->ch_layout.nb_channels)
        av_channel_layout_copy(&in_chl, &d->frame->ch_layout);
    else if (d->codec_ctx->ch_layout.nb_channels)
        av_channel_layout_copy(&in_chl, &d->codec_ctx->ch_layout);
    else
        av_channel_layout_default(&in_chl, 2);

    AVChannelLayout out_chl = AV_CHANNEL_LAYOUT_STEREO;

    if (!d->swr_ctx) d->swr_ctx = swr_alloc();
    if (!d->swr_ctx) return -1;

    swr_close(d->swr_ctx);
    av_opt_set_chlayout   (d->swr_ctx, "in_chlayout",     &in_chl,   0);
    av_opt_set_int        (d->swr_ctx, "in_sample_rate",   in_rate,  0);
    av_opt_set_sample_fmt (d->swr_ctx, "in_sample_fmt",    in_fmt,   0);
    av_opt_set_chlayout   (d->swr_ctx, "out_chlayout",    &out_chl,  0);
    av_opt_set_int        (d->swr_ctx, "out_sample_rate",  out_rate, 0);
    av_opt_set_sample_fmt (d->swr_ctx, "out_sample_fmt",   out_fmt,  0);
    if (swr_init(d->swr_ctx) < 0) return -1;

    const int out_max = swr_get_out_samples(d->swr_ctx, d->frame->nb_samples);
    if (out_max <= 0) return 0;

    const int out_nb_ch = out_chl.nb_channels;
    const int out_bps   = av_get_bytes_per_sample(out_fmt);

    uint8_t *out = NULL;
    int out_linesize = 0;
    if (av_samples_alloc(&out, &out_linesize, out_nb_ch, out_max, out_fmt, 0) < 0)
        return -1;

    const uint8_t *src[AV_NUM_DATA_POINTERS] = { 0 };
    for (int i = 0; i < AV_NUM_DATA_POINTERS; ++i)
        src[i] = d->frame->extended_data[i];

    int got = swr_convert(d->swr_ctx, &out, out_max, src, d->frame->nb_samples);
    if (got > 0) {
        const size_t bytes = (size_t)got * out_nb_ch * out_bps;
        d->cb(DREAM_DECODER_OUTPUT_PCM,
              (unsigned int)out_rate, (unsigned int)out_nb_ch,
              out, bytes, d->last_pts, d->cb_user);
    }
    av_freep(&out);
    return 0;
}

int dream_decoder_decode(DreamDecoder *d,
                         const uint8_t *data,
                         int size,
                         int64_t pts,
                         int64_t dts)
{
    if (!d || !d->codec_ctx) return -1;
    if (!data || size <= 0)  return 0;

    d->avpkt->data = (uint8_t *)data;
    d->avpkt->size = size;
    d->avpkt->pts  = pts;
    d->avpkt->dts  = dts;

    /* HBR passthrough: bypass libavcodec entirely, feed raw TrueHD /
     * DTS-HD frames into the spdif muxer. The muxer emits IEC61937
     * bytes via hbr_write_cb and hbr_push_packet forwards them to
     * dream_alsa at 192k/8ch S16. */
    if (d->hbr_mode) {
        if (pts != AV_NOPTS_VALUE) d->last_pts = pts;
        hbr_push_packet(d, data, size);
        return 0;
    }

    /* Passthrough path: feed IEC61937 frame without invoking the decoder. */
    const int dgraw = dream_get_digital_raw();
    if (dgraw == DIGITAL_RAW_SPDIF || dgraw == DIGITAL_RAW_HDMI) {
        int rc = passthrough_for_codec(d, d->avpkt);
        if (rc == 0)  return 0;
        if (rc == -1) return -1;
        /* rc == -2: codec not passthroughable, fall through to PCM */
    } else {
        dream_set_digital_codec(0);
    }

    /* Walk one packet through the parser (if any) and feed each complete
     * frame to the decoder. With no parser this is a single pass with
     * data/size as-is. */
    const uint8_t *in_data = data;
    int            in_size = size;
    int64_t        in_pts  = pts;
    int64_t        in_dts  = dts;

    while (in_size > 0 || !d->parser) {
        uint8_t *frame_data = (uint8_t *)in_data;
        int      frame_size = in_size;
        int      consumed   = in_size;

        if (d->parser) {
            consumed = av_parser_parse2(d->parser, d->codec_ctx,
                                        &frame_data, &frame_size,
                                        in_data, in_size,
                                        in_pts, in_dts, 0);
            if (consumed < 0) break;
            if (consumed == 0 && frame_size == 0) break;  /* no progress → bail */
            in_data += consumed;
            in_size -= consumed;
            in_pts   = AV_NOPTS_VALUE;
            in_dts   = AV_NOPTS_VALUE;
            if (frame_size == 0) {
                if (in_size == 0) break;       /* parser swallowed all */
                continue;                       /* needs more bytes for a frame */
            }
        }

        d->avpkt->data = frame_data;
        d->avpkt->size = frame_size;
        d->avpkt->pts  = d->parser ? d->parser->pts : pts;
        d->avpkt->dts  = d->parser ? d->parser->dts : dts;

        /* HLS chunks often have a bad frame at the boundary — skip,
         * don't abort the pipeline. */
        int rc = avcodec_send_packet(d->codec_ctx, d->avpkt);
        if (rc < 0 && rc != AVERROR(EAGAIN)) {
            DEC_DBG("avcodec_send_packet: %d (skipping packet)", rc);
            av_packet_unref(d->avpkt);
            if (!d->parser) break;
            continue;
        }

        while (1) {
            rc = avcodec_receive_frame(d->codec_ctx, d->frame);
            if (rc == AVERROR(EAGAIN) || rc == AVERROR_EOF) break;
            if (rc < 0) {
                DEC_DBG("avcodec_receive_frame: %d (skipping frame)", rc);
                av_frame_unref(d->frame);
                break;
            }
            if (d->frame->pts != AV_NOPTS_VALUE)
                d->last_pts = d->frame->pts;
            if (d->transcode_aac_to_ac3)
                feed_encoder_with_pcm(d, d->frame);
            else
                emit_pcm_from_frame(d);
            av_frame_unref(d->frame);
        }
        av_packet_unref(d->avpkt);

        if (!d->parser) break;   /* one-shot for non-parser codecs */
    }
    return 0;
}
