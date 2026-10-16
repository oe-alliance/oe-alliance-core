/* SPDX-License-Identifier: LGPL-2.1-or-later */
#ifndef GST_DREAM_VIDEO_SINK_H
#define GST_DREAM_VIDEO_SINK_H

#include <gst/base/gstbasesink.h>
#include "dream_codec.h"
#include "dream_overlay.h"
#include "dream_video_device.h"

#define GST_TYPE_DREAM_VIDEO_SINK (gst_dream_video_sink_get_type())
#define GST_DREAM_VIDEO_SINK(obj) (G_TYPE_CHECK_INSTANCE_CAST((obj), GST_TYPE_DREAM_VIDEO_SINK, GstDreamVideoSink))

typedef struct {
	GstBaseSink parent;
	/* Lock order when both required: ioMutex, then mutex. No blocking poll
	 * under either lock. Queue bytes include the worker's current frame. */
	GMutex mutex, ioMutex;
	GCond changed;
	GQueue queue;
	GThread *worker;
	DreamVideoDevice device;
	DreamVideoSysinfo decoderInfo;
	DreamCodec codec;
	GstCaps *caps;
	GstBuffer *preparedBuffer;
	GstDreamOverlayCompositor *compositor;
	GstClock *latencyClock;
	GstClockTime latency, latencyClockTime;
	guint64 latencyPts;
	guint64 queuedBytes, submittedFrames, submittedBytes;
	guint64 generation, cancelGeneration;
	guint maxQueuedFrames;
	guint64 maxQueuedBytes;
	guint ioTimeoutMs;
	gboolean started, stopping, playing, flushing, unlocked, configured;
	gboolean failed, eosDone, ptsValid, needHeaders, needKeyframe;
	gboolean independentPts;
	gint width, height, aspect, frameRate, progressive;
	GstSegment segment;
} GstDreamVideoSink;

typedef struct {
	GstBaseSinkClass parent;
	gint64 (*getDecoderTime)(GstDreamVideoSink *sink);
	gpointer (*getOverlayCompositor)(GstDreamVideoSink *sink);
} GstDreamVideoSinkClass;

GType gst_dream_video_sink_get_type(void);
gboolean gst_dream_video_sink_register(GstPlugin *plugin);

#endif
