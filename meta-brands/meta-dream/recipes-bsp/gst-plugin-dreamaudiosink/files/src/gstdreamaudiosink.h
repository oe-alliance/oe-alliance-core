#ifndef __GST_DREAM_AUDIO_SINK_H__
#define __GST_DREAM_AUDIO_SINK_H__

#include <gst/gst.h>
#include <gst/base/gstbasesink.h>

#include "dream_decoder.h"
#include "dream_alsa.h"
#include "dream_avsync.h"

G_BEGIN_DECLS

#define GST_TYPE_DREAM_AUDIO_SINK (gst_dream_audio_sink_get_type())
G_DECLARE_FINAL_TYPE(GstDreamAudioSink, gst_dream_audio_sink,
                     GST, DREAM_AUDIO_SINK, GstBaseSink)

struct _GstDreamAudioSink
{
    GstBaseSink parent;

    /* properties */
    gboolean    e2_sync;
    gboolean    e2_async;
    gchar      *device;
    gint        tsync_mode;
    gint        softdecoder_delay_ms;
    gdouble     volume;
    gboolean    volume_set_explicitly;  /* TRUE = upstream called PROP_VOLUME setter */

    /* runtime - SW decode + ALSA (Kodi/CoreELEC AESinkALSA pattern). */
    DreamDecoder *decoder;
    DreamAlsa    *alsa;
    DreamAvsync  *avsync;

    gint     codec_id;          /* FFmpeg AVCodecID, -1 unset */
    gboolean raw_pcm;           /* input is interleaved S16LE, no FFmpeg */
    guint    sample_rate;
    guint    channels;
    gint64   last_pts_90k;
};

G_END_DECLS

#endif
