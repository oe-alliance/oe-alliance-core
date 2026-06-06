#ifdef HAVE_CONFIG_H
#include "config.h"
#endif

#include "gstdreamaudiosink.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <libavcodec/codec_id.h>
#include <libavutil/avutil.h>

GST_DEBUG_CATEGORY_STATIC(gst_dream_audio_sink_debug);
#define GST_CAT_DEFAULT gst_dream_audio_sink_debug

#define NS_PER_90K_TICK 11111

enum {
    PROP_0,
    PROP_E2_SYNC,
    PROP_E2_ASYNC,
    PROP_DEVICE,
    PROP_TSYNC_MODE,
    PROP_SOFTDECODER_DELAY_MS,
    PROP_VOLUME,
};

enum {
    SIGNAL_GET_DECODER_TIME,
    SIGNAL_DISCONTINUE,
    SIGNAL_LAST
};
static guint gst_dream_audio_sink_signals[SIGNAL_LAST] = { 0 };

#define DEFAULT_E2_SYNC               TRUE
#define DEFAULT_E2_ASYNC              FALSE
/* "dreamhdmi" is the same ALSA device eAlsaOutput uses for Live-TV PCM.
 * Going through "default" (plug → dreamhdmi → softvol → dmix) instead
 * of "dreamhdmi" (softvol → dmix) directly caused movie audio to be
 * silently dropped — writes succeeded but never reached hw:0,0. */
#define DEFAULT_DEVICE                "dreamhdmi"
#define DEFAULT_TSYNC_MODE            0     /* VMASTER = kernel tsync disabled */
#define DEFAULT_SOFTDECODER_DELAY_MS  0

static GstStaticPadTemplate sink_template = GST_STATIC_PAD_TEMPLATE(
    "sink",
    GST_PAD_SINK,
    GST_PAD_ALWAYS,
    GST_STATIC_CAPS(
        "audio/mpeg, mpegversion=(int)1, parsed=(boolean)true; "        /* MP1/2/3 */
        "audio/mpeg, mpegversion=(int)2, parsed=(boolean)true; "        /* MPEG-2 audio (MP2 ext) */
        "audio/mpeg, mpegversion=(int)2, framed=(boolean)true; "        /* MPEG-2 AAC */
        "audio/mpeg, mpegversion=(int)4, framed=(boolean)true; "        /* MPEG-4 AAC */
        /* ac3parse / dcaparse exist as gstreamer elements and frame the
         * bitstream cleanly. Require framed=true so the autoplugger
         * inserts them between matroskademux and us — without that the
         * decoder gets unframed bytes and rejects every packet with
         * AVERROR_INVALIDDATA. */
        "audio/x-ac3, framed=(boolean)true; "
        "audio/x-eac3, framed=(boolean)true; "
        "audio/x-dts, framed=(boolean)true; "
        /* No mlpparse in this build. Accept unframed TrueHD and let our
         * dream_decoder slice access units via libavcodec's MLP parser
         * (av_parser_init). Without AVR this falls back to libavcodec
         * PCM decode. */
        "audio/x-true-hd"
    ));

G_DEFINE_TYPE(GstDreamAudioSink, gst_dream_audio_sink, GST_TYPE_BASE_SINK)

/* ---------- helpers ---------- */

static gint codec_id_from_caps(const GstCaps *caps)
{
    if (!caps || gst_caps_get_size(caps) == 0) return -1;
    const GstStructure *s = gst_caps_get_structure(caps, 0);
    const gchar *name = gst_structure_get_name(s);

    if (g_str_equal(name, "audio/x-ac3"))     return AV_CODEC_ID_AC3;
    if (g_str_equal(name, "audio/x-eac3"))    return AV_CODEC_ID_EAC3;
    if (g_str_equal(name, "audio/x-dts"))     return AV_CODEC_ID_DTS;
    if (g_str_equal(name, "audio/x-true-hd")) return AV_CODEC_ID_TRUEHD;
    if (g_str_equal(name, "audio/mpeg")) {
        gint mv = 0;
        gst_structure_get_int(s, "mpegversion", &mv);
        if (mv == 1) {
            gint layer = 0;
            gst_structure_get_int(s, "layer", &layer);
            return (layer == 3) ? AV_CODEC_ID_MP3 : AV_CODEC_ID_MP2;
        }
        if (mv == 2) {
            /* MPEG-2 audio (MP1/2/3) carries layer; MPEG-2 AAC doesn't. */
            gint layer = 0;
            if (gst_structure_get_int(s, "layer", &layer) && layer != 0)
                return (layer == 3) ? AV_CODEC_ID_MP3 : AV_CODEC_ID_MP2;
            return AV_CODEC_ID_AAC;
        }
        if (mv == 4) return AV_CODEC_ID_AAC;
    }
    return -1;
}

