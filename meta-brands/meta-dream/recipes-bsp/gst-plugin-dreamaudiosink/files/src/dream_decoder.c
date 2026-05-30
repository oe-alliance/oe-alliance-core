#ifdef HAVE_CONFIG_H
#include "config.h"
#endif

#define _DEFAULT_SOURCE   /* for swab() in <unistd.h> */
#define _XOPEN_SOURCE 700

#include "dream_decoder.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <libavcodec/avcodec.h>
#include <libavutil/opt.h>
#include <libavutil/channel_layout.h>
#include <libavutil/samplefmt.h>
#include <libswresample/swresample.h>

#define DEC_DBG(...) do { fprintf(stderr, "[dream_decoder] " __VA_ARGS__); fputc('\n', stderr); } while (0)

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
};

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

DreamDecoder *dream_decoder_new(int codec_id,
                                int sample_rate,
                                int channels,
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

    DEC_DBG("init codec=%s (%s) rate=%u ch=%u",
            avcodec_get_name((enum AVCodecID)codec_id),
            d->codec->long_name ? d->codec->long_name : "?",
            d->out_sample_rate, d->out_channels);

    return d;
}

void dream_decoder_free(DreamDecoder *d)
{
    if (!d) return;
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

    int rc = avcodec_send_packet(d->codec_ctx, d->avpkt);
    if (rc < 0 && rc != AVERROR(EAGAIN)) {
        DEC_DBG("avcodec_send_packet: %d", rc);
        return -1;
    }

    while (1) {
        rc = avcodec_receive_frame(d->codec_ctx, d->frame);
        if (rc == AVERROR(EAGAIN) || rc == AVERROR_EOF) break;
        if (rc < 0) {
            DEC_DBG("avcodec_receive_frame: %d", rc);
            return -1;
        }

        if (d->frame->pts != AV_NOPTS_VALUE)
            d->last_pts = d->frame->pts;

        emit_pcm_from_frame(d);
        av_frame_unref(d->frame);
    }

    av_packet_unref(d->avpkt);
    return 0;
}
