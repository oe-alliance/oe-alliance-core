/* SPDX-License-Identifier: LGPL-2.1-or-later */
#ifdef HAVE_CONFIG_H
#include "config.h"
#endif
#include "gstdreamvideosink.h"

#include <errno.h>
#include <libudev.h>
#include <math.h>
#include <string.h>
#include <gst/video/videooverlay.h>

GST_DEBUG_CATEGORY_STATIC(dream_video_debug);
#define GST_CAT_DEFAULT dream_video_debug

/* Public names match E2. The instance layout and worker are new code, not
 * copies of the proprietary sink's object layout or decompiler output. */
G_DEFINE_TYPE_WITH_CODE(GstDreamVideoSink, gst_dream_video_sink, GST_TYPE_BASE_SINK,
	G_IMPLEMENT_INTERFACE(GST_TYPE_VIDEO_OVERLAY, NULL))

enum { PROP_0, PROP_QUEUE_BYTES, PROP_QUEUE_FRAMES, PROP_IO_TIMEOUT,
	PROP_QUEUED_BYTES, PROP_SUBMITTED_FRAMES, PROP_SUBMITTED_BYTES };

typedef struct {
	GstBuffer *buffer;
	gsize size;
	guint64 generation;
	GstClockTime pts;
	guint width, height;
	gboolean eos;
} DreamVideoJob;

static GstStaticPadTemplate sink_template = GST_STATIC_PAD_TEMPLATE("sink",
	GST_PAD_SINK, GST_PAD_ALWAYS, GST_STATIC_CAPS(DREAM_VIDEO_CAPS));

static gint64 decoder_time(GstDreamVideoSink *sink);

static void overlay_size(GstDreamVideoSink *sink, gint width, gint height)
{
	GstVideoInfo info;
	gst_video_info_init(&info);
	if (width > 0 && height > 0 && gst_video_info_set_format(&info, GST_VIDEO_FORMAT_BGRA, width, height))
		gst_dream_overlay_compositor_set_vinfo(sink->compositor, &info);
}

static void job_free(DreamVideoJob *job)
{
	if (job->buffer)
		gst_buffer_unref(job->buffer);
	g_free(job);
}

/* mutex held; consumes encoded on both success and failure. Capacity must be
 * awaited BEFORE stateful codec push/drain, never after advancing lookahead. */
static gboolean queue_encoded(GstDreamVideoSink *sink, GstBuffer *encoded)
{
	if (!encoded)
		return TRUE;
	gsize size = gst_buffer_get_size(encoded);
	DreamVideoJob *job = size <= sink->maxQueuedBytes ? g_try_new0(DreamVideoJob, 1) : NULL;
	if (!job) {
		gst_buffer_unref(encoded);
		return FALSE;
	}
	job->buffer = encoded;
	job->size = size;
	job->generation = sink->generation;
	if (sink->codec.kind == DREAM_CODEC_SORENSON) {
		/* Snapshot the parsed size with its frame, not the codec's later
		 * lookahead state or asynchronous decoder size notifications. */
		job->width = sink->codec.width;
		job->height = sink->codec.height;
	}
	GstClockTime pts = GST_BUFFER_PTS(encoded), dts = GST_BUFFER_DTS(encoded);
	if (GST_CLOCK_TIME_IS_VALID(pts) && pts != dts)
		sink->independentPts = TRUE;
	/* DTS-only containers need timestamps too. Once real presentation order
	 * differs from decoding order, missing PTS must not fall back to DTS.
	 * Resolve while queueing so later buffers cannot change an older job. */
	job->pts = GST_CLOCK_TIME_IS_VALID(pts) || sink->independentPts ? pts : dts;
	g_queue_push_tail(&sink->queue, job);
	sink->queuedBytes += size;
	return TRUE;
}

/* mutex held. The in-flight frame remains counted until the worker releases it. */
static void clear_queue(GstDreamVideoSink *sink)
{
	DreamVideoJob *job;
	while ((job = g_queue_pop_head(&sink->queue))) {
		sink->queuedBytes -= job->size;
		job_free(job);
	}
	g_cond_broadcast(&sink->changed);
}

static void invalidate_stream(GstDreamVideoSink *sink, gboolean recovery)
{
	++sink->generation;
	/* Decoder recovery discards compressed data, but must not stop the
	 * upstream task. Keep real flush/stop cancellation visible even if its
	 * start/stop flags have already changed again when a waiter wakes. */
	if (!recovery)
		++sink->cancelGeneration;
	dream_codec_flush(&sink->codec);
	sink->ptsValid = FALSE;
	sink->latencyClockTime = GST_CLOCK_TIME_NONE;
	sink->latencyPts = DREAM_VIDEO_PTS_NONE;
	sink->needHeaders = TRUE;
	sink->needKeyframe = TRUE;
	sink->eosDone = FALSE;
	gst_buffer_replace(&sink->preparedBuffer, NULL);
	clear_queue(sink);
}

static void report_failure(GstDreamVideoSink *sink, const gchar *message)
{
	gboolean first;
	g_mutex_lock(&sink->mutex);
	first = !sink->failed && !sink->stopping;
	sink->failed = TRUE;
	clear_queue(sink);
	g_mutex_unlock(&sink->mutex);
	if (first)
		GST_ELEMENT_ERROR(sink, RESOURCE, WRITE, ("Dream video decoder failed"), ("%s", message));
}

static void post_integer(GstDreamVideoSink *sink, const gchar *event,
	const gchar *field, gint value)
{
	gst_element_post_message(GST_ELEMENT(sink), gst_message_new_element(GST_OBJECT(sink),
		gst_structure_new(event, field, G_TYPE_INT, value, NULL)));
}

static void post_size(GstDreamVideoSink *sink, const gchar *event,
	gint width, gint height, gint aspect)
{
	gst_element_post_message(GST_ELEMENT(sink), gst_message_new_element(GST_OBJECT(sink),
		gst_structure_new(event, "width", G_TYPE_INT, width, "height", G_TYPE_INT, height,
			"aspect_ratio", G_TYPE_INT, aspect, NULL)));
}