static inline gint64 ns_to_pts90k(GstClockTime ns)
{
    if (!GST_CLOCK_TIME_IS_VALID(ns)) return AV_NOPTS_VALUE;
    return (gint64)(ns / NS_PER_90K_TICK);
}

/* ---------- decoder output callback ---------- */

static void
gst_dream_audio_sink_decoder_cb(DreamDecoderOutputType type,
                                unsigned int sample_rate,
                                unsigned int channels,
                                const uint8_t *data,
                                size_t size,
                                int64_t pts,
                                void *user_data)
{
    GstDreamAudioSink *self = GST_DREAM_AUDIO_SINK(user_data);

    int passthrough = (type == DREAM_DECODER_OUTPUT_IEC61937);
    unsigned int bps = 2; /* S16LE */

    if (self->sample_rate != sample_rate || self->channels != channels) {
        if (dream_alsa_set_params(self->alsa, sample_rate, channels, bps,
                                  passthrough) < 0) {
            GST_WARNING_OBJECT(self, "alsa set_params failed sr=%u ch=%u pt=%d",
                               sample_rate, channels, passthrough);
            return;
        }
        self->sample_rate = sample_rate;
        self->channels    = channels;
    }

    if (pts >= 0) self->last_pts_90k = pts;
    if (self->avsync && pts >= 0)
        dream_avsync_checkin_audio_pts(self->avsync, (uint32_t)pts);

    dream_alsa_write(self->alsa, data, size);
}

static int read_user_volume_setting(void)
{
    FILE *f = fopen("/etc/enigma2/settings", "r");
    if (!f) return -1;
    char line[256];
    int vol = -1;
    while (fgets(line, sizeof(line), f)) {
        const char *prefix = "config.volumeControl.volume=";
        const size_t plen = strlen(prefix);
        if (strncmp(line, prefix, plen) != 0) continue;
        vol = atoi(line + plen);
        break;
    }
    fclose(f);
    if (vol < 0)   vol = 0;
    if (vol > 100) vol = 100;
    return vol;
}

/* ---------- GstBaseSink vmethods ---------- */

static gboolean
gst_dream_audio_sink_start(GstBaseSink *bsink)
{
    GstDreamAudioSink *self = GST_DREAM_AUDIO_SINK(bsink);

    self->alsa = dream_alsa_new(self->device);
    if (!self->alsa) return FALSE;

    if (!self->volume_set_explicitly) {
        int v = read_user_volume_setting();
        if (v >= 0) self->volume = (gdouble)v / 100.0;
    }
    /* Apply pre-start volume — PROP_VOLUME setter no-ops when alsa is NULL. */
    dream_alsa_set_volume(self->alsa, (int)(self->volume * 100.0 + 0.5));

    self->avsync = dream_avsync_new((DreamAvsyncMode)self->tsync_mode);

    self->codec_id     = -1;
    self->sample_rate  = 0;
    self->channels     = 0;
    self->last_pts_90k = AV_NOPTS_VALUE;
    return TRUE;
}

static gboolean
gst_dream_audio_sink_stop(GstBaseSink *bsink)
{
    GstDreamAudioSink *self = GST_DREAM_AUDIO_SINK(bsink);
    if (self->decoder) { dream_decoder_free(self->decoder); self->decoder = NULL; }
    if (self->alsa)    { dream_alsa_free(self->alsa);       self->alsa    = NULL; }
    if (self->avsync)  { dream_avsync_free(self->avsync);   self->avsync  = NULL; }
    self->codec_id = -1;
    return TRUE;
}

static gboolean
gst_dream_audio_sink_set_caps(GstBaseSink *bsink, GstCaps *caps)
{
    GstDreamAudioSink *self = GST_DREAM_AUDIO_SINK(bsink);

    gint codec = codec_id_from_caps(caps);
    if (codec < 0) { GST_WARNING_OBJECT(self, "unsupported caps"); return FALSE; }

    gint ch = 0;
    const GstStructure *s = gst_caps_get_structure(caps, 0);
    gst_structure_get_int(s, "channels", &ch);
    if (ch <= 0) ch = 2;

    if (self->decoder && self->codec_id == codec) return TRUE;

    if (self->decoder) { dream_decoder_free(self->decoder); self->decoder = NULL; }

    /* codec_data from caps = AAC AudioSpecificConfig etc. Without it,
     * FFmpeg AAC rejects every raw frame as INVALIDDATA. */
    const void *extradata = NULL;
    gsize extradata_size = 0;
    GstMapInfo cd_mi = {0};
    const GValue *cd_val = gst_structure_get_value(s, "codec_data");
    GstBuffer *cd_buf = cd_val ? gst_value_get_buffer(cd_val) : NULL;
    if (cd_buf && gst_buffer_map(cd_buf, &cd_mi, GST_MAP_READ)) {
        extradata = cd_mi.data;
        extradata_size = cd_mi.size;
    }

    /* Force ALSA output rate to 48 kHz — AMlogic HW rejects exotic rates. */
    self->decoder = dream_decoder_new(codec, 48000, ch,
                                      extradata, (int)extradata_size,
                                      gst_dream_audio_sink_decoder_cb, self);
    if (cd_buf) gst_buffer_unmap(cd_buf, &cd_mi);
    if (!self->decoder) {
        GST_ERROR_OBJECT(self, "decoder_new failed codec=%d", codec);
        return FALSE;
    }
    self->codec_id = codec;
    return TRUE;
}