/* Read events under ioMutex, but never run bus handlers while holding it. */
static void decoder_events(GstDreamVideoSink *sink)
{
	/* Acquire the element clock outside our locks. Keep the baseline clock
	 * referenced so changing clocks cannot accidentally reuse its identity. */
	GstClock *clock = gst_element_get_clock(GST_ELEMENT(sink));
	struct video_event events[32];
	guint count = 0;
	gint hintedRate = 0;
	gboolean sizeChanged = FALSE, progressiveChanged = FALSE, rateChanged = FALSE;
	gboolean discontinuity = FALSE;
	gboolean latencyChanged = FALSE;
	gint width = 0, height = 0, aspect = 0, progressive = 0;
	g_mutex_lock(&sink->ioMutex);
	GstClockTime clockTime = clock ? gst_clock_get_internal_time(clock) : GST_CLOCK_TIME_NONE;
	/* Poll even when every AU was accepted immediately: amvideo_poll carries
	 * size notifications independently of decoder backpressure. */
	int pollResult = sink->device.videoFd >= 0 ? dream_video_device_wait(&sink->device, FALSE, 0) : 0;
	gint polledWidth = 0, polledHeight = 0, polledAspect = 0;
	gboolean polledSize = dream_video_device_read_size(&sink->device, &polledWidth, &polledHeight, &polledAspect);
	g_mutex_lock(&sink->mutex);
	while (sink->device.videoFd >= 0 && count < G_N_ELEMENTS(events)) {
		/* Some Dream drivers return success without writing an empty event.
		 * Initialize each result and treat type 0 as the end of the queue. */
		memset(&events[count], 0, sizeof(events[count]));
		if (dream_video_device_ioctl(&sink->device, VIDEO_GET_EVENT,
			(uintptr_t) &events[count]) < 0 || events[count].type == 0)
			break;
		++count;
	}
	if (sink->device.monitor) {
		for (guint i = 0; i < 32; ++i) {
			struct udev_device *event = udev_monitor_receive_device(sink->device.monitor);
			if (!event)
				break;
			const gchar *hint = udev_device_get_property_value(event, "FRAME_RATE_HINT");
			if (hint) {
				gchar *end = NULL;
				guint64 duration = g_ascii_strtoull(hint, &end, 10);
				if (end && !*end && duration >= 800 && duration <= 96000)
					hintedRate = (gint) gst_util_uint64_scale_round(96000, 1000, duration);
			}
			udev_device_unref(event);
		}
	}
	/* Keep the read and state update atomic against flush/reconfiguration. */
	if (sink->flushing || sink->stopping || !sink->configured) {
		count = 0;
		hintedRate = 0;
		polledSize = FALSE;
	}
	for (guint i = 0; i < count; ++i) {
		struct video_event *event = &events[i];
		switch (event->type) {
		case VIDEO_EVENT_SIZE_CHANGED:
			if (event->u.size.w > 0 && event->u.size.h > 0) {
				gint aspect = event->u.size.aspect_ratio == VIDEO_FORMAT_4_3 ? 2 : 3;
				sink->width = event->u.size.w;
				sink->height = event->u.size.h;
				sink->aspect = aspect;
				sizeChanged = TRUE;
			}
			break;
		case VIDEO_EVENT_FRAME_RATE_CHANGED:
			hintedRate = (gint) event->u.frame_rate;
			break;
		case DREAM_VIDEO_EVENT_PROGRESSIVE: {
			gint progressive = (gint) event->u.frame_rate;
			sink->progressive = progressive;
			progressiveChanged = TRUE;
			break;
		}
		case DREAM_VIDEO_EVENT_PTS_VALID: {
			sink->ptsValid = TRUE;
			guint64 pts = DREAM_VIDEO_PTS_NONE;
			if (clock && clock == sink->latencyClock && GST_CLOCK_TIME_IS_VALID(clockTime) &&
				GST_CLOCK_TIME_IS_VALID(sink->latencyClockTime) && clockTime >= sink->latencyClockTime &&
				dream_video_device_ioctl(&sink->device, VIDEO_GET_PTS, (uintptr_t) &pts) == 0 && pts != DREAM_VIDEO_PTS_NONE) {
				guint64 ticks = ((pts & DREAM_VIDEO_PTS_MASK) - sink->latencyPts) & DREAM_VIDEO_PTS_MASK;
				GstClockTime elapsed = clockTime - sink->latencyClockTime;
				GstClockTime decoded = gst_util_uint64_scale(ticks, 100000, 9);
				/* Accept one unambiguous forward 33-bit wrap, not a backwards
				 * timestamp or unsigned underflow masquerading as huge latency. */
				if (ticks <= DREAM_VIDEO_PTS_MASK / 2 && elapsed >= decoded && elapsed - decoded <= 9 * GST_SECOND) {
					sink->latency = elapsed - decoded;
					latencyChanged = TRUE;
				}
			}
			GST_DEBUG_OBJECT(sink, "Decoder PTS valid");
			break;
		}
		case DREAM_VIDEO_EVENT_DISCONTINUITY:
			invalidate_stream(sink, TRUE);
			discontinuity = TRUE;
			if (dream_video_device_ioctl(&sink->device, VIDEO_CLEAR_BUFFER, 0) < 0)
				GST_WARNING_OBJECT(sink, "Clear after discontinuity: %s", g_strerror(errno));
			GST_INFO_OBJECT(sink, "Decoder discontinuity: discard pending data, await keyframe");
			/* Remaining events refer to the discarded decoder generation. */
			count = i + 1;
			break;
		default:
			GST_LOG_OBJECT(sink, "Decoder event %d", event->type);
			break;
		}
	}
	if (polledSize && !discontinuity) {
		sizeChanged |= sink->width != polledWidth || sink->height != polledHeight || sink->aspect != polledAspect;
		sink->width = polledWidth;
		sink->height = polledHeight;
		sink->aspect = polledAspect;
	}
	if (hintedRate > 0) {
		rateChanged = sink->frameRate != hintedRate;
		sink->frameRate = hintedRate;
	}
	width = sink->width;
	height = sink->height;
	aspect = sink->aspect;
	progressive = sink->progressive;
	g_mutex_unlock(&sink->mutex);
	g_mutex_unlock(&sink->ioMutex);
	if (clock)
		gst_object_unref(clock);
	if (pollResult < 0)
		report_failure(sink, "Decoder event poll failed");
	if (discontinuity)
		gst_dream_overlay_compositor_free_overlays_stop_syncers(sink->compositor);
	if (latencyChanged && !discontinuity)
		gst_element_post_message(GST_ELEMENT(sink), gst_message_new_latency(GST_OBJECT(sink)));
	if (sizeChanged) {
		overlay_size(sink, width, height);
		post_size(sink, "eventSizeChanged", width, height, aspect);
	}
	if (progressiveChanged)
		post_integer(sink, "eventProgressiveChanged", "progressive", progressive);
	if (rateChanged)
		post_integer(sink, "eventFrameRateChanged", "frame_rate", hintedRate);
}

static gpointer decoder_worker(gpointer data)
{
	GstDreamVideoSink *sink = data;
	DreamVideoJob *job = NULL;
	GstMapInfo map = GST_MAP_INFO_INIT;
	gsize offset = 0;
	gint64 lastProgress = 0;
	gint64 nextEosPtsCheck = 0;
	guint64 eosProgressPts = DREAM_VIDEO_PTS_NONE;
	gboolean eosSent = FALSE;
	gboolean reverseEos = FALSE;
	for (;;) {
		g_mutex_lock(&sink->mutex);
		if (job && (sink->stopping || sink->flushing || sink->failed || job->generation != sink->generation)) {
			sink->queuedBytes -= job->size;
			if (map.data)
				gst_buffer_unmap(job->buffer, &map);
			memset(&map, 0, sizeof(map));
			job_free(job);
			job = NULL;
			g_cond_broadcast(&sink->changed);
		}
		if (sink->stopping) {
			g_mutex_unlock(&sink->mutex);
			break;
		}
		if (!sink->playing || sink->flushing || sink->failed || !sink->configured || (!job && g_queue_is_empty(&sink->queue))) {
			g_cond_wait_until(&sink->changed, &sink->mutex, g_get_monotonic_time() + 50000);
			lastProgress = g_get_monotonic_time(); /* Paused time is not a decoder timeout. */
			gboolean readEvents = !sink->eosDone && (!job || !job->eos || !eosSent);
			g_mutex_unlock(&sink->mutex);
			if (readEvents)
				decoder_events(sink);
			continue;
		}
		if (!job) {
			job = g_queue_pop_head(&sink->queue);
			offset = 0;
			eosSent = FALSE;
			eosProgressPts = DREAM_VIDEO_PTS_NONE;
			nextEosPtsCheck = 0;
			reverseEos = sink->segment.rate < 0;
			lastProgress = g_get_monotonic_time();
			g_cond_broadcast(&sink->changed);
		}
		guint timeoutMs = sink->ioTimeoutMs;
		g_mutex_unlock(&sink->mutex);
		if (!job->eos && !map.data && !gst_buffer_map(job->buffer, &map, GST_MAP_READ)) {
			report_failure(sink, "Cannot map encoded video buffer");
			continue;
		}
		GError *error = NULL;
		DreamWriteResult result = DREAM_WRITE_AGAIN;
		/* Writable readiness is the driver's backpressure contract, even
		 * when SET_FRAME itself would accept more bytes. Process priority
		 * events before using that readiness, as the original writer does. */
		int writable = job->eos ? 1 : dream_video_device_wait(&sink->device, FALSE, 20);
		if (writable < 0) {
			report_failure(sink, "Decoder writable poll failed");
			continue;
		}
		if (!job->eos)
			decoder_events(sink);
		GstClock *clock = !job->eos && writable ? gst_element_get_clock(GST_ELEMENT(sink)) : NULL;
		GstClockTime clockTime = clock ? gst_clock_get_internal_time(clock) : GST_CLOCK_TIME_NONE;
		GstClock *previousClock = NULL;
		gboolean sizeChanged = FALSE;
		gint aspect = 0;
		g_mutex_lock(&sink->ioMutex);
		g_mutex_lock(&sink->mutex);
		gboolean valid = !sink->stopping && !sink->flushing && !sink->failed && sink->playing && job->generation == sink->generation;
		if (valid && writable && !job->eos && !offset && job->width && job->height &&
			(job->width != sink->decoderInfo.width || job->height != sink->decoderInfo.height)) {
			/* Converted Spark data needs the actual picture size in SYSINFO
			 * before its first write, even when upstream caps have no size. */
			DreamVideoSysinfo info = sink->decoderInfo;
			info.width = job->width;
			info.height = job->height;
			if (!dream_video_device_configure(&sink->device, &info, sink->codec.streamType, FALSE, &error)) {
				result = DREAM_WRITE_ERROR;
			} else {
				sink->decoderInfo = info;
				sink->width = job->width;
				sink->height = job->height;
				sink->aspect = aspect = (guint64) job->width * 3 > (guint64) job->height * 4 ? 3 : 2;
				sink->ptsValid = FALSE;
				sink->latencyClockTime = GST_CLOCK_TIME_NONE;
				sizeChanged = TRUE;
				GST_INFO_OBJECT(sink, "Spark picture size: %ux%u", job->width, job->height);
			}
		}
		if (valid && writable && result != DREAM_WRITE_ERROR && !job->eos && !sink->ptsValid &&
			!GST_CLOCK_TIME_IS_VALID(sink->latencyClockTime) && GST_CLOCK_TIME_IS_VALID(job->pts) &&
			GST_CLOCK_TIME_IS_VALID(clockTime)) {
			/* Anchor at the actual decoder submission, never when a buffer
			 * merely enters our queue. Keep the last estimate across flushes. */
			previousClock = sink->latencyClock;
			sink->latencyClock = gst_object_ref(clock);
			sink->latencyClockTime = clockTime;
			sink->latencyPts = dream_video_pts_from_ns(job->pts);
		}
		g_mutex_unlock(&sink->mutex);
		gsize previousOffset = offset;
		if (valid && writable && result != DREAM_WRITE_ERROR) {
			if (job->eos) {
				/* The original sends its zero-byte EOS marker after pending
				 * payload is submitted, without a separate POLLOUT wait. */
				if (!eosSent) {
					DreamWriteResult markerResult = dream_video_device_end(&sink->device, &error);
					if (markerResult == DREAM_WRITE_ERROR)
						result = DREAM_WRITE_ERROR;
					else if (markerResult == DREAM_WRITE_DONE) {
						eosSent = TRUE;
						/* Start the drain deadline at marker acceptance. A
						 * queued decoder can legitimately play for much longer
						 * than the timeout while its output PTS keeps advancing. */
						guint64 pts = DREAM_VIDEO_PTS_NONE;
						if (dream_video_device_ioctl(&sink->device, VIDEO_GET_PTS, (uintptr_t) &pts) == 0 &&
							pts != DREAM_VIDEO_PTS_NONE)
							eosProgressPts = pts & DREAM_VIDEO_PTS_MASK;
						lastProgress = g_get_monotonic_time();
						nextEosPtsCheck = lastProgress + (gint64) MIN(250U, timeoutMs / 2) * 1000;
					}
				}
			} else {
				result = dream_video_device_submit(&sink->device, map.data, map.size, &offset,
					dream_video_pts_from_ns(job->pts), &error);
			}
		}
		g_mutex_unlock(&sink->ioMutex);
		if (sizeChanged) {
			overlay_size(sink, job->width, job->height);
			post_size(sink, "eventSizeChanged", job->width, job->height, aspect);
		}
		if (previousClock)
			gst_object_unref(previousClock);
		if (clock)
			gst_object_unref(clock);
		if (!valid)
			continue;
		if (result != DREAM_WRITE_ERROR && job->eos && eosSent) {
			int ready = dream_video_device_wait(&sink->device, TRUE, 250);
			if (ready > 0)
				result = DREAM_WRITE_DONE;
			else if (ready < 0) {
				g_set_error(&error, G_FILE_ERROR, G_FILE_ERROR_IO, "EOS poll: %s", g_strerror(errno));
				result = DREAM_WRITE_ERROR;
			}
			if (result == DREAM_WRITE_AGAIN) {
				gint64 now = g_get_monotonic_time();
				g_mutex_lock(&sink->ioMutex);
				g_mutex_lock(&sink->mutex);
				valid = !sink->stopping && !sink->flushing && !sink->failed && sink->playing && job->generation == sink->generation;
				g_mutex_unlock(&sink->mutex);
				if (valid && now >= nextEosPtsCheck) {
					guint64 pts = DREAM_VIDEO_PTS_NONE;
					if (dream_video_device_ioctl(&sink->device, VIDEO_GET_PTS, (uintptr_t) &pts) == 0 &&
						pts != DREAM_VIDEO_PTS_NONE) {
						pts &= DREAM_VIDEO_PTS_MASK;
						guint64 advance = (reverseEos ? eosProgressPts - pts : pts - eosProgressPts) & DREAM_VIDEO_PTS_MASK;
						if (eosProgressPts == DREAM_VIDEO_PTS_NONE)
							eosProgressPts = pts;
						else if (advance && advance <= DREAM_VIDEO_PTS_MASK / 2) {
							eosProgressPts = pts;
							lastProgress = now;
						}
					}
					nextEosPtsCheck = now + (gint64) MIN(250U, timeoutMs / 2) * 1000;
				}
				g_mutex_unlock(&sink->ioMutex);
				if (!valid)
					continue; /* Pause/flush/stop is not a decoder stall. */
			}
		}
		if (offset != previousOffset)
			lastProgress = g_get_monotonic_time();
		if (result == DREAM_WRITE_AGAIN && job->eos && !eosSent) {
			g_mutex_lock(&sink->mutex);
			valid = !sink->stopping && !sink->flushing && !sink->failed &&
				sink->playing && job->generation == sink->generation;
			g_mutex_unlock(&sink->mutex);
			if (!valid)
				continue;
		}
		if (result == DREAM_WRITE_AGAIN && g_get_monotonic_time() - lastProgress > (gint64) timeoutMs * 1000) {
			g_set_error_literal(&error, G_FILE_ERROR, G_FILE_ERROR_FAILED, "Decoder made no progress before timeout");
			result = DREAM_WRITE_ERROR;
		}
		if (result == DREAM_WRITE_ERROR) {
			report_failure(sink, error ? error->message : "Unknown decoder error");
			g_clear_error(&error);
			continue;
		}
		if (result == DREAM_WRITE_DONE) {
			g_mutex_lock(&sink->mutex);
			if (job->generation == sink->generation) {
				if (job->eos)
					sink->eosDone = TRUE;
				else {
					++sink->submittedFrames;
					sink->submittedBytes += job->size;
				}
			}
			sink->queuedBytes -= job->size;
			g_cond_broadcast(&sink->changed);
			g_mutex_unlock(&sink->mutex);
			GST_LOG_OBJECT(sink, "Submitted %" G_GSIZE_FORMAT " bytes%s generation %" G_GUINT64_FORMAT
				" PTS %" GST_TIME_FORMAT, job->size, job->eos ? " (EOS)" : "", job->generation,
				GST_TIME_ARGS(job->eos ? GST_CLOCK_TIME_NONE : job->pts));
			if (map.data)
				gst_buffer_unmap(job->buffer, &map);
			memset(&map, 0, sizeof(map));
			job_free(job);
			job = NULL;
		} else if (!job->eos || !eosSent) {
			/* Event-only readiness or POLLOUT followed by EAGAIN must not
			 * busy-spin, nor may a transiently rejected zero-byte marker.
			 * Flush/stop wakes this bounded yield immediately. */
			if (offset == previousOffset) {
				g_mutex_lock(&sink->mutex);
				if (!sink->stopping && !sink->flushing)
					g_cond_wait_until(&sink->changed, &sink->mutex, g_get_monotonic_time() + 5000);
				g_mutex_unlock(&sink->mutex);
			}
		}
		/* After the EOS marker, the original waits only for video POLLIN.
		 * Normal POLLOUT/event polling must not interfere with that drain,
		 * or invalidate completed EOS before its waiter observes it. A
		 * flush/new stream clears eosDone and enables normal polling again. */
		g_mutex_lock(&sink->mutex);
		gboolean readEvents = !sink->eosDone && (!job || !job->eos || !eosSent);
		g_mutex_unlock(&sink->mutex);
		if (readEvents)
			decoder_events(sink);
	}
	return NULL;
}