static GstFlowReturn
gst_dream_audio_sink_render(GstBaseSink *bsink, GstBuffer *buf)
{
    GstDreamAudioSink *self = GST_DREAM_AUDIO_SINK(bsink);

    if (!self->decoder) {
        GST_WARNING_OBJECT(self, "no decoder yet");
        return GST_FLOW_ERROR;
    }

    GstMapInfo mi;
    if (!gst_buffer_map(buf, &mi, GST_MAP_READ)) return GST_FLOW_ERROR;

    const gint64 pts_90k = ns_to_pts90k(GST_BUFFER_PTS(buf));
    int rc = dream_decoder_decode(self->decoder, mi.data, (int)mi.size,
                                  pts_90k, pts_90k);
    gst_buffer_unmap(buf, &mi);
    return (rc < 0) ? GST_FLOW_ERROR : GST_FLOW_OK;
}

/* ---------- properties ---------- */

static void
gst_dream_audio_sink_set_property(GObject *o, guint id, const GValue *v, GParamSpec *p)
{
    GstDreamAudioSink *self = GST_DREAM_AUDIO_SINK(o);
    switch (id) {
        case PROP_E2_SYNC:               self->e2_sync  = g_value_get_boolean(v); break;
        case PROP_E2_ASYNC:              self->e2_async = g_value_get_boolean(v); break;
        case PROP_DEVICE:
            g_free(self->device);
            self->device = g_value_dup_string(v);
            break;
        case PROP_TSYNC_MODE:            self->tsync_mode = g_value_get_int(v); break;
        case PROP_SOFTDECODER_DELAY_MS:  self->softdecoder_delay_ms = g_value_get_int(v); break;
        case PROP_VOLUME:
        {
            gdouble vol = g_value_get_double(v);
            if (vol < 0.0) vol = 0.0;
            if (vol > 1.0) vol = 1.0;
            self->volume = vol;
            self->volume_set_explicitly = TRUE;
            if (self->alsa)
                dream_alsa_set_volume(self->alsa, (int)(vol * 100.0 + 0.5));
            break;
        }
        default: G_OBJECT_WARN_INVALID_PROPERTY_ID(o, id, p); break;
    }
}

static void
gst_dream_audio_sink_get_property(GObject *o, guint id, GValue *v, GParamSpec *p)
{
    GstDreamAudioSink *self = GST_DREAM_AUDIO_SINK(o);
    switch (id) {
        case PROP_E2_SYNC:               g_value_set_boolean(v, self->e2_sync);  break;
        case PROP_E2_ASYNC:              g_value_set_boolean(v, self->e2_async); break;
        case PROP_DEVICE:                g_value_set_string(v, self->device);    break;
        case PROP_TSYNC_MODE:            g_value_set_int(v, self->tsync_mode);   break;
        case PROP_SOFTDECODER_DELAY_MS:  g_value_set_int(v, self->softdecoder_delay_ms); break;
        case PROP_VOLUME:                g_value_set_double(v, self->volume); break;
        default: G_OBJECT_WARN_INVALID_PROPERTY_ID(o, id, p); break;
    }
}

static gint64
gst_dream_audio_sink_get_decoder_time(GstDreamAudioSink *self)
{
    return self->last_pts_90k;
}

static void
gst_dream_audio_sink_discontinue(GstDreamAudioSink *self)
{
    if (self->avsync) dream_avsync_signal_discontinuity(self->avsync);
    if (self->alsa)   dream_alsa_drop(self->alsa);
}

static void
gst_dream_audio_sink_finalize(GObject *obj)
{
    GstDreamAudioSink *self = GST_DREAM_AUDIO_SINK(obj);
    g_free(self->device);
    G_OBJECT_CLASS(gst_dream_audio_sink_parent_class)->finalize(obj);
}