static gboolean sink_start(GstBaseSink *base)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(base);
	GError *error = NULL;
	if (!dream_video_device_open(&sink->device, &error)) {
		GST_ELEMENT_ERROR(sink, RESOURCE, OPEN_READ_WRITE, ("Cannot open Dream video decoder"),
			("%s", error ? error->message : "Unknown open error"));
		g_clear_error(&error);
		return FALSE;
	}
	g_mutex_lock(&sink->mutex);
	sink->started = TRUE;
	sink->stopping = sink->failed = sink->unlocked = sink->flushing = FALSE;
	sink->playing = sink->configured = FALSE;
	sink->independentPts = FALSE;
	sink->submittedFrames = sink->submittedBytes = 0;
	invalidate_stream(sink, FALSE);
	g_mutex_unlock(&sink->mutex);
	sink->worker = g_thread_try_new("dream-video", decoder_worker, sink, &error);
	if (!sink->worker) {
		GST_ELEMENT_ERROR(sink, RESOURCE, FAILED, ("Cannot start Dream video worker"), ("%s", error->message));
		g_clear_error(&error);
		dream_video_device_close(&sink->device);
		sink->started = FALSE;
		return FALSE;
	}
	GST_INFO_OBJECT(sink, "Source-built Dream video sink started (%s)", PACKAGE_VERSION);
	return TRUE;
}

static gboolean sink_stop(GstBaseSink *base)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(base);
	gst_dream_overlay_compositor_free_overlays_stop_syncers(sink->compositor);
	g_mutex_lock(&sink->mutex);
	sink->stopping = TRUE;
	sink->playing = FALSE;
	invalidate_stream(sink, FALSE);
	g_mutex_unlock(&sink->mutex);
	dream_video_device_wake(&sink->device);
	if (sink->worker) {
		g_thread_join(sink->worker);
		sink->worker = NULL;
	}
	g_mutex_lock(&sink->ioMutex);
	dream_video_device_close(&sink->device);
	g_mutex_unlock(&sink->ioMutex);
	g_mutex_lock(&sink->mutex);
	dream_codec_clear(&sink->codec);
	gst_caps_replace(&sink->caps, NULL);
	sink->configured = sink->started = FALSE;
	sink->queuedBytes = 0;
	g_mutex_unlock(&sink->mutex);
	return TRUE;
}

static gboolean sink_set_caps(GstBaseSink *base, GstCaps *caps)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(base);
	DreamCodec codec;
	GError *error = NULL;
	dream_codec_init(&codec);
	if (!dream_codec_configure(&codec, caps, &error)) {
		GST_ELEMENT_ERROR(sink, STREAM, FORMAT, ("Unsupported encoded video format"), ("%s", error->message));
		g_clear_error(&error);
		return FALSE;
	}
	g_mutex_lock(&sink->mutex);
	if (sink->caps && gst_caps_is_equal(sink->caps, caps)) {
		g_mutex_unlock(&sink->mutex);
		dream_codec_clear(&codec);
		return TRUE;
	}
	sink->flushing = TRUE;
	invalidate_stream(sink, FALSE);
	g_mutex_unlock(&sink->mutex);
	dream_video_device_wake(&sink->device);
	DreamVideoSysinfo info = {0};
	info.format = codec.format;
	info.width = codec.width;
	info.height = codec.height;
	info.rate = codec.rate;
	g_mutex_lock(&sink->ioMutex);
	g_mutex_lock(&sink->mutex);
	gboolean paused = !sink->playing;
	g_mutex_unlock(&sink->mutex);
	gboolean configured = dream_video_device_configure(&sink->device, &info, codec.streamType, paused, &error);
	if (!configured) {
		g_mutex_unlock(&sink->ioMutex);
		report_failure(sink, error ? error->message : "Decoder configuration failed");
		g_clear_error(&error);
		dream_codec_clear(&codec);
		return FALSE;
	}
	g_mutex_lock(&sink->mutex);
	dream_codec_clear(&sink->codec);
	sink->codec = codec;
	sink->decoderInfo = info;
	gst_caps_replace(&sink->caps, caps);
	sink->width = (gint) codec.width;
	sink->height = (gint) codec.height;
	sink->aspect = ((guint64) codec.width * 3 > (guint64) codec.height * 4) ? 3 : 2;
	sink->frameRate = codec.rate ? (gint) gst_util_uint64_scale_round(96000, 1000, codec.rate) : 0;
	sink->configured = TRUE;
	sink->flushing = FALSE;
	g_cond_broadcast(&sink->changed);
	gint width = sink->width, height = sink->height, aspect = sink->aspect, rate = sink->frameRate;
	g_mutex_unlock(&sink->mutex);
	g_mutex_unlock(&sink->ioMutex);
	overlay_size(sink, width, height);
	GST_INFO_OBJECT(sink, "Configured %" GST_PTR_FORMAT " (format=%u stream-type=%u duration96k=%u)",
		caps, codec.format, codec.streamType, codec.rate);
	post_size(sink, "eventSizeAvail", width, height, aspect);
	post_integer(sink, "eventFrameRateAvail", "frame_rate", rate);
	post_integer(sink, "eventProgressiveAvail", "progressive", -1);
	return TRUE;
}

static GstFlowReturn sink_prepare(GstBaseSink *base, GstBuffer *buffer)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(base);
	GError *error = NULL;
	/* GstBaseSink invokes prepare once for preroll and again before render.
	 * A strong reference avoids both duplicate submission and pointer reuse. */
	g_mutex_lock(&sink->mutex);
	gboolean alreadyPrepared = sink->preparedBuffer == buffer;
	g_mutex_unlock(&sink->mutex);
	if (alreadyPrepared)
		return GST_FLOW_OK;
	/* DISCONT can accompany a non-flushing Matroska segment correction for
	 * reordered startup pictures. It does not invalidate decoder references:
	 * clearing here loses the first GOP until the next keyframe. Stream/caps
	 * changes, flushes and actual decoder discontinuity events reset state. */
	if (GST_BUFFER_FLAG_IS_SET(buffer, GST_BUFFER_FLAG_DISCONT))
		GST_DEBUG_OBJECT(sink, "Buffer discontinuity: retain decoder reference pictures");
	g_mutex_lock(&sink->mutex);
	if (!sink->configured || sink->failed || sink->flushing || sink->stopping) {
		GstFlowReturn result = sink->failed ? GST_FLOW_ERROR : !sink->configured ? GST_FLOW_NOT_NEGOTIATED : GST_FLOW_FLUSHING;
		g_mutex_unlock(&sink->mutex);
		return result;
	}
	if (!gst_buffer_get_size(buffer) || ((sink->needKeyframe || sink->segment.rate < 0) &&
		GST_BUFFER_FLAG_IS_SET(buffer, GST_BUFFER_FLAG_DELTA_UNIT))) {
		g_mutex_unlock(&sink->mutex);
		return GST_FLOW_OK;
	}
	guint64 generation = sink->generation;
	guint64 cancelGeneration = sink->cancelGeneration;
	while (!sink->failed && !sink->stopping && !sink->flushing && generation == sink->generation) {
		if (sink->unlocked) {
			/* prepare holds PREROLL_LOCK. An ordinary pause must publish a
			 * retained preroll buffer, not stop upstream with FLOW_FLUSHING.
			 * The parent cannot clear unlock until this wait releases its lock. */
			g_mutex_unlock(&sink->mutex);
			GstFlowReturn result = gst_base_sink_wait_preroll(base);
			g_mutex_lock(&sink->mutex);
			if (result != GST_FLOW_OK) {
				g_mutex_unlock(&sink->mutex);
				return result;
			}
		} else if (sink->queuedBytes >= sink->maxQueuedBytes || sink->queue.length >= sink->maxQueuedFrames) {
			if (base->need_preroll) {
				/* PAUSED may already be pending before this buffer arrives.
				 * Let BaseSink retain it without growing the full queue; it
				 * calls prepare again after PLAYING, before rendering it. */
				g_mutex_unlock(&sink->mutex);
				return GST_FLOW_OK;
			}
			g_cond_wait(&sink->changed, &sink->mutex);
		} else {
			break;
		}
	}
	if (sink->failed || sink->stopping || sink->flushing || cancelGeneration != sink->cancelGeneration) {
		GstFlowReturn result = sink->failed ? GST_FLOW_ERROR : GST_FLOW_FLUSHING;
		g_mutex_unlock(&sink->mutex);
		return result;
	}
	if (generation != sink->generation) {
		/* A decoder discontinuity invalidated the AU while capacity was
		 * unavailable. Drop it and keep upstream running until the next
		 * keyframe. BaseSink may prepare this same preroll buffer again. */
		gst_buffer_replace(&sink->preparedBuffer, buffer);
		g_mutex_unlock(&sink->mutex);
		return GST_FLOW_OK;
	}
	/* Legacy codecs retain picture state. Keep transformation atomic against
	 * flush/caps changes rather than copying ownership of that state. */
	GstBuffer *encoded = NULL;
	if (!dream_codec_push(&sink->codec, buffer, sink->needHeaders, &encoded, &error)) {
		g_mutex_unlock(&sink->mutex);
		GST_ELEMENT_ERROR(sink, STREAM, DECODE, ("Invalid encoded video buffer"), ("%s", error->message));
		g_clear_error(&error);
		return GST_FLOW_ERROR;
	}
	if (!queue_encoded(sink, encoded)) {
		g_mutex_unlock(&sink->mutex);
		report_failure(sink, "Cannot queue encoded frame within the byte limit");
		return GST_FLOW_ERROR;
	}
	GST_LOG_OBJECT(sink, "Queued buffer %p generation %" G_GUINT64_FORMAT " PTS %" GST_TIME_FORMAT,
		buffer, generation, GST_TIME_ARGS(GST_BUFFER_PTS(buffer)));
	gst_buffer_replace(&sink->preparedBuffer, buffer);
	sink->needHeaders = sink->needKeyframe = FALSE;
	g_cond_broadcast(&sink->changed);
	g_mutex_unlock(&sink->mutex);
	return GST_FLOW_OK;
}

static GstFlowReturn sink_render(GstBaseSink *base, GstBuffer *buffer)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(base);
	g_mutex_lock(&sink->mutex);
	GstFlowReturn result = sink->failed ? GST_FLOW_ERROR : GST_FLOW_OK;
	GstSegment segment = sink->segment;
	gst_buffer_replace(&sink->preparedBuffer, NULL);
	g_mutex_unlock(&sink->mutex);
	if (result == GST_FLOW_OK) {
		GstClock *clock = gst_element_get_clock(GST_ELEMENT(sink));
		gst_dream_overlay_compositor_set_timing(sink->compositor, &segment,
			gst_element_get_base_time(GST_ELEMENT(sink)));
		if (!gst_dream_overlay_compositor_save_overlays(sink->compositor, clock,
			(GstClockTime) decoder_time(sink), buffer)) {
			GST_ELEMENT_ERROR(sink, STREAM, FAILED, ("Video overlay could not be scheduled"), (NULL));
			result = GST_FLOW_ERROR;
		}
		if (clock)
			gst_object_unref(clock);
	}
	return result; /* prepare already queued exactly one copy of this buffer. */
}

static gboolean sink_unlock(GstBaseSink *base)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(base);
	g_mutex_lock(&sink->mutex);
	sink->unlocked = TRUE;
	g_cond_broadcast(&sink->changed);
	g_mutex_unlock(&sink->mutex);
	dream_video_device_wake(&sink->device);
	return TRUE;
}

static gboolean sink_unlock_stop(GstBaseSink *base)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(base);
	g_mutex_lock(&sink->mutex);
	sink->unlocked = FALSE;
	g_cond_broadcast(&sink->changed);
	g_mutex_unlock(&sink->mutex);
	return TRUE;
}