static void
gst_dream_audio_sink_class_init(GstDreamAudioSinkClass *klass)
{
    GObjectClass     *gobject_class  = G_OBJECT_CLASS(klass);
    GstElementClass  *element_class  = GST_ELEMENT_CLASS(klass);
    GstBaseSinkClass *basesink_class = GST_BASE_SINK_CLASS(klass);

    gobject_class->set_property = gst_dream_audio_sink_set_property;
    gobject_class->get_property = gst_dream_audio_sink_get_property;
    gobject_class->finalize     = gst_dream_audio_sink_finalize;

    g_object_class_install_property(gobject_class, PROP_E2_SYNC,
        g_param_spec_boolean("e2-sync", "e2-sync", "dvbaudiosink parity",
            DEFAULT_E2_SYNC, G_PARAM_READWRITE));
    g_object_class_install_property(gobject_class, PROP_E2_ASYNC,
        g_param_spec_boolean("e2-async", "e2-async", "dvbaudiosink parity",
            DEFAULT_E2_ASYNC, G_PARAM_READWRITE));
    g_object_class_install_property(gobject_class, PROP_DEVICE,
        g_param_spec_string("device", "ALSA device", "ALSA PCM device name",
            DEFAULT_DEVICE, G_PARAM_READWRITE));
    g_object_class_install_property(gobject_class, PROP_TSYNC_MODE,
        g_param_spec_int("tsync-mode", "tsync mode",
            "0=disable kernel tsync (default)",
            0, 2, DEFAULT_TSYNC_MODE, G_PARAM_READWRITE));
    g_object_class_install_property(gobject_class, PROP_SOFTDECODER_DELAY_MS,
        g_param_spec_int("softdecoder-delay-ms", "softdecoder delay ms",
            "Legacy hint", 0, 5000, DEFAULT_SOFTDECODER_DELAY_MS, G_PARAM_READWRITE));
    g_object_class_install_property(gobject_class, PROP_VOLUME,
        g_param_spec_double("volume", "volume",
            "Software volume 0.0-1.0 (AML Master pinned at max).",
            0.0, 1.0, 1.0, G_PARAM_READWRITE));

    gst_dream_audio_sink_signals[SIGNAL_GET_DECODER_TIME] =
        g_signal_new_class_handler("get-decoder-time",
            G_TYPE_FROM_CLASS(klass),
            G_SIGNAL_RUN_LAST | G_SIGNAL_ACTION,
            G_CALLBACK(gst_dream_audio_sink_get_decoder_time),
            NULL, NULL, NULL, G_TYPE_INT64, 0);

    gst_dream_audio_sink_signals[SIGNAL_DISCONTINUE] =
        g_signal_new_class_handler("discontinue",
            G_TYPE_FROM_CLASS(klass),
            G_SIGNAL_RUN_LAST | G_SIGNAL_ACTION,
            G_CALLBACK(gst_dream_audio_sink_discontinue),
            NULL, NULL, NULL, G_TYPE_NONE, 0);

    gst_element_class_add_pad_template(
        element_class, gst_static_pad_template_get(&sink_template));

    gst_element_class_set_static_metadata(
        element_class,
        "Dreambox AMLogic Audio Sink",
        "Sink/Audio",
        "Audio sink for Dreambox AMLogic boxes - FFmpeg decode + ALSA",
        "openATV <openatv@gmail.com>");

    basesink_class->start    = GST_DEBUG_FUNCPTR(gst_dream_audio_sink_start);
    basesink_class->stop     = GST_DEBUG_FUNCPTR(gst_dream_audio_sink_stop);
    basesink_class->set_caps = GST_DEBUG_FUNCPTR(gst_dream_audio_sink_set_caps);
    basesink_class->render   = GST_DEBUG_FUNCPTR(gst_dream_audio_sink_render);

    GST_DEBUG_CATEGORY_INIT(
        gst_dream_audio_sink_debug, "dreamaudiosink", 0, "dreamaudiosink");
}

static void
gst_dream_audio_sink_init(GstDreamAudioSink *self)
{
    self->e2_sync              = DEFAULT_E2_SYNC;
    self->e2_async             = DEFAULT_E2_ASYNC;
    self->device               = g_strdup(DEFAULT_DEVICE);
    self->tsync_mode           = DEFAULT_TSYNC_MODE;
    self->softdecoder_delay_ms = DEFAULT_SOFTDECODER_DELAY_MS;
    self->volume               = 1.0;
    self->codec_id             = -1;
    self->last_pts_90k         = AV_NOPTS_VALUE;

    /* Disable GstBaseSink's late-buffer drop. With file playback (non-
     * live) BaseSink's default max-lateness=20ms drops audio buffers that
     * arrive too late vs the pipeline clock. Combined with the 300+ms
     * drift our dream_avsync calibration measures, this dropped every
     * buffer after the initial preroll — explained the "1 second audio,
     * then silence" symptom on local MKV files (Tagesschau HLS bypasses
     * this because live pipelines disable lateness checks). */
    gst_base_sink_set_max_lateness(GST_BASE_SINK(self), -1);
    gst_base_sink_set_sync(GST_BASE_SINK(self), TRUE);
}