static gboolean sink_event(GstBaseSink *base, GstEvent *event)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(base);
	switch (GST_EVENT_TYPE(event)) {
	case GST_EVENT_STREAM_START: {
		gst_dream_overlay_compositor_free_overlays_stop_syncers(sink->compositor);
		g_mutex_lock(&sink->ioMutex);
		g_mutex_lock(&sink->mutex);
		gboolean configured = sink->configured;
		sink->independentPts = FALSE;
		invalidate_stream(sink, FALSE);
		g_mutex_unlock(&sink->mutex);
		int result = configured ? dream_video_device_ioctl(&sink->device, VIDEO_CLEAR_BUFFER, 0) : 0;
		g_mutex_unlock(&sink->ioMutex);
		dream_video_device_wake(&sink->device);
		if (result < 0) {
			report_failure(sink, "VIDEO_CLEAR_BUFFER failed on stream change");
			gst_event_unref(event);
			return FALSE;
		}
		break;
	}
	case GST_EVENT_FLUSH_START:
		gst_dream_overlay_compositor_free_overlays_stop_syncers(sink->compositor);
		g_mutex_lock(&sink->mutex);
		sink->flushing = TRUE;
		invalidate_stream(sink, FALSE);
		g_mutex_unlock(&sink->mutex);
		dream_video_device_wake(&sink->device);
		break;
	case GST_EVENT_FLUSH_STOP: {
		g_mutex_lock(&sink->ioMutex);
		int result = sink->device.videoFd < 0 ? 0 : dream_video_device_ioctl(&sink->device, VIDEO_CLEAR_BUFFER, 0);
		g_mutex_unlock(&sink->ioMutex);
		g_mutex_lock(&sink->mutex);
		invalidate_stream(sink, FALSE);
		sink->flushing = sink->unlocked = FALSE;
		g_cond_broadcast(&sink->changed);
		g_mutex_unlock(&sink->mutex);
		if (result < 0) {
			report_failure(sink, "VIDEO_CLEAR_BUFFER failed on flush");
			gst_event_unref(event);
			return FALSE;
		}
		break;
	}
	case GST_EVENT_SEGMENT: {
		gst_dream_overlay_compositor_free_overlays_stop_syncers(sink->compositor);
		const GstSegment *segment;
		gst_event_parse_segment(event, &segment);
		if (segment->format != GST_FORMAT_TIME || !isfinite(segment->rate) || segment->rate == 0 || fabs(segment->rate) > 64) {
			GST_WARNING_OBJECT(sink, "Unsupported video segment");
			gst_event_unref(event);
			return FALSE;
		}
		g_mutex_lock(&sink->mutex);
		sink->segment = *segment;
		g_mutex_unlock(&sink->mutex);
		guint fast = fabs(segment->rate) > 1 ? (guint) fabs(segment->rate) : 0;
		guint slow = fabs(segment->rate) < 1 ? (guint) MIN(64., 1. / fabs(segment->rate)) : 0;
		g_mutex_lock(&sink->ioMutex);
		int fastResult = dream_video_device_ioctl(&sink->device, VIDEO_FAST_FORWARD, fast);
		int slowResult = dream_video_device_ioctl(&sink->device, VIDEO_SLOWMOTION, slow);
		g_mutex_unlock(&sink->ioMutex);
		if (fastResult < 0 || slowResult < 0)
			GST_WARNING_OBJECT(sink, "Driver did not accept trickmode rate %.3f", segment->rate);
		GST_DEBUG_OBJECT(sink, "Segment rate %.3f start %" GST_TIME_FORMAT,
			segment->rate, GST_TIME_ARGS(segment->start));
		break;
	}
	default:
		break;
	}
	return GST_BASE_SINK_CLASS(gst_dream_video_sink_parent_class)->event(base, event);
}

static GstFlowReturn sink_wait_event(GstBaseSink *base, GstEvent *event)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(base);
	/* The parent completes initial EOS-only preroll and waits for PLAYING.
	 * Draining before this call would deadlock an empty PAUSED pipeline. */
	GstFlowReturn result = GST_BASE_SINK_CLASS(gst_dream_video_sink_parent_class)->wait_event(base, event);
	if (result != GST_FLOW_OK || GST_EVENT_TYPE(event) != GST_EVENT_EOS)
		return result;
	GST_BASE_SINK_PREROLL_UNLOCK(base);
	g_mutex_lock(&sink->mutex);
	if (!sink->started || !sink->configured) {
		g_mutex_unlock(&sink->mutex);
		GST_BASE_SINK_PREROLL_LOCK(base);
		return GST_FLOW_OK;
	}
	guint64 cancelGeneration = sink->cancelGeneration;
	for (;;) {
		while (!sink->failed && !sink->stopping && !sink->flushing && cancelGeneration == sink->cancelGeneration &&
			(sink->queuedBytes >= sink->maxQueuedBytes || sink->queue.length >= sink->maxQueuedFrames))
			g_cond_wait(&sink->changed, &sink->mutex);
		if (sink->failed || sink->stopping || sink->flushing || cancelGeneration != sink->cancelGeneration) {
			result = sink->failed ? GST_FLOW_ERROR : GST_FLOW_FLUSHING;
			g_mutex_unlock(&sink->mutex);
			GST_BASE_SINK_PREROLL_LOCK(base);
			return result;
		}
		guint64 generation = sink->generation;
		GstBuffer *delayed = NULL;
		GError *error = NULL;
		if (!dream_codec_drain(&sink->codec, &delayed, &error) || !queue_encoded(sink, delayed)) {
			g_mutex_unlock(&sink->mutex);
			report_failure(sink, error ? error->message : "Cannot queue the final delayed video frame");
			g_clear_error(&error);
			GST_BASE_SINK_PREROLL_LOCK(base);
			return GST_FLOW_ERROR;
		}
		DreamVideoJob *job = g_try_new0(DreamVideoJob, 1);
		if (!job) {
			g_mutex_unlock(&sink->mutex);
			report_failure(sink, "Cannot allocate decoder EOS marker");
			GST_BASE_SINK_PREROLL_LOCK(base);
			return GST_FLOW_ERROR;
		}
		job->eos = TRUE;
		job->generation = generation;
		sink->eosDone = FALSE;
		g_queue_push_tail(&sink->queue, job);
		g_cond_broadcast(&sink->changed);
		g_mutex_unlock(&sink->mutex);
		GST_BASE_SINK_PREROLL_LOCK(base);
		for (;;) {
			/* Serialized sink events enter with PREROLL_LOCK held. Allow pause,
			 * flush and shutdown to acquire it while the hardware drains. */
			GST_BASE_SINK_PREROLL_UNLOCK(base);
			g_mutex_lock(&sink->mutex);
			while (!sink->eosDone && !sink->failed && !sink->stopping && !sink->flushing &&
				!sink->unlocked && generation == sink->generation)
				g_cond_wait(&sink->changed, &sink->mutex);
			gboolean done = sink->eosDone && generation == sink->generation;
			gboolean cancelled = sink->stopping || sink->flushing || cancelGeneration != sink->cancelGeneration;
			gboolean recovered = generation != sink->generation;
			gboolean failed = sink->failed;
			g_mutex_unlock(&sink->mutex);
			GST_BASE_SINK_PREROLL_LOCK(base);
			if (failed)
				return GST_FLOW_ERROR;
			if (cancelled || base->flushing)
				return GST_FLOW_FLUSHING;
			if (done)
				return GST_FLOW_OK;
			if (recovered) {
				/* VIDEO_CLEAR_BUFFER also discarded the EOS marker. Requeue
				 * it after recovery so upstream still receives normal EOS. */
				GST_BASE_SINK_PREROLL_UNLOCK(base);
				g_mutex_lock(&sink->mutex);
				break;
			}
			/* An ordinary pause must retain queued frames/EOS, not lose the end
			 * event. The base helper handles the next resume or a real flush. */
			result = gst_base_sink_wait_preroll(base);
			if (result != GST_FLOW_OK)
				return result;
		}
	}
}

static gint64 decoder_time(GstDreamVideoSink *sink)
{
	guint64 pts = DREAM_VIDEO_PTS_NONE;
	g_mutex_lock(&sink->ioMutex);
	g_mutex_lock(&sink->mutex);
	gboolean valid = sink->started && sink->configured && sink->ptsValid && !sink->flushing;
	g_mutex_unlock(&sink->mutex);
	if (!valid || dream_video_device_ioctl(&sink->device, VIDEO_GET_PTS, (uintptr_t) &pts) < 0)
		pts = DREAM_VIDEO_PTS_NONE;
	g_mutex_unlock(&sink->ioMutex);
	return (gint64) dream_video_pts_to_ns(pts);
}

static gpointer overlay_compositor(GstDreamVideoSink *sink)
{
	return sink->compositor; /* Borrowed pointer, as in the original action signal. */
}

static gboolean sink_propose_allocation(GstBaseSink *base, GstQuery *query)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(base);
	g_mutex_lock(&sink->mutex);
	gint width = sink->width, height = sink->height;
	g_mutex_unlock(&sink->mutex);
	GstStructure *parameters = gst_structure_new("GstVideoOverlayCompositionMeta",
		"width", G_TYPE_UINT, (guint) MAX(0, width), "height", G_TYPE_UINT, (guint) MAX(0, height), NULL);
	gst_query_add_allocation_meta(query, GST_VIDEO_OVERLAY_COMPOSITION_META_API_TYPE, parameters);
	gst_structure_free(parameters);
	return TRUE;
}

static gboolean sink_query(GstElement *element, GstQuery *query)
{
	if (GST_QUERY_TYPE(query) == GST_QUERY_POSITION) {
		GstFormat format;
		gst_query_parse_position(query, &format, NULL);
		gint64 position = decoder_time(GST_DREAM_VIDEO_SINK(element));
		if (format == GST_FORMAT_TIME && position >= 0) {
			gst_query_set_position(query, format, position);
			return TRUE;
		}
	}
	if (GST_QUERY_TYPE(query) == GST_QUERY_LATENCY) {
		gboolean live, upstreamLive;
		GstClockTime minimum, maximum;
		if (!gst_base_sink_query_latency(GST_BASE_SINK(element), &live, &upstreamLive, &minimum, &maximum))
			return FALSE;
		GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(element);
		g_mutex_lock(&sink->mutex);
		GstClockTime latency = sink->latency;
		g_mutex_unlock(&sink->mutex);
		/* Original contract: preserve the base sink's live flag, but report
		 * the measured decoder latency as both bounds, not an added delay. */
		gst_query_set_latency(query, live, latency, latency);
		return TRUE;
	}
	return GST_ELEMENT_CLASS(gst_dream_video_sink_parent_class)->query(element, query);
}

static GstStateChangeReturn sink_change_state(GstElement *element, GstStateChange transition)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(element);
	GstStateChangeReturn stateResult = GST_STATE_CHANGE_SUCCESS;
	if (transition == GST_STATE_CHANGE_PAUSED_TO_READY) {
		/* Pad deactivation waits for serialized EOS to leave the stream lock.
		 * The parent's unlock/unlock_stop pulse alone can be missed by our
		 * worker wait; publish persistent cancellation before deactivation. */
		g_mutex_lock(&sink->mutex);
		sink->flushing = TRUE;
		invalidate_stream(sink, FALSE);
		g_mutex_unlock(&sink->mutex);
		dream_video_device_wake(&sink->device);
	}
	if (transition == GST_STATE_CHANGE_PLAYING_TO_PAUSED || transition == GST_STATE_CHANGE_PAUSED_TO_PLAYING) {
		gboolean playing = transition == GST_STATE_CHANGE_PAUSED_TO_PLAYING;
		if (!playing) {
			gst_dream_overlay_compositor_set_paused(sink->compositor, TRUE);
			/* Stop new worker submissions before the parent unlocks the
			 * streaming task, but freeze the decoder only after BaseSink has
			 * entered its paused/preroll state, matching the original sink. */
			g_mutex_lock(&sink->mutex);
			sink->playing = FALSE;
			g_cond_broadcast(&sink->changed);
			g_mutex_unlock(&sink->mutex);
			dream_video_device_wake(&sink->device);
			stateResult = GST_ELEMENT_CLASS(gst_dream_video_sink_parent_class)->change_state(element, transition);
			if (stateResult == GST_STATE_CHANGE_FAILURE)
				return stateResult;
		}
		g_mutex_lock(&sink->ioMutex);
		g_mutex_lock(&sink->mutex);
		sink->playing = playing;
		gboolean configured = sink->configured;
		g_cond_broadcast(&sink->changed);
		g_mutex_unlock(&sink->mutex);
		int result = configured ? dream_video_device_ioctl(&sink->device, playing ? VIDEO_CONTINUE : VIDEO_FREEZE, 0) : 0;
		g_mutex_unlock(&sink->ioMutex);
		dream_video_device_wake(&sink->device);
		if (result < 0) {
			report_failure(sink, playing ? "VIDEO_CONTINUE failed" : "VIDEO_FREEZE failed");
			return GST_STATE_CHANGE_FAILURE;
		}
		if (playing)
			gst_dream_overlay_compositor_set_paused(sink->compositor, FALSE);
	}
	return transition == GST_STATE_CHANGE_PLAYING_TO_PAUSED ? stateResult :
		GST_ELEMENT_CLASS(gst_dream_video_sink_parent_class)->change_state(element, transition);
}

static void sink_set_property(GObject *object, guint property, const GValue *value, GParamSpec *spec)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(object);
	if (GST_STATE(sink) > GST_STATE_READY) {
		GST_WARNING_OBJECT(sink, "Property %s can only change in NULL/READY", spec->name);
		return;
	}
	g_mutex_lock(&sink->mutex);
	switch (property) {
	case PROP_QUEUE_BYTES: sink->maxQueuedBytes = g_value_get_uint64(value); break;
	case PROP_QUEUE_FRAMES: sink->maxQueuedFrames = g_value_get_uint(value); break;
	case PROP_IO_TIMEOUT: sink->ioTimeoutMs = g_value_get_uint(value); break;
	default: G_OBJECT_WARN_INVALID_PROPERTY_ID(object, property, spec); break;
	}
	g_mutex_unlock(&sink->mutex);
}

static void sink_get_property(GObject *object, guint property, GValue *value, GParamSpec *spec)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(object);
	g_mutex_lock(&sink->mutex);
	switch (property) {
	case PROP_QUEUE_BYTES: g_value_set_uint64(value, sink->maxQueuedBytes); break;
	case PROP_QUEUE_FRAMES: g_value_set_uint(value, sink->maxQueuedFrames); break;
	case PROP_IO_TIMEOUT: g_value_set_uint(value, sink->ioTimeoutMs); break;
	case PROP_QUEUED_BYTES: g_value_set_uint64(value, sink->queuedBytes); break;
	case PROP_SUBMITTED_FRAMES: g_value_set_uint64(value, sink->submittedFrames); break;
	case PROP_SUBMITTED_BYTES: g_value_set_uint64(value, sink->submittedBytes); break;
	default: G_OBJECT_WARN_INVALID_PROPERTY_ID(object, property, spec); break;
	}
	g_mutex_unlock(&sink->mutex);
}

static void sink_finalize(GObject *object)
{
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(object);
	/* Handles a failed state transition too; normal stop has already joined. */
	if (sink->started || sink->worker)
		sink_stop(GST_BASE_SINK(sink));
	gst_dream_overlay_compositor_free_overlays_stop_syncers(sink->compositor);
	gst_object_unref(sink->compositor);
	dream_codec_clear(&sink->codec);
	gst_caps_replace(&sink->caps, NULL);
	gst_buffer_replace(&sink->preparedBuffer, NULL);
	if (sink->latencyClock)
		gst_object_unref(sink->latencyClock);
	g_cond_clear(&sink->changed);
	g_mutex_clear(&sink->mutex);
	g_mutex_clear(&sink->ioMutex);
	G_OBJECT_CLASS(gst_dream_video_sink_parent_class)->finalize(object);
}

static void gst_dream_video_sink_class_init(GstDreamVideoSinkClass *klass)
{
	GObjectClass *objectClass = G_OBJECT_CLASS(klass);
	GstElementClass *elementClass = GST_ELEMENT_CLASS(klass);
	GstBaseSinkClass *baseClass = GST_BASE_SINK_CLASS(klass);
	objectClass->set_property = sink_set_property;
	objectClass->get_property = sink_get_property;
	objectClass->finalize = sink_finalize;
	elementClass->change_state = sink_change_state;
	elementClass->query = sink_query;
	baseClass->start = sink_start;
	baseClass->stop = sink_stop;
	baseClass->set_caps = sink_set_caps;
	baseClass->prepare = sink_prepare;
	baseClass->render = sink_render;
	baseClass->event = sink_event;
	baseClass->wait_event = sink_wait_event;
	baseClass->propose_allocation = sink_propose_allocation;
	baseClass->unlock = sink_unlock;
	baseClass->unlock_stop = sink_unlock_stop;
	klass->getDecoderTime = decoder_time;
	klass->getOverlayCompositor = overlay_compositor;
	g_signal_new("get-decoder-time", G_TYPE_FROM_CLASS(klass), G_SIGNAL_RUN_LAST | G_SIGNAL_ACTION,
		G_STRUCT_OFFSET(GstDreamVideoSinkClass, getDecoderTime), NULL, NULL, g_cclosure_marshal_generic,
		G_TYPE_INT64, 0);
	g_signal_new("get-overlay-compositor", G_TYPE_FROM_CLASS(klass), G_SIGNAL_RUN_LAST | G_SIGNAL_ACTION,
		G_STRUCT_OFFSET(GstDreamVideoSinkClass, getOverlayCompositor), NULL, NULL, g_cclosure_marshal_generic,
		G_TYPE_POINTER, 0);
	GParamFlags flags = G_PARAM_READWRITE | G_PARAM_STATIC_STRINGS | GST_PARAM_MUTABLE_READY;
	g_object_class_install_property(objectClass, PROP_QUEUE_BYTES, g_param_spec_uint64("max-queue-bytes", "Queue byte limit",
		"Encoded queue backpressure threshold including in-flight bytes; at most one bounded AU may exceed it", 1048576, 268435456, 67108864, flags));
	g_object_class_install_property(objectClass, PROP_QUEUE_FRAMES, g_param_spec_uint("max-queue-frames", "Queue frame limit",
		"Maximum pending frames (plus one in flight)", 1, 512, 64, flags));
	g_object_class_install_property(objectClass, PROP_IO_TIMEOUT, g_param_spec_uint("io-timeout-ms", "Decoder progress timeout",
		"Maximum running time without decoder progress, excluding pause", 100, 120000, 10000, flags));
	g_object_class_install_property(objectClass, PROP_QUEUED_BYTES, g_param_spec_uint64("queued-bytes", "Queued bytes",
		"Pending and in-flight bytes", 0, G_MAXUINT64, 0, G_PARAM_READABLE | G_PARAM_STATIC_STRINGS));
	g_object_class_install_property(objectClass, PROP_SUBMITTED_FRAMES, g_param_spec_uint64("submitted-frames", "Submitted frames",
		"Frames fully accepted by decoder", 0, G_MAXUINT64, 0, G_PARAM_READABLE | G_PARAM_STATIC_STRINGS));
	g_object_class_install_property(objectClass, PROP_SUBMITTED_BYTES, g_param_spec_uint64("submitted-bytes", "Submitted bytes",
		"Bytes in fully submitted frames", 0, G_MAXUINT64, 0, G_PARAM_READABLE | G_PARAM_STATIC_STRINGS));
	GstCaps *caps = gst_dream_overlay_compositor_add_caps(gst_static_caps_get(&sink_template.static_caps));
	gst_element_class_add_pad_template(elementClass, gst_pad_template_new("sink", GST_PAD_SINK, GST_PAD_ALWAYS, caps));
	gst_caps_unref(caps);
	gst_element_class_set_static_metadata(elementClass, "Dreambox hardware video sink", "Sink/Video/Hardware",
		"Source-built Dreambox One/Two compressed video decoder sink", "OpenATV contributors");
	GST_DEBUG_CATEGORY_INIT(dream_video_debug, "dreamvideosink", 0, "Dream video decoder");
}

static void gst_dream_video_sink_init(GstDreamVideoSink *sink)
{
	g_mutex_init(&sink->mutex);
	g_mutex_init(&sink->ioMutex);
	g_cond_init(&sink->changed);
	g_queue_init(&sink->queue);
	dream_video_device_init(&sink->device);
	dream_codec_init(&sink->codec);
	sink->compositor = gst_dream_overlay_compositor_new();
	gst_object_ref_sink(sink->compositor);
	gst_segment_init(&sink->segment, GST_FORMAT_TIME);
	sink->maxQueuedBytes = 67108864;
	sink->maxQueuedFrames = 64;
	sink->ioTimeoutMs = 10000;
	sink->needHeaders = sink->needKeyframe = TRUE;
	sink->progressive = -1;
	sink->latency = GST_SECOND;
	sink->latencyClockTime = GST_CLOCK_TIME_NONE;
	sink->latencyPts = DREAM_VIDEO_PTS_NONE;
	gst_base_sink_set_sync(GST_BASE_SINK(sink), FALSE);
	gst_base_sink_set_async_enabled(GST_BASE_SINK(sink), TRUE);
	gst_base_sink_set_render_delay(GST_BASE_SINK(sink), GST_SECOND);
	gst_base_sink_set_last_sample_enabled(GST_BASE_SINK(sink), FALSE);
}

gboolean gst_dream_video_sink_register(GstPlugin *plugin)
{
	return gst_element_register(plugin, "dreamvideosink", GST_RANK_PRIMARY,
		GST_TYPE_DREAM_VIDEO_SINK);
}
