/* SPDX-License-Identifier: LGPL-2.1-or-later */
#include "gstdreamvideosink.h"

#include <errno.h>
#include <gst/check/gstharness.h>
#include <gst/check/gsttestclock.h>
#include <string.h>

#define TEST_CAPS "video/x-h264,parsed=(boolean)true,stream-format=byte-stream,alignment=au,width=1280,height=720,framerate=25/1"

typedef struct {
	GMutex mutex;
	GCond changed;
	guint opens, closes, frames, attempts, freezes, continues, clears, eosFrames, eosWaits;
	gboolean blocked, rejectFrames, ptsEvent, eosBlocked, discontinuityEvent, manualPtsEvent;
	gboolean emptyEventSuccess, emptySincePoll;
	guint emptyEventCalls, validEventCalls;
	guint unlocks, freezeAfterUnlock;
	guint writeWaits;
	guint configurations;
	gboolean checkSparkSize;
	guint drainNormalPolls, drainEventCalls;
	guint eosPtsReads;
	guint eosAttempts, eosRetries, eosAgainReturns;
	gint eosError, eosUnexpectedResult;
	gboolean eosRetryBlocked;
	guint64 eosPtsIncrement;
	gsize partialBytes;
	gboolean frozen, writable, eosOutstanding, eosPtsInvalid, eosPtsBackwards;
	guint64 pts, firstPts;
	guint8 marker;
	DreamVideoSysinfo info;
} TestDevice;

static void initDevice(TestDevice *device)
{
	memset(device, 0, sizeof(*device));
	device->writable = TRUE;
	g_mutex_init(&device->mutex);
	g_cond_init(&device->changed);
}

static void clearDevice(TestDevice *device)
{
	g_cond_clear(&device->changed);
	g_mutex_clear(&device->mutex);
}

static gboolean openDevice(DreamVideoDevice *device, GError **error)
{
	TestDevice *test = device->ioctlData;
	(void) error;
	g_mutex_lock(&test->mutex);
	test->opens++;
	g_mutex_unlock(&test->mutex);
	/* An impossible-to-use mock descriptor: every ioctl is intercepted. */
	device->videoFd = 42;
	device->pollFd = -1;
	return TRUE;
}

static void closeDevice(DreamVideoDevice *device)
{
	TestDevice *test = device->ioctlData;
	g_mutex_lock(&test->mutex);
	if (device->videoFd >= 0)
		test->closes++;
	g_mutex_unlock(&test->mutex);
	device->videoFd = device->pollFd = -1;
	device->playing = FALSE;
}

static int waitDevice(DreamVideoDevice *device, gboolean eos, int timeoutMs)
{
	TestDevice *test = device->ioctlData;
	gboolean ready = FALSE;
	g_mutex_lock(&test->mutex);
	if (!eos) {
		if (test->eosOutstanding)
			test->drainNormalPolls++;
		test->emptySincePoll = FALSE;
		ready = test->writable;
		if (timeoutMs > 0)
			test->writeWaits++;
		g_cond_broadcast(&test->changed);
	}
	if (eos) {
		test->eosWaits++;
		ready = !test->eosBlocked;
		if (ready)
			test->eosOutstanding = FALSE;
		g_cond_broadcast(&test->changed);
	}
	g_mutex_unlock(&test->mutex);
	/* Wakeups are bounded without using any receiver or real descriptor. */
	g_usleep(1000);
	return ready ? 1 : 0;
}

static int ioctlDevice(int fd, unsigned long request, uintptr_t argument, gpointer userData)
{
	TestDevice *test = userData;
	int result = 0;
	(void) fd;
	g_mutex_lock(&test->mutex);
	if (request == DREAM_VIDEO_SET_FRAME) {
		/* Neither payload nor EOS may be submitted between FREEZE and
		 * CONTINUE, including state transitions with a full worker queue. */
		g_assert_false(test->frozen);
		DreamVideoFrame *frame = (DreamVideoFrame *) argument;
		gsize bytes = 0;
		for (guint index = 0; index < G_N_ELEMENTS(frame->bytes); index++)
			bytes += frame->bytes[index];
		if (bytes) {
			if (test->checkSparkSize) {
				/* The converter's validated picture header must agree with
				 * decoder configuration before ANY payload reaches hardware. */
				g_assert_cmpuint(frame->bytes[0], >=, 11);
				g_assert_cmpuint(test->info.width, ==, (frame->data[0][5] << 8) | frame->data[0][6]);
				g_assert_cmpuint(test->info.height, ==, (frame->data[0][7] << 8) | frame->data[0][8]);
			}
			test->attempts++;
			if (test->blocked || test->rejectFrames) {
				errno = test->rejectFrames ? ENOMEM : EAGAIN;
				result = -1;
			} else {
				g_assert_true(test->writable);
				test->frames++;
				test->pts = frame->pts;
				if (test->frames == 1)
					test->firstPts = frame->pts;
				test->ptsEvent = !test->manualPtsEvent;
				if (frame->bytes[0] > 0)
					test->marker = frame->data[0][frame->bytes[0] - 1];
				result = (int) (test->partialBytes ? MIN(bytes, test->partialBytes) : bytes);
				if ((gsize) result < bytes)
					test->writable = FALSE;
			}
		} else {
			const DreamVideoFrame empty = {0};
			g_assert_cmpmem(frame, sizeof(*frame), &empty, sizeof(empty));
			test->eosAttempts++;
			if (test->eosRetryBlocked || test->eosRetries) {
				if (test->eosRetries)
					test->eosRetries--;
				errno = test->eosAttempts % 2 ? EAGAIN : EINTR;
				if (errno == EAGAIN)
					test->eosAgainReturns++;
				result = -1;
			} else if (test->eosError) {
				errno = test->eosError;
				result = -1;
			} else if (test->eosUnexpectedResult) {
				result = test->eosUnexpectedResult;
			} else {
				test->eosFrames++;
				test->eosOutstanding = TRUE;
			}
		}
	} else if (request == DREAM_VIDEO_SET_SYSINFO) {
		test->info = *(DreamVideoSysinfo *) argument;
		test->configurations++;
	} else if (request == VIDEO_GET_PTS) {
		if (test->eosOutstanding) {
			test->eosPtsReads++;
			if (test->eosPtsIncrement)
				test->pts = (test->eosPtsBackwards ? test->pts - test->eosPtsIncrement :
					test->pts + test->eosPtsIncrement) & DREAM_VIDEO_PTS_MASK;
		}
		*(guint64 *) argument = test->eosOutstanding && test->eosPtsInvalid ? DREAM_VIDEO_PTS_NONE : test->pts;
	} else if (request == VIDEO_GET_EVENT) {
		if (test->eosOutstanding)
			test->drainEventCalls++;
		if (test->emptyEventSuccess) {
			const guint8 *bytes = (const guint8 *) argument;
			/* No second read is allowed after an empty-success sentinel in
			 * this poll cycle; every result buffer must be initialized. */
			g_assert_false(test->emptySincePoll);
			for (gsize index = 0; index < sizeof(struct video_event); index++)
				g_assert_cmpuint(bytes[index], ==, 0);
		}
		if (test->discontinuityEvent) {
			struct video_event *event = (struct video_event *) argument;
			memset(event, 0, sizeof(*event));
			event->type = DREAM_VIDEO_EVENT_DISCONTINUITY;
			test->discontinuityEvent = FALSE;
			test->validEventCalls++;
		} else if (test->ptsEvent) {
			struct video_event *event = (struct video_event *) argument;
			memset(event, 0, sizeof(*event));
			event->type = DREAM_VIDEO_EVENT_PTS_VALID;
			test->ptsEvent = FALSE;
			test->validEventCalls++;
		} else if (test->emptyEventSuccess) {
			/* Match the Dream driver: success, no writes to the argument. */
			test->emptySincePoll = TRUE;
			test->emptyEventCalls++;
		} else {
			errno = EAGAIN;
			result = -1;
		}
	} else if (request == VIDEO_GET_SIZE) {
		video_size_t *size = (video_size_t *) argument;
		size->w = 1280;
		size->h = 720;
		size->aspect_ratio = VIDEO_FORMAT_16_9;
	} else if (request == VIDEO_GET_FRAME_RATE) {
		*(guint *) argument = 25000;
	} else if (request == VIDEO_PLAY) {
		test->frozen = FALSE;
	} else if (request == VIDEO_FREEZE) {
		if (test->freezeAfterUnlock) {
			g_assert_cmpuint(test->unlocks, >=, test->freezeAfterUnlock);
			test->freezeAfterUnlock = 0;
		}
		test->frozen = TRUE;
		test->freezes++;
	} else if (request == VIDEO_CONTINUE) {
		test->frozen = FALSE;
		test->continues++;
	} else if (request == VIDEO_CLEAR_BUFFER) {
		test->clears++;
		test->eosOutstanding = FALSE;
		test->ptsEvent = FALSE;
		test->pts = 0;
	}
	g_cond_broadcast(&test->changed);
	g_mutex_unlock(&test->mutex);
	return result;
}

static GstHarness *makeHarness(TestDevice *test)
{
	GstDreamVideoSink *sink = g_object_new(GST_TYPE_DREAM_VIDEO_SINK,
		"async", FALSE, "sync", FALSE, NULL);
	GstHarness *harness;

	gst_object_ref_sink(sink);
	sink->device.ioctlCall = ioctlDevice;
	sink->device.ioctlData = test;
	sink->device.openHook = openDevice;
	sink->device.closeHook = closeDevice;
	sink->device.waitHook = waitDevice;
	harness = gst_harness_new_with_element(GST_ELEMENT(sink), "sink", NULL);
	g_assert_nonnull(harness);
	/* new_with_element is transfer-none and keeps its own reference. */
	gst_object_unref(sink);
	gst_harness_set_src_caps_str(harness, TEST_CAPS);
	gst_harness_play(harness);
	return harness;
}

static GstBuffer *makeFrame(guint8 marker, GstClockTime pts)
{
	const guint8 payload[] = {0, 0, 0, 1, 0x65, 0x88, marker};
	GstBuffer *buffer = gst_buffer_new_allocate(NULL, sizeof(payload), NULL);
	gst_buffer_fill(buffer, 0, payload, sizeof(payload));
	GST_BUFFER_PTS(buffer) = GST_BUFFER_DTS(buffer) = pts;
	GST_BUFFER_DURATION(buffer) = GST_SECOND / 25;
	return buffer;
}

static void teardownHarness(GstHarness *harness)
{
	/* A standalone sink has no GstPipeline to flush its bus at shutdown.
	 * Pending EOS/state messages otherwise keep their source alive, while
	 * the element retains the bus: break that reference cycle explicitly. */
	GstBus *bus = gst_element_get_bus(harness->element);
	if (bus) {
		gst_bus_set_flushing(bus, TRUE);
		gst_element_set_bus(harness->element, NULL);
		gst_object_unref(bus);
	}
	gst_harness_teardown(harness);
}

static void waitForCount(TestDevice *test, gboolean attempts, guint count)
{
	gint64 deadline = g_get_monotonic_time() + 3 * G_TIME_SPAN_SECOND;
	g_mutex_lock(&test->mutex);
	while ((attempts ? test->attempts : test->frames) < count) {
		if (!g_cond_wait_until(&test->changed, &test->mutex, deadline))
			break;
	}
	g_assert_cmpuint(attempts ? test->attempts : test->frames, >=, count);
	g_mutex_unlock(&test->mutex);
}

static void testStateAndPause(void)
{
	TestDevice test;
	initDevice(&test);
	GstHarness *harness = makeHarness(&test);

	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x11, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PAUSED), !=, GST_STATE_CHANGE_FAILURE);
	gst_element_get_state(harness->element, NULL, NULL, GST_SECOND);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.opens, ==, 1);
	g_assert_cmpuint(test.freezes, >, 0);
	g_assert_cmpuint(test.info.width, ==, 1280);
	g_assert_cmpuint(test.info.height, ==, 720);
	g_mutex_unlock(&test.mutex);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PLAYING), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x22, 2 * GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 2);
	teardownHarness(harness);
	g_assert_cmpuint(test.closes, ==, 1);
	g_assert_cmpuint(test.continues, >, 0);
	clearDevice(&test);
}

static void testFlushDropsBlockedFrame(void)
{
	TestDevice test;
	GstSegment segment;
	initDevice(&test);
	test.blocked = TRUE;
	GstHarness *harness = makeHarness(&test);

	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x11, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, TRUE, 1);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_start()));
	g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_stop(TRUE)));
	gst_segment_init(&segment, GST_FORMAT_TIME);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
	g_mutex_lock(&test.mutex);
	test.blocked = FALSE;
	g_mutex_unlock(&test.mutex);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x22, 2 * GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	teardownHarness(harness);
	g_assert_cmpuint(test.frames, ==, 1);
	g_assert_cmpuint(test.marker, ==, 0x22);
	g_assert_cmpuint(test.clears, >, 0);
	clearDevice(&test);
}

static void testSegmentDiscontPreservesFrames(gconstpointer data)
{
	TestDevice test;
	initDevice(&test);
	test.blocked = GPOINTER_TO_INT(data);
	GstHarness *harness = makeHarness(&test);
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(harness->element);
	gst_harness_set_src_caps_str(harness, "video/x-h265,parsed=(boolean)true,stream-format=byte-stream,alignment=au,width=3840,height=1608,framerate=24000/1001");
	guint clears = test.clears;
	guint64 generation = sink->generation;
	const GstClockTime timestamps[] = {166 * GST_MSECOND, 83 * GST_MSECOND, 0};
	/* Matroska moves the initial segment backwards as reordered pictures
	 * arrive. Its DISCONT flag is not a request to discard the first GOP. */
	for (guint index = 0; index < G_N_ELEMENTS(timestamps); index++) {
		GstSegment segment;
		gst_segment_init(&segment, GST_FORMAT_TIME);
		segment.start = segment.position = timestamps[index];
		g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
		const guint8 payload[] = {0, 0, 0, 1, index ? 0x02 : 0x26, 0x01, index};
		GstBuffer *buffer = gst_buffer_new_allocate(NULL, sizeof(payload), NULL);
		gst_buffer_fill(buffer, 0, payload, sizeof(payload));
		GST_BUFFER_PTS(buffer) = timestamps[index];
		GST_BUFFER_FLAG_SET(buffer, GST_BUFFER_FLAG_DISCONT);
		if (index)
			GST_BUFFER_FLAG_SET(buffer, GST_BUFFER_FLAG_DELTA_UNIT);
		g_assert_cmpint(gst_harness_push(harness, buffer), ==, GST_FLOW_OK);
		if (!index)
			waitForCount(&test, GPOINTER_TO_INT(data), 1);
	}
	g_mutex_lock(&test.mutex);
	test.blocked = FALSE;
	g_mutex_unlock(&test.mutex);
	waitForCount(&test, FALSE, 3);
	g_mutex_lock(&sink->mutex);
	g_assert_cmpuint(sink->generation, ==, generation);
	g_mutex_unlock(&sink->mutex);
	teardownHarness(harness);
	g_assert_cmpuint(test.frames, ==, 3);
	g_assert_cmpuint(test.firstPts, ==, 14940);
	g_assert_cmpuint(test.pts, ==, 0);
	g_assert_cmpuint(test.clears, ==, clears);
	clearDevice(&test);
}

static void testStopWhileDecoderBlocked(void)
{
	TestDevice test;
	initDevice(&test);
	test.blocked = TRUE;
	GstHarness *harness = makeHarness(&test);

	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x11, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, TRUE, 1);
	gint64 started = g_get_monotonic_time();
	teardownHarness(harness);
	g_assert_cmpint(g_get_monotonic_time() - started, <, 2 * G_TIME_SPAN_SECOND);
	g_assert_cmpuint(test.frames, ==, 0);
	g_assert_cmpuint(test.closes, ==, 1);
	clearDevice(&test);
}

static void testCapsChange(void)
{
	TestDevice test;
	initDevice(&test);
	GstHarness *harness = makeHarness(&test);

	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x11, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	gst_harness_set_src_caps_str(harness,
		"video/x-h265,parsed=(boolean)true,stream-format=byte-stream,alignment=au,width=1920,height=1080,framerate=50/1");
	const guint8 payload[] = {0, 0, 0, 1, 0x26, 1, 0x22};
	GstBuffer *buffer = gst_buffer_new_allocate(NULL, sizeof(payload), NULL);
	gst_buffer_fill(buffer, 0, payload, sizeof(payload));
	GST_BUFFER_PTS(buffer) = 2 * GST_SECOND;
	g_assert_cmpint(gst_harness_push(harness, buffer), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 2);
	teardownHarness(harness);
	g_assert_cmpuint(test.info.format, ==, 15);
	g_assert_cmpuint(test.info.width, ==, 1920);
	g_assert_cmpuint(test.info.height, ==, 1080);
	g_assert_cmpuint(test.info.rate, ==, 1920);
	clearDevice(&test);
}

static void testDecoderTimeContract(void)
{
	GstElement *sink = g_object_new(GST_TYPE_DREAM_VIDEO_SINK, NULL);
	GSignalQuery query = {0};
	guint signal = g_signal_lookup("get-decoder-time", G_OBJECT_TYPE(sink));
	gint64 time = 0;

	g_assert_cmpuint(signal, !=, 0);
	g_signal_query(signal, &query);
	g_assert_cmpuint(query.return_type, ==, G_TYPE_INT64);
	g_assert_cmpuint(query.n_params, ==, 0);
	g_signal_emit(sink, signal, 0, &time);
	g_assert_cmpint(time, ==, -1);
	gst_object_unref(sink);
}

typedef struct {
	GMutex mutex;
	GCond changed;
	GstPad *pad;
	gboolean done, result;
} PendingEos;

static gpointer sendEos(gpointer userData)
{
	PendingEos *pending = userData;
	gboolean result = gst_pad_send_event(pending->pad, gst_event_new_eos());
	g_mutex_lock(&pending->mutex);
	pending->result = result;
	pending->done = TRUE;
	g_cond_broadcast(&pending->changed);
	g_mutex_unlock(&pending->mutex);
	return NULL;
}

static GThread *startEos(GstElement *sink, PendingEos *pending)
{
	memset(pending, 0, sizeof(*pending));
	g_mutex_init(&pending->mutex);
	g_cond_init(&pending->changed);
	pending->pad = gst_element_get_static_pad(sink, "sink");
	g_assert_nonnull(pending->pad);
	return g_thread_new("test-eos", sendEos, pending);
}

static gboolean finishEos(GThread *thread, PendingEos *pending)
{
	gint64 deadline = g_get_monotonic_time() + 3 * G_TIME_SPAN_SECOND;
	gboolean result;
	g_mutex_lock(&pending->mutex);
	while (!pending->done && g_cond_wait_until(&pending->changed, &pending->mutex, deadline))
		;
	g_assert_true(pending->done);
	result = pending->result;
	g_mutex_unlock(&pending->mutex);
	g_thread_join(thread);
	gst_object_unref(pending->pad);
	g_cond_clear(&pending->changed);
	g_mutex_clear(&pending->mutex);
	return result;
}

static void waitForEosPoll(TestDevice *test)
{
	gint64 deadline = g_get_monotonic_time() + 3 * G_TIME_SPAN_SECOND;
	g_mutex_lock(&test->mutex);
	while (!test->eosWaits && g_cond_wait_until(&test->changed, &test->mutex, deadline))
		;
	g_assert_cmpuint(test->eosWaits, >, 0);
	g_mutex_unlock(&test->mutex);
}

static void waitForEosAttempts(TestDevice *test, guint count)
{
	gint64 deadline = g_get_monotonic_time() + 3 * G_TIME_SPAN_SECOND;
	g_mutex_lock(&test->mutex);
	while (test->eosAttempts < count && g_cond_wait_until(&test->changed, &test->mutex, deadline))
		;
	g_assert_cmpuint(test->eosAttempts, >=, count);
	g_mutex_unlock(&test->mutex);
}

static void waitForEmptyQueue(GstElement *sink)
{
	guint64 bytes = G_MAXUINT64;
	gint64 deadline = g_get_monotonic_time() + 3 * G_TIME_SPAN_SECOND;
	do {
		g_object_get(sink, "queued-bytes", &bytes, NULL);
		if (!bytes)
			break;
		g_usleep(1000);
	} while (g_get_monotonic_time() < deadline);
	g_assert_cmpuint(bytes, ==, 0);
}

static void waitForWritablePoll(TestDevice *test, guint count)
{
	gint64 deadline = g_get_monotonic_time() + 3 * G_TIME_SPAN_SECOND;
	g_mutex_lock(&test->mutex);
	while (test->writeWaits < count && g_cond_wait_until(&test->changed, &test->mutex, deadline))
		;
	g_assert_cmpuint(test->writeWaits, >=, count);
	g_mutex_unlock(&test->mutex);
}

/* Isolate blocking regressions so make check has a hard deadline, even if
 * a future change prevents NULL-state teardown from joining its worker. */
static gboolean runIsolated(void)
{
	if (g_test_subprocess())
		return TRUE;
	g_test_trap_subprocess(NULL, 8 * G_TIME_SPAN_SECOND, 0);
	g_test_trap_assert_passed();
	return FALSE;
}

static void testAsyncEosOnlyPreroll(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	PendingEos pending;
	GstState state = GST_STATE_NULL;
	GstSegment segment;
	initDevice(&test);
	GstDreamVideoSink *sink = g_object_new(GST_TYPE_DREAM_VIDEO_SINK, "async", TRUE, "sync", FALSE, NULL);
	sink->device.ioctlCall = ioctlDevice;
	sink->device.ioctlData = &test;
	sink->device.openHook = openDevice;
	sink->device.closeHook = closeDevice;
	sink->device.waitHook = waitDevice;
	GstBus *bus = gst_bus_new();
	gst_element_set_bus(GST_ELEMENT(sink), bus);
	g_assert_cmpint(gst_element_set_state(GST_ELEMENT(sink), GST_STATE_PAUSED), ==, GST_STATE_CHANGE_ASYNC);
	GstPad *pad = gst_element_get_static_pad(GST_ELEMENT(sink), "sink");
	GstCaps *caps = gst_caps_from_string(TEST_CAPS);
	g_assert_true(gst_pad_send_event(pad, gst_event_new_stream_start("empty-preroll")));
	g_assert_true(gst_pad_send_event(pad, gst_event_new_caps(caps)));
	gst_caps_unref(caps);
	gst_segment_init(&segment, GST_FORMAT_TIME);
	g_assert_true(gst_pad_send_event(pad, gst_event_new_segment(&segment)));
	GThread *thread = startEos(GST_ELEMENT(sink), &pending);
	/* EOS, not a buffer, must complete the initial PAUSED preroll. The EOS
 * event itself may legitimately remain blocked until PLAYING. */
	g_assert_cmpint(gst_element_get_state(GST_ELEMENT(sink), &state, NULL, 2 * GST_SECOND), ==, GST_STATE_CHANGE_SUCCESS);
	g_assert_cmpint(state, ==, GST_STATE_PAUSED);
	g_assert_cmpint(gst_element_set_state(GST_ELEMENT(sink), GST_STATE_PLAYING), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_true(finishEos(thread, &pending));
	GstMessage *message = gst_bus_timed_pop_filtered(bus, GST_SECOND, GST_MESSAGE_EOS | GST_MESSAGE_ERROR);
	g_assert_nonnull(message);
	g_assert_cmpint(GST_MESSAGE_TYPE(message), ==, GST_MESSAGE_EOS);
	gst_message_unref(message);
	g_assert_cmpint(gst_element_set_state(GST_ELEMENT(sink), GST_STATE_NULL), !=, GST_STATE_CHANGE_FAILURE);
	gst_element_set_bus(GST_ELEMENT(sink), NULL);
	gst_object_unref(pad);
	gst_object_unref(bus);
	gst_object_unref(sink);
	g_assert_cmpuint(test.frames, ==, 0);
	g_assert_cmpuint(test.closes, ==, 1);
	clearDevice(&test);
}

typedef struct {
	GstPad *pad;
	GstBuffer *buffer;
	GstFlowReturn result;
} PendingBuffer;

static gpointer sendBuffer(gpointer userData)
{
	PendingBuffer *pending = userData;
	pending->result = gst_pad_chain(pending->pad, pending->buffer);
	return NULL;
}

static gboolean (*originalUnlock)(GstBaseSink *base);

static gboolean recordUnlock(GstBaseSink *base)
{
	TestDevice *test = GST_DREAM_VIDEO_SINK(base)->device.ioctlData;
	g_mutex_lock(&test->mutex);
	test->unlocks++;
	g_mutex_unlock(&test->mutex);
	return originalUnlock(base);
}

static void testAsyncBufferPrerollOnce(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	PendingBuffer pending;
	GstSegment segment;
	initDevice(&test);
	GstDreamVideoSink *sink = g_object_new(GST_TYPE_DREAM_VIDEO_SINK, "async", TRUE, "sync", FALSE, NULL);
	sink->device.ioctlCall = ioctlDevice;
	sink->device.ioctlData = &test;
	sink->device.openHook = openDevice;
	sink->device.closeHook = closeDevice;
	sink->device.waitHook = waitDevice;
	g_assert_cmpint(gst_element_set_state(GST_ELEMENT(sink), GST_STATE_PAUSED), ==, GST_STATE_CHANGE_ASYNC);
	pending.pad = gst_element_get_static_pad(GST_ELEMENT(sink), "sink");
	pending.buffer = makeFrame(0x42, GST_SECOND);
	pending.result = GST_FLOW_ERROR;
	GstCaps *caps = gst_caps_from_string(TEST_CAPS);
	g_assert_true(gst_pad_send_event(pending.pad, gst_event_new_stream_start("single-preroll")));
	g_assert_true(gst_pad_send_event(pending.pad, gst_event_new_caps(caps)));
	gst_caps_unref(caps);
	gst_segment_init(&segment, GST_FORMAT_TIME);
	g_assert_true(gst_pad_send_event(pending.pad, gst_event_new_segment(&segment)));
	GThread *thread = g_thread_new("test-preroll", sendBuffer, &pending);
	g_assert_cmpint(gst_element_get_state(GST_ELEMENT(sink), NULL, NULL, 2 * GST_SECOND), ==, GST_STATE_CHANGE_SUCCESS);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.frames, ==, 0);
	g_mutex_unlock(&test.mutex);
	g_assert_cmpint(gst_element_set_state(GST_ELEMENT(sink), GST_STATE_PLAYING), !=, GST_STATE_CHANGE_FAILURE);
	g_thread_join(thread);
	g_assert_cmpint(pending.result, ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	waitForEmptyQueue(GST_ELEMENT(sink));
	g_assert_cmpint(gst_element_set_state(GST_ELEMENT(sink), GST_STATE_NULL), !=, GST_STATE_CHANGE_FAILURE);
	gst_object_unref(pending.pad);
	gst_object_unref(sink);
	/* GstBaseSink can prepare the same first buffer for both preroll and
	 * rendering. The hardware must receive exactly one access unit. */
	g_assert_cmpuint(test.frames, ==, 1);
	g_assert_cmpuint(test.marker, ==, 0x42);
	g_assert_cmpuint(test.closes, ==, 1);
	clearDevice(&test);
}

static void testFullQueuePause(gconstpointer userData)
{
	if (!runIsolated())
		return;
	const gboolean pauseFirst = GPOINTER_TO_INT(userData);
	TestDevice test;
	PendingBuffer pending;
	GstSegment segment;
	GstState state = GST_STATE_NULL;
	initDevice(&test);
	test.blocked = TRUE;
	GstDreamVideoSink *sink = g_object_new(GST_TYPE_DREAM_VIDEO_SINK,
		"async", TRUE, "sync", FALSE, "max-queue-frames", 64, NULL);
	/* The isolated process can observe the real parent's unlock callback
	 * without adding instrumentation or test hooks to production code. */
	GstBaseSinkClass *baseClass = GST_BASE_SINK_GET_CLASS(sink);
	originalUnlock = baseClass->unlock;
	baseClass->unlock = recordUnlock;
	sink->device.ioctlCall = ioctlDevice;
	sink->device.ioctlData = &test;
	sink->device.openHook = openDevice;
	sink->device.closeHook = closeDevice;
	sink->device.waitHook = waitDevice;
	g_assert_cmpint(gst_element_set_state(GST_ELEMENT(sink), GST_STATE_PAUSED), ==, GST_STATE_CHANGE_ASYNC);
	pending.pad = gst_element_get_static_pad(GST_ELEMENT(sink), "sink");
	GstCaps *caps = gst_caps_from_string(TEST_CAPS);
	g_assert_true(gst_pad_send_event(pending.pad, gst_event_new_stream_start("full-queue-pause")));
	g_assert_true(gst_pad_send_event(pending.pad, gst_event_new_caps(caps)));
	gst_caps_unref(caps);
	gst_segment_init(&segment, GST_FORMAT_TIME);
	g_assert_true(gst_pad_send_event(pending.pad, gst_event_new_segment(&segment)));

	for (guint cycle = 0; cycle < 2; cycle++) {
		g_mutex_lock(&test.mutex);
		test.blocked = TRUE;
		guint attempts = test.attempts;
		guint clears = test.clears;
		g_mutex_unlock(&test.mutex);
		/* One access unit is held by the blocked device; fill all 64
		 * pending slots before introducing the next upstream buffer. */
		if (cycle == 0) {
			/* Use a real initial preroll. Older BaseSink versions retain
			 * have_preroll on the direct NULL-to-PLAYING fast path. */
			pending.buffer = makeFrame(0x11, GST_SECOND);
			pending.result = GST_FLOW_ERROR;
			GThread *initial = g_thread_new("test-initial-preroll", sendBuffer, &pending);
			g_assert_cmpint(gst_element_get_state(GST_ELEMENT(sink), NULL, NULL, 2 * GST_SECOND), ==, GST_STATE_CHANGE_SUCCESS);
			g_assert_cmpint(gst_element_set_state(GST_ELEMENT(sink), GST_STATE_PLAYING), !=, GST_STATE_CHANGE_FAILURE);
			g_thread_join(initial);
			g_assert_cmpint(pending.result, ==, GST_FLOW_OK);
		} else {
			g_assert_cmpint(gst_pad_chain(pending.pad, makeFrame(0x11, GST_SECOND)), ==, GST_FLOW_OK);
		}
		waitForCount(&test, TRUE, attempts + 1);
		for (guint index = 0; index < 64; index++)
			g_assert_cmpint(gst_pad_chain(pending.pad, makeFrame(0x22, 2 * GST_SECOND)), ==, GST_FLOW_OK);
		g_mutex_lock(&sink->mutex);
		g_assert_cmpuint(sink->queue.length, ==, 64);
		guint64 bytes = sink->queuedBytes;
		guint64 generation = sink->generation;
		g_mutex_unlock(&sink->mutex);
		g_mutex_lock(&test.mutex);
		test.freezeAfterUnlock = test.unlocks + 1;
		g_mutex_unlock(&test.mutex);
		if (pauseFirst) {
			g_assert_cmpint(gst_element_set_state(GST_ELEMENT(sink), GST_STATE_PAUSED), !=, GST_STATE_CHANGE_FAILURE);
			/* Downward transitions need not return ASYNC. Verify the
			 * actual condition that exercises deferred preparation. */
			GST_BASE_SINK_PREROLL_LOCK(GST_BASE_SINK(sink));
			g_assert_true(GST_BASE_SINK(sink)->need_preroll);
			GST_BASE_SINK_PREROLL_UNLOCK(GST_BASE_SINK(sink));
		}
		pending.buffer = makeFrame(0x42, 3 * GST_SECOND);
		pending.result = GST_FLOW_ERROR;
		GThread *thread = g_thread_new("test-full-queue", sendBuffer, &pending);
		if (!pauseFirst) {
			/* Only this streaming thread can hold PREROLL_LOCK here.
			 * Ensure prepare is already waiting before requesting pause. */
			gint64 deadline = g_get_monotonic_time() + 2 * G_TIME_SPAN_SECOND;
			gboolean locked;
			do {
				locked = g_mutex_trylock(&GST_BASE_SINK(sink)->preroll_lock);
				if (locked) {
					GST_BASE_SINK_PREROLL_UNLOCK(GST_BASE_SINK(sink));
					g_usleep(1000);
				}
			} while (locked && g_get_monotonic_time() < deadline);
			g_assert_false(locked);
			g_assert_cmpint(gst_element_set_state(GST_ELEMENT(sink), GST_STATE_PAUSED), !=, GST_STATE_CHANGE_FAILURE);
		}
		g_assert_cmpint(gst_element_get_state(GST_ELEMENT(sink), &state, NULL, 2 * GST_SECOND), ==, GST_STATE_CHANGE_SUCCESS);
		g_assert_cmpint(state, ==, GST_STATE_PAUSED);
		/* Neither ordering may overflow the queue or clear existing data. */
		g_mutex_lock(&sink->mutex);
		g_assert_cmpuint(sink->queue.length, ==, 64);
		g_assert_cmpuint(sink->queuedBytes, ==, bytes);
		g_assert_cmpuint(sink->generation, ==, generation);
		g_mutex_unlock(&sink->mutex);
		g_mutex_lock(&test.mutex);
		g_assert_cmpuint(test.frames, ==, cycle * 66);
		g_assert_cmpuint(test.clears, ==, clears);
		test.blocked = FALSE;
		g_mutex_unlock(&test.mutex);
		g_assert_cmpint(gst_element_set_state(GST_ELEMENT(sink), GST_STATE_PLAYING), !=, GST_STATE_CHANGE_FAILURE);
		g_assert_cmpint(gst_element_get_state(GST_ELEMENT(sink), &state, NULL, 2 * GST_SECOND), ==, GST_STATE_CHANGE_SUCCESS);
		g_assert_cmpint(state, ==, GST_STATE_PLAYING);
		g_thread_join(thread);
		g_assert_cmpint(pending.result, ==, GST_FLOW_OK);
		waitForCount(&test, FALSE, (cycle + 1) * 66);
		waitForEmptyQueue(GST_ELEMENT(sink));
		g_mutex_lock(&test.mutex);
		g_assert_cmpuint(test.marker, ==, 0x42);
		g_assert_cmpuint(test.clears, ==, clears);
		g_mutex_unlock(&test.mutex);
	}
	g_assert_cmpint(gst_element_set_state(GST_ELEMENT(sink), GST_STATE_NULL), !=, GST_STATE_CHANGE_FAILURE);
	baseClass->unlock = originalUnlock;
	gst_object_unref(pending.pad);
	gst_object_unref(sink);
	g_assert_cmpuint(test.frames, ==, 132);
	g_assert_cmpuint(test.closes, ==, 1);
	clearDevice(&test);
}

static void checkTimestamp(TestDevice *test, GstHarness *harness,
	GstClockTime pts, GstClockTime dts, guint64 expected)
{
	GstBuffer *buffer = makeFrame(0x55, pts);
	GST_BUFFER_DTS(buffer) = dts;
	g_mutex_lock(&test->mutex);
	guint count = test->frames + 1;
	g_mutex_unlock(&test->mutex);
	/* Keep an independent reference: sink timing must not rewrite input. */
	g_assert_cmpint(gst_harness_push(harness, gst_buffer_ref(buffer)), ==, GST_FLOW_OK);
	waitForCount(test, FALSE, count);
	waitForEmptyQueue(harness->element);
	g_mutex_lock(&test->mutex);
	g_assert_cmpuint(test->pts, ==, expected);
	g_mutex_unlock(&test->mutex);
	g_assert_cmpuint(GST_BUFFER_PTS(buffer), ==, pts);
	g_assert_cmpuint(GST_BUFFER_DTS(buffer), ==, dts);
	gst_buffer_unref(buffer);
}

static void testDtsTimestampFallback(void)
{
	TestDevice test;
	initDevice(&test);
	GstHarness *harness = makeHarness(&test);
	checkTimestamp(&test, harness, GST_CLOCK_TIME_NONE, GST_CLOCK_TIME_NONE, DREAM_VIDEO_PTS_NONE);
	checkTimestamp(&test, harness, 0, 0, 0);
	checkTimestamp(&test, harness, GST_CLOCK_TIME_NONE, GST_SECOND, 90000);
	checkTimestamp(&test, harness, 2 * GST_SECOND, 2 * GST_SECOND, 180000);
	checkTimestamp(&test, harness, GST_CLOCK_TIME_NONE, 3 * GST_SECOND, 270000);
	teardownHarness(harness);
	clearDevice(&test);
}

static void testIndependentPtsPrecedence(gconstpointer userData)
{
	TestDevice test;
	initDevice(&test);
	GstHarness *harness = makeHarness(&test);
	GstClockTime dts = GPOINTER_TO_INT(userData) ? GST_CLOCK_TIME_NONE : GST_SECOND;
	checkTimestamp(&test, harness, 2 * GST_SECOND, dts, 180000);
	checkTimestamp(&test, harness, GST_CLOCK_TIME_NONE, 3 * GST_SECOND, DREAM_VIDEO_PTS_NONE);
	checkTimestamp(&test, harness, 4 * GST_SECOND, 4 * GST_SECOND, 360000);
	checkTimestamp(&test, harness, GST_CLOCK_TIME_NONE, 5 * GST_SECOND, DREAM_VIDEO_PTS_NONE);
	teardownHarness(harness);
	clearDevice(&test);
}

static void testTimestampStreamLifetime(void)
{
	TestDevice test;
	GstSegment segment;
	initDevice(&test);
	GstHarness *harness = makeHarness(&test);
	checkTimestamp(&test, harness, 2 * GST_SECOND, GST_SECOND, 180000);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PAUSED), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PLAYING), !=, GST_STATE_CHANGE_FAILURE);
	checkTimestamp(&test, harness, GST_CLOCK_TIME_NONE, 3 * GST_SECOND, DREAM_VIDEO_PTS_NONE);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_start()));
	g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_stop(TRUE)));
	gst_segment_init(&segment, GST_FORMAT_TIME);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
	checkTimestamp(&test, harness, GST_CLOCK_TIME_NONE, 4 * GST_SECOND, DREAM_VIDEO_PTS_NONE);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
	checkTimestamp(&test, harness, GST_CLOCK_TIME_NONE, 5 * GST_SECOND, DREAM_VIDEO_PTS_NONE);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_stream_start("new-dts-only-stream")));
	gst_harness_set_src_caps_str(harness, TEST_CAPS);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
	checkTimestamp(&test, harness, GST_CLOCK_TIME_NONE, 6 * GST_SECOND, 540000);
	teardownHarness(harness);
	clearDevice(&test);
}

static void testTimestampQueueOrder(void)
{
	TestDevice test;
	initDevice(&test);
	test.blocked = TRUE;
	GstHarness *harness = makeHarness(&test);
	GstBuffer *first = makeFrame(0x11, GST_CLOCK_TIME_NONE);
	GST_BUFFER_DTS(first) = GST_SECOND;
	g_assert_cmpint(gst_harness_push(harness, first), ==, GST_FLOW_OK);
	waitForCount(&test, TRUE, 1);
	GstBuffer *second = makeFrame(0x22, 3 * GST_SECOND);
	GST_BUFFER_DTS(second) = 2 * GST_SECOND;
	g_assert_cmpint(gst_harness_push(harness, second), ==, GST_FLOW_OK);
	g_mutex_lock(&test.mutex);
	test.blocked = FALSE;
	g_mutex_unlock(&test.mutex);
	waitForCount(&test, FALSE, 2);
	waitForEmptyQueue(harness->element);
	g_mutex_lock(&test.mutex);
	/* A later independent PTS must not disable fallback for a job that
	 * was queued before it, even while the device was blocked. */
	g_assert_cmpuint(test.firstPts, ==, 90000);
	g_assert_cmpuint(test.pts, ==, 270000);
	g_mutex_unlock(&test.mutex);
	teardownHarness(harness);
	clearDevice(&test);
}

static GstBuffer *makeH263Frame(GstClockTime pts)
{
	/* Baseline H.263 sub-QCIF P picture with all 48 macroblocks skipped.
	 * Kept small so the lifecycle test exercises state ownership, not VLCs. */
	const guint8 data[] = {0x00, 0x00, 0x80, 0x1e, 0x06, 0x0a, 0x3f, 0xff, 0xff, 0xff, 0xff, 0xff, 0xc0};
	GstBuffer *buffer = gst_buffer_new_allocate(NULL, sizeof(data), NULL);
	gst_buffer_fill(buffer, 0, data, sizeof(data));
	GST_BUFFER_PTS(buffer) = GST_BUFFER_DTS(buffer) = pts;
	GST_BUFFER_DURATION(buffer) = GST_SECOND / 25;
	return buffer;
}

static void testH263StateLifecycle(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	GstSegment segment;
	initDevice(&test);
	GstHarness *harness = makeHarness(&test);
	const gchar *caps = "video/x-h263,variant=itu,parsed=true,width=128,height=96,framerate=25/1";
	gst_harness_set_src_caps_str(harness, caps);
	for (guint index = 0; index < 2; index++) {
		g_assert_cmpint(gst_harness_push(harness, makeH263Frame((index + 1) * GST_SECOND)), ==, GST_FLOW_OK);
		waitForCount(&test, FALSE, index + 1);
	}
	g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_start()));
	g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_stop(TRUE)));
	gst_segment_init(&segment, GST_FORMAT_TIME);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
	g_assert_cmpint(gst_harness_push(harness, makeH263Frame(3 * GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 3);
	gst_harness_set_src_caps_str(harness, TEST_CAPS);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x55, 4 * GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 4);
	gst_harness_set_src_caps_str(harness, caps);
	g_assert_cmpint(gst_harness_push(harness, makeH263Frame(5 * GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 5);
	waitForEmptyQueue(harness->element);
	teardownHarness(harness);
	g_assert_cmpuint(test.frames, ==, 5);
	g_assert_cmpuint(test.info.format, ==, 7);
	g_assert_cmpuint(test.closes, ==, 1);
	clearDevice(&test);
}

static GstBuffer *makeSparkFrame(GstClockTime pts)
{
	/* Spark version 0, explicit 16x16 size, one skipped macroblock.
	 * No duration, matching flvdemux on FLV files without metadata. */
	const guint8 data[] = {0x00, 0x00, 0x80, 0x1c, 0x80, 0x08, 0x00, 0x08, 0x25, 0x20};
	GstBuffer *buffer = gst_buffer_new_allocate(NULL, sizeof(data), NULL);
	gst_buffer_fill(buffer, 0, data, sizeof(data));
	GST_BUFFER_PTS(buffer) = GST_BUFFER_DTS(buffer) = pts;
	return buffer;
}

static void testSparkSize(gconstpointer data)
{
	if (!runIsolated())
		return;
	TestDevice test;
	initDevice(&test);
	GstHarness *harness = makeHarness(&test);
	gst_harness_set_src_caps_str(harness, data);
	g_mutex_lock(&test.mutex);
	test.checkSparkSize = TRUE;
	guint configurations = test.configurations;
	guint expectedChanges = test.info.width == 16 && test.info.height == 16 ? 0 : 1;
	g_mutex_unlock(&test.mutex);
	g_assert_cmpint(gst_harness_push(harness, makeSparkFrame(0)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PAUSED), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_cmpint(gst_element_get_state(harness->element, NULL, NULL, GST_SECOND), ==, GST_STATE_CHANGE_SUCCESS);
	gst_harness_play(harness);
	g_assert_cmpint(gst_harness_push(harness, makeSparkFrame(83 * GST_MSECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 2);
	/* A seek must retain the discovered size, without another decoder
	 * restart; the next frame is converted with reset picture state. */
	g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_start()));
	g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_stop(TRUE)));
	GstSegment segment;
	gst_segment_init(&segment, GST_FORMAT_TIME);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
	g_assert_cmpint(gst_harness_push(harness, makeSparkFrame(GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 3);
	waitForEmptyQueue(harness->element);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.info.width, ==, 16);
	g_assert_cmpuint(test.info.height, ==, 16);
	g_assert_cmpuint(test.configurations, ==, configurations + expectedChanges);
	g_mutex_unlock(&test.mutex);
	teardownHarness(harness);
	clearDevice(&test);
}

static GstCaps *makeVc1Caps(void)
{
	/* Advanced-profile sequence + entry point, progressive 16x16 at 25 fps.
	 * Syntax-only fixture shared in purpose with the codec timing tests. */
	const guint8 data[] = {0x00, 0x00, 0x00, 0x01, 0x0f, 0xc2, 0x00, 0x00,
		0x70, 0x07, 0x0a, 0x00, 0x78, 0x01, 0xe8, 0x08, 0x40,
		0x00, 0x00, 0x01, 0x0e, 0x00, 0x00};
	GstCaps *caps = gst_caps_from_string("video/x-wmv,wmvversion=3,format=WVC1,width=16,height=16,framerate=25/1");
	GstBuffer *header = gst_buffer_new_allocate(NULL, sizeof(data), NULL);
	gst_buffer_fill(header, 0, data, sizeof(data));
	gst_caps_set_simple(caps, "codec_data", GST_TYPE_BUFFER, header, NULL);
	gst_buffer_unref(header);
	return caps;
}

static GstBuffer *makeVc1Frame(guint8 picture, guint8 marker, GstClockTime pts)
{
	const guint8 data[] = {0, 0, 1, 0x0d, picture, marker};
	GstBuffer *buffer = gst_buffer_new_allocate(NULL, sizeof(data), NULL);
	gst_buffer_fill(buffer, 0, data, sizeof(data));
	GST_BUFFER_PTS(buffer) = pts;
	GST_BUFFER_DURATION(buffer) = GST_SECOND / 25;
	return buffer;
}

static void testVc1DelayedDrain(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	PendingEos pending;
	initDevice(&test);
	GstHarness *harness = makeHarness(&test);
	gst_harness_set_src_caps(harness, makeVc1Caps());
	g_assert_cmpint(gst_harness_push(harness, makeVc1Frame(0xc0, 0x11, GST_SECOND)), ==, GST_FLOW_OK);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.attempts, ==, 0);
	g_mutex_unlock(&test.mutex);
	g_assert_cmpint(gst_harness_push(harness, makeVc1Frame(0x00, 0x22, GST_CLOCK_TIME_NONE)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.marker, ==, 0x11);
	g_assert_cmpuint(test.pts, ==, 90000);
	g_mutex_unlock(&test.mutex);
	g_assert_cmpint(gst_harness_push(harness, makeVc1Frame(0x80, 0x33, GST_CLOCK_TIME_NONE)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 2);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.marker, ==, 0x22);
	g_assert_cmpuint(test.pts, ==, 97200);
	g_mutex_unlock(&test.mutex);
	GThread *thread = startEos(harness->element, &pending);
	g_assert_true(finishEos(thread, &pending));
	waitForEmptyQueue(harness->element);
	teardownHarness(harness);
	g_assert_cmpuint(test.frames, ==, 3);
	g_assert_cmpuint(test.marker, ==, 0x33);
	g_assert_cmpuint(test.pts, ==, 93600);
	g_assert_cmpuint(test.eosFrames, ==, 1);
	clearDevice(&test);
}

static void testVc1NewStreamDiscardsLookahead(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	PendingEos pending;
	GstSegment segment;
	initDevice(&test);
	GstHarness *harness = makeHarness(&test);
	gst_harness_set_src_caps(harness, makeVc1Caps());
	g_assert_cmpint(gst_harness_push(harness, makeVc1Frame(0xc0, 0x11, GST_SECOND)), ==, GST_FLOW_OK);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_stream_start("replacement-vc1")));
	gst_harness_set_src_caps(harness, makeVc1Caps());
	gst_segment_init(&segment, GST_FORMAT_TIME);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
	g_assert_cmpint(gst_harness_push(harness, makeVc1Frame(0xc0, 0x44, 4 * GST_SECOND)), ==, GST_FLOW_OK);
	GThread *thread = startEos(harness->element, &pending);
	g_assert_true(finishEos(thread, &pending));
	waitForEmptyQueue(harness->element);
	teardownHarness(harness);
	g_assert_cmpuint(test.frames, ==, 1);
	g_assert_cmpuint(test.marker, ==, 0x44);
	g_assert_cmpuint(test.pts, ==, 360000);
	g_assert_cmpuint(test.eosFrames, ==, 1);
	clearDevice(&test);
}

static void testEosWithPausedQueue(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	PendingEos pending;
	initDevice(&test);
	test.blocked = TRUE;
	GstHarness *harness = makeHarness(&test);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x44, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, TRUE, 1);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PAUSED), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_cmpint(gst_element_get_state(harness->element, NULL, NULL, GST_SECOND), ==, GST_STATE_CHANGE_SUCCESS);
	GThread *thread = startEos(harness->element, &pending);
	/* Keep a queued frame across a pause; EOS must not discard it or drain
 * a frozen decoder. Resume is what permits both frame and EOS to finish. */
	g_usleep(100000);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.frames, ==, 0);
	test.blocked = FALSE;
	g_mutex_unlock(&test.mutex);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PLAYING), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_true(finishEos(thread, &pending));
	waitForEmptyQueue(harness->element);
	teardownHarness(harness);
	g_assert_cmpuint(test.frames, ==, 1);
	g_assert_cmpuint(test.marker, ==, 0x44);
	g_assert_cmpuint(test.eosFrames, ==, 1);
	clearDevice(&test);
}

static void testNewStreamDropsOldQueue(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	GstSegment segment;
	initDevice(&test);
	test.blocked = TRUE;
	GstHarness *harness = makeHarness(&test);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x11, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, TRUE, 1);
	/* A reused sink can receive a new stream with identical caps and no
	 * separate FLUSH pair. Old compressed data must not enter that stream. */
	g_assert_true(gst_harness_push_event(harness, gst_event_new_stream_start("replacement-stream")));
	gst_harness_set_src_caps_str(harness, TEST_CAPS);
	gst_segment_init(&segment, GST_FORMAT_TIME);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
	g_mutex_lock(&test.mutex);
	test.blocked = FALSE;
	g_mutex_unlock(&test.mutex);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x22, 2 * GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	waitForEmptyQueue(harness->element);
	teardownHarness(harness);
	g_assert_cmpuint(test.frames, ==, 1);
	g_assert_cmpuint(test.marker, ==, 0x22);
	g_assert_cmpuint(test.clears, >, 0);
	clearDevice(&test);
}

static void testBlockedPrepareCancellation(gconstpointer userData)
{
	if (!runIsolated())
		return;
	const gboolean recovery = GPOINTER_TO_INT(userData);
	TestDevice test;
	PendingBuffer pending;
	initDevice(&test);
	test.blocked = TRUE;
	GstHarness *harness = makeHarness(&test);
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(harness->element);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x11, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, TRUE, 1);
	for (guint index = 0; index < 64; index++)
		g_assert_cmpint(gst_harness_push(harness, makeFrame(0x22, 2 * GST_SECOND)), ==, GST_FLOW_OK);
	g_mutex_lock(&sink->mutex);
	g_assert_cmpuint(sink->queue.length, ==, 64);
	guint64 generation = sink->generation;
	guint64 cancelGeneration = sink->cancelGeneration;
	g_mutex_unlock(&sink->mutex);
	pending.pad = gst_element_get_static_pad(harness->element, "sink");
	pending.buffer = makeFrame(0x33, 3 * GST_SECOND);
	pending.result = GST_FLOW_ERROR;
	GThread *thread = g_thread_new("test-blocked-prepare", sendBuffer, &pending);
	/* A full queue and the streaming thread's PREROLL_LOCK prove prepare
	 * is waiting when the driver event or external flush is introduced. */
	gint64 deadline = g_get_monotonic_time() + 2 * G_TIME_SPAN_SECOND;
	gboolean locked;
	do {
		locked = g_mutex_trylock(&GST_BASE_SINK(sink)->preroll_lock);
		if (locked) {
			GST_BASE_SINK_PREROLL_UNLOCK(GST_BASE_SINK(sink));
			g_usleep(1000);
		}
	} while (locked && g_get_monotonic_time() < deadline);
	g_assert_false(locked);
	if (recovery) {
		g_mutex_lock(&test.mutex);
		test.discontinuityEvent = TRUE;
		g_mutex_unlock(&test.mutex);
	} else {
		g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_start()));
	}
	g_thread_join(thread);
	g_assert_cmpint(pending.result, ==, recovery ? GST_FLOW_OK : GST_FLOW_FLUSHING);
	gst_object_unref(pending.pad);
	waitForEmptyQueue(harness->element);
	g_mutex_lock(&sink->mutex);
	g_assert_cmpuint(sink->generation, >, generation);
	if (recovery)
		g_assert_cmpuint(sink->cancelGeneration, ==, cancelGeneration);
	else
		g_assert_cmpuint(sink->cancelGeneration, >, cancelGeneration);
	g_assert_true(sink->needKeyframe);
	g_mutex_unlock(&sink->mutex);
	if (!recovery) {
		GstSegment segment;
		g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_stop(TRUE)));
		gst_segment_init(&segment, GST_FORMAT_TIME);
		g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
	}
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.frames, ==, 0);
	test.blocked = FALSE;
	g_mutex_unlock(&test.mutex);
	/* Recovery must drop the raced AU and dependent pictures, then accept
	 * the next keyframe without restarting the upstream streaming task. */
	GstBuffer *delta = makeFrame(0x44, 4 * GST_SECOND);
	GST_BUFFER_FLAG_SET(delta, GST_BUFFER_FLAG_DELTA_UNIT);
	g_assert_cmpint(gst_harness_push(harness, delta), ==, GST_FLOW_OK);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x55, 5 * GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	waitForEmptyQueue(harness->element);
	teardownHarness(harness);
	g_assert_cmpuint(test.frames, ==, 1);
	g_assert_cmpuint(test.marker, ==, 0x55);
	clearDevice(&test);
}

static void testRecoveryBeforeEosMarker(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	PendingEos pending;
	initDevice(&test);
	test.blocked = TRUE;
	test.eosBlocked = TRUE;
	GstHarness *harness = makeHarness(&test);
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(harness->element);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x33, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, TRUE, 1);
	GThread *thread = startEos(harness->element, &pending);
	/* EOS is queued behind a blocked payload, before its decoder marker.
	 * Recovery must discard that payload without losing serialized EOS. */
	gint64 deadline = g_get_monotonic_time() + 3 * G_TIME_SPAN_SECOND;
	g_mutex_lock(&sink->mutex);
	while (g_queue_is_empty(&sink->queue) && g_cond_wait_until(&sink->changed, &sink->mutex, deadline))
		;
	g_assert_false(g_queue_is_empty(&sink->queue));
	g_mutex_unlock(&sink->mutex);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.eosFrames, ==, 0);
	test.discontinuityEvent = TRUE;
	g_mutex_unlock(&test.mutex);
	waitForEosPoll(&test);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.clears, >, 0);
	g_assert_cmpuint(test.frames, ==, 0);
	g_assert_cmpuint(test.eosFrames, ==, 1);
	test.eosBlocked = FALSE;
	g_mutex_unlock(&test.mutex);
	g_assert_true(finishEos(thread, &pending));
	waitForEmptyQueue(harness->element);
	teardownHarness(harness);
	g_assert_cmpuint(test.clears, >, 0);
	clearDevice(&test);
}

static void testEosDrainNoNormalPoll(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	PendingEos pending;
	initDevice(&test);
	test.eosBlocked = TRUE;
	GstHarness *harness = makeHarness(&test);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x44, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	waitForEmptyQueue(harness->element);
	GThread *thread = startEos(harness->element, &pending);
	waitForEosPoll(&test);
	g_mutex_lock(&test.mutex);
	guint targetPolls = test.eosWaits + 5;
	test.discontinuityEvent = TRUE;
	gint64 deadline = g_get_monotonic_time() + 3 * G_TIME_SPAN_SECOND;
	while (test.eosWaits < targetPolls && g_cond_wait_until(&test.changed, &test.mutex, deadline))
		;
	g_assert_cmpuint(test.eosWaits, >=, targetPolls);
	g_assert_cmpuint(test.drainNormalPolls, ==, 0);
	g_assert_cmpuint(test.drainEventCalls, ==, 0);
	g_assert_true(test.discontinuityEvent);
	g_mutex_unlock(&test.mutex);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PAUSED), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_cmpint(gst_element_get_state(harness->element, NULL, NULL, GST_SECOND), ==, GST_STATE_CHANGE_SUCCESS);
	/* Exercise more than one 50-ms paused-worker wakeup as well. */
	g_usleep(120000);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.drainNormalPolls, ==, 0);
	g_assert_cmpuint(test.drainEventCalls, ==, 0);
	g_assert_true(test.discontinuityEvent);
	test.eosBlocked = FALSE;
	guint events = test.validEventCalls;
	g_mutex_unlock(&test.mutex);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PLAYING), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_true(finishEos(thread, &pending));
	/* Completed EOS must remain final, not be invalidated by a deferred
	 * event before its waiter has observed completion. */
	g_usleep(120000);
	g_mutex_lock(&test.mutex);
	g_assert_true(test.discontinuityEvent);
	g_assert_cmpuint(test.validEventCalls, ==, events);
	g_assert_cmpuint(test.eosFrames, ==, 1);
	g_mutex_unlock(&test.mutex);
	/* A logical stream reset must re-enable event processing. */
	g_assert_true(gst_harness_push_event(harness, gst_event_new_stream_start("after-eos")));
	deadline = g_get_monotonic_time() + 3 * G_TIME_SPAN_SECOND;
	g_mutex_lock(&test.mutex);
	while (test.validEventCalls == events && g_cond_wait_until(&test.changed, &test.mutex, deadline))
		;
	g_assert_cmpuint(test.validEventCalls, >, events);
	g_assert_false(test.discontinuityEvent);
	g_mutex_unlock(&test.mutex);
	teardownHarness(harness);
	g_assert_cmpuint(test.eosFrames, ==, 1);
	clearDevice(&test);
}

static void testEosMarkerRetries(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	PendingEos pending;
	initDevice(&test);
	test.eosRetries = 2;
	test.eosBlocked = TRUE;
	GstHarness *harness = makeHarness(&test);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x55, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	GThread *thread = startEos(harness->element, &pending);
	waitForEosPoll(&test);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.eosAttempts, ==, 3);
	g_assert_cmpuint(test.eosFrames, ==, 1);
	g_mutex_unlock(&test.mutex);
	g_mutex_lock(&pending.mutex);
	g_assert_false(pending.done);
	g_mutex_unlock(&pending.mutex);
	/* Acceptance is not completion. While waiting for actual EOF, the
	 * accepted zero-byte marker must not be submitted for a second time. */
	g_usleep(30000);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.eosAttempts, ==, 3);
	test.eosBlocked = FALSE;
	g_mutex_unlock(&test.mutex);
	g_assert_true(finishEos(thread, &pending));
	teardownHarness(harness);
	g_assert_cmpuint(test.eosAttempts, ==, 3);
	g_assert_cmpuint(test.eosFrames, ==, 1);
	clearDevice(&test);
}

static void testEosMarkerFailure(gconstpointer data)
{
	if (!runIsolated())
		return;
	gint mode = GPOINTER_TO_INT(data);
	TestDevice test;
	PendingEos pending;
	initDevice(&test);
	test.eosRetryBlocked = mode == 0;
	test.eosError = mode == 1 ? EIO : mode == 2 ? ENOMEM : 0;
	test.eosUnexpectedResult = mode == 3 ? 1 : 0;
	GstHarness *harness = makeHarness(&test);
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(harness->element);
	g_mutex_lock(&sink->mutex);
	sink->ioTimeoutMs = 100;
	g_mutex_unlock(&sink->mutex);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x66, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	waitForEmptyQueue(harness->element);
	GstBus *bus = gst_bus_new();
	gst_element_set_bus(harness->element, bus);
	GThread *thread = startEos(harness->element, &pending);
	GstMessage *message = gst_bus_timed_pop_filtered(bus, 2 * GST_SECOND, GST_MESSAGE_ERROR);
	g_assert_nonnull(message);
	GError *error = NULL;
	gchar *debug = NULL;
	gst_message_parse_error(message, &error, &debug);
	g_assert_error(error, GST_RESOURCE_ERROR, GST_RESOURCE_ERROR_WRITE);
	g_assert_nonnull(debug);
	g_assert_nonnull(strstr(debug, mode == 0 ? "no progress" : "end of stream"));
	g_error_free(error);
	g_free(debug);
	gst_message_unref(message);
	g_assert_false(finishEos(thread, &pending));
	teardownHarness(harness);
	gst_object_unref(bus);
	g_assert_cmpuint(test.eosFrames, ==, 0);
	g_assert_cmpuint(test.eosWaits, ==, 0);
	if (mode == 0) {
		/* The shared ioctl wrapper retries each EINTR immediately, then
		 * the following EAGAIN completes one worker retry round. Bound
		 * those rounds to verify its 5-ms yield, not individual ioctls. */
		g_assert_cmpuint(test.eosAgainReturns, >=, 2);
		g_assert_cmpuint(test.eosAgainReturns, <=, 30);
		g_assert_cmpuint(test.eosAttempts, ==, 2 * test.eosAgainReturns - 1);
	} else {
		g_assert_cmpuint(test.eosAttempts, ==, 1);
	}
	clearDevice(&test);
}

static void testPauseDuringEosMarkerRetry(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	PendingEos pending;
	initDevice(&test);
	test.eosRetryBlocked = TRUE;
	GstHarness *harness = makeHarness(&test);
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(harness->element);
	g_mutex_lock(&sink->mutex);
	sink->ioTimeoutMs = 100;
	g_mutex_unlock(&sink->mutex);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x77, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	GstBus *bus = gst_bus_new();
	gst_element_set_bus(harness->element, bus);
	GThread *thread = startEos(harness->element, &pending);
	waitForEosAttempts(&test, 2);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PAUSED), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_cmpint(gst_element_get_state(harness->element, NULL, NULL, GST_SECOND), ==, GST_STATE_CHANGE_SUCCESS);
	g_mutex_lock(&test.mutex);
	guint attempts = test.eosAttempts;
	g_assert_cmpuint(test.eosFrames, ==, 0);
	g_assert_cmpuint(test.eosWaits, ==, 0);
	g_mutex_unlock(&test.mutex);
	g_assert_null(gst_bus_timed_pop_filtered(bus, 250 * GST_MSECOND, GST_MESSAGE_ERROR));
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.eosAttempts, ==, attempts);
	test.eosRetryBlocked = FALSE;
	g_mutex_unlock(&test.mutex);
	g_mutex_lock(&pending.mutex);
	g_assert_false(pending.done);
	g_mutex_unlock(&pending.mutex);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PLAYING), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_true(finishEos(thread, &pending));
	teardownHarness(harness);
	gst_object_unref(bus);
	g_assert_cmpuint(test.eosAttempts, ==, attempts + 1);
	g_assert_cmpuint(test.eosFrames, ==, 1);
	clearDevice(&test);
}

static void testCancelEosMarkerRetry(gconstpointer data)
{
	if (!runIsolated())
		return;
	gboolean stop = GPOINTER_TO_INT(data);
	TestDevice test;
	PendingEos pending;
	initDevice(&test);
	test.eosRetryBlocked = TRUE;
	GstHarness *harness = makeHarness(&test);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x88, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	GstBus *bus = gst_bus_new();
	gst_element_set_bus(harness->element, bus);
	GThread *thread = startEos(harness->element, &pending);
	waitForEosAttempts(&test, 2);
	if (stop)
		g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_NULL), !=, GST_STATE_CHANGE_FAILURE);
	else
		g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_start()));
	g_assert_false(finishEos(thread, &pending));
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.eosFrames, ==, 0);
	g_assert_cmpuint(test.eosWaits, ==, 0);
	test.eosRetryBlocked = FALSE;
	g_mutex_unlock(&test.mutex);
	g_assert_null(gst_bus_pop_filtered(bus, GST_MESSAGE_ERROR));
	if (!stop) {
		GstSegment segment;
		g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_stop(TRUE)));
		gst_segment_init(&segment, GST_FORMAT_TIME);
		g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
		g_assert_cmpint(gst_harness_push(harness, makeFrame(0x99, 2 * GST_SECOND)), ==, GST_FLOW_OK);
		waitForCount(&test, FALSE, 2);
		thread = startEos(harness->element, &pending);
		g_assert_true(finishEos(thread, &pending));
	}
	teardownHarness(harness);
	gst_object_unref(bus);
	g_assert_cmpuint(test.eosFrames, ==, stop ? 0 : 1);
	clearDevice(&test);
}

static void testEosProgressDeadline(gconstpointer data)
{
	if (!runIsolated())
		return;
	gint mode = GPOINTER_TO_INT(data);
	gboolean wrap = mode == 1, reverse = mode == 2;
	TestDevice test;
	PendingEos pending;
	initDevice(&test);
	test.eosBlocked = TRUE;
	test.eosPtsIncrement = 9000;
	test.eosPtsBackwards = reverse;
	GstHarness *harness = makeHarness(&test);
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(harness->element);
	g_mutex_lock(&sink->mutex);
	sink->ioTimeoutMs = 100;
	g_mutex_unlock(&sink->mutex);
	if (reverse) {
		GstSegment segment;
		gst_segment_init(&segment, GST_FORMAT_TIME);
		segment.rate = -1;
		segment.stop = segment.position = 2 * GST_SECOND;
		g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
	}
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x55, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	waitForEmptyQueue(harness->element);
	if (wrap) {
		g_mutex_lock(&test.mutex);
		test.pts = DREAM_VIDEO_PTS_MASK - 15000;
		g_mutex_unlock(&test.mutex);
	}
	GstBus *bus = gst_bus_new();
	gst_element_set_bus(harness->element, bus);
	GThread *thread = startEos(harness->element, &pending);
	waitForEosPoll(&test);
	/* Progress, not the total time spent draining, controls the deadline.
	 * This wait spans more than three complete timeout periods. */
	GstMessage *message = gst_bus_timed_pop_filtered(bus, 350 * GST_MSECOND, GST_MESSAGE_ERROR);
	g_assert_null(message);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PAUSED), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_cmpint(gst_element_get_state(harness->element, NULL, NULL, GST_SECOND), ==, GST_STATE_CHANGE_SUCCESS);
	message = gst_bus_timed_pop_filtered(bus, 200 * GST_MSECOND, GST_MESSAGE_ERROR);
	g_assert_null(message);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PLAYING), !=, GST_STATE_CHANGE_FAILURE);
	message = gst_bus_timed_pop_filtered(bus, 200 * GST_MSECOND, GST_MESSAGE_ERROR);
	g_assert_null(message);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.eosPtsReads, >=, 3);
	g_assert_cmpuint(test.eosPtsReads, <=, 20);
	g_assert_cmpuint(test.drainNormalPolls, ==, 0);
	g_assert_cmpuint(test.drainEventCalls, ==, 0);
	test.eosBlocked = FALSE;
	g_mutex_unlock(&test.mutex);
	g_assert_true(finishEos(thread, &pending));
	teardownHarness(harness);
	gst_object_unref(bus);
	g_assert_cmpuint(test.eosFrames, ==, 1);
	clearDevice(&test);
}

static void testEosStallDeadline(gconstpointer data)
{
	if (!runIsolated())
		return;
	gint mode = GPOINTER_TO_INT(data);
	TestDevice test;
	PendingEos pending;
	initDevice(&test);
	test.eosBlocked = TRUE;
	test.eosPtsInvalid = mode == 1;
	test.eosPtsBackwards = mode == 2;
	test.eosPtsIncrement = mode == 2 ? 9000 : 0;
	GstHarness *harness = makeHarness(&test);
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(harness->element);
	g_mutex_lock(&sink->mutex);
	sink->ioTimeoutMs = 100;
	g_mutex_unlock(&sink->mutex);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x66, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	waitForEmptyQueue(harness->element);
	GstBus *bus = gst_bus_new();
	gst_element_set_bus(harness->element, bus);
	GThread *thread = startEos(harness->element, &pending);
	waitForEosPoll(&test);
	GstMessage *message = gst_bus_timed_pop_filtered(bus, 2 * GST_SECOND, GST_MESSAGE_ERROR);
	g_assert_nonnull(message);
	GError *error = NULL;
	gchar *debug = NULL;
	gst_message_parse_error(message, &error, &debug);
	g_assert_error(error, GST_RESOURCE_ERROR, GST_RESOURCE_ERROR_WRITE);
	g_assert_nonnull(debug);
	g_assert_nonnull(strstr(debug, "no progress"));
	g_error_free(error);
	g_free(debug);
	gst_message_unref(message);
	g_assert_false(finishEos(thread, &pending));
	teardownHarness(harness);
	gst_object_unref(bus);
	g_assert_cmpuint(test.eosPtsReads, >=, 2);
	g_assert_cmpuint(test.eosPtsReads, <=, 5);
	clearDevice(&test);
}

static void testFlushDuringEosDrain(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	PendingEos pending;
	initDevice(&test);
	test.eosBlocked = TRUE;
	test.eosPtsIncrement = 9000;
	GstHarness *harness = makeHarness(&test);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x77, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	GThread *thread = startEos(harness->element, &pending);
	waitForEosPoll(&test);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_start()));
	g_assert_false(finishEos(thread, &pending));
	g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_stop(TRUE)));
	GstSegment segment;
	gst_segment_init(&segment, GST_FORMAT_TIME);
	g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x88, 2 * GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 2);
	teardownHarness(harness);
	clearDevice(&test);
}

static void testPauseDuringEosDrain(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	PendingEos pending;
	initDevice(&test);
	test.eosBlocked = TRUE;
	GstHarness *harness = makeHarness(&test);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x77, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	GThread *thread = startEos(harness->element, &pending);
	waitForEosPoll(&test);
	/* EOS is already draining. The wait_event implementation must release
 * PREROLL_LOCK so a state change can pause it without deadlocking. */
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PAUSED), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_cmpint(gst_element_get_state(harness->element, NULL, NULL, GST_SECOND), ==, GST_STATE_CHANGE_SUCCESS);
	g_mutex_lock(&test.mutex);
	test.eosBlocked = FALSE;
	g_mutex_unlock(&test.mutex);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_PLAYING), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_true(finishEos(thread, &pending));
	teardownHarness(harness);
	g_assert_cmpuint(test.eosFrames, ==, 1);
	clearDevice(&test);
}

static void testStopDuringEosDrain(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	PendingEos pending;
	initDevice(&test);
	test.eosBlocked = TRUE;
	GstHarness *harness = makeHarness(&test);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x88, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	GThread *thread = startEos(harness->element, &pending);
	waitForEosPoll(&test);
	g_assert_cmpint(gst_element_set_state(harness->element, GST_STATE_NULL), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_false(finishEos(thread, &pending));
	teardownHarness(harness);
	g_assert_cmpuint(test.closes, ==, 1);
	clearDevice(&test);
}

static void testEmptySuccessfulDecoderEvent(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	initDevice(&test);
	test.emptyEventSuccess = TRUE;
	GstHarness *harness = makeHarness(&test);
	gint64 deadline = g_get_monotonic_time() + 3 * G_TIME_SPAN_SECOND;
	g_mutex_lock(&test.mutex);
	while (!test.emptyEventCalls && g_cond_wait_until(&test.changed, &test.mutex, deadline))
		;
	g_assert_cmpuint(test.emptyEventCalls, >, 0);
	g_assert_cmpuint(test.validEventCalls, ==, 0);
	guint clears = test.clears;
	g_mutex_unlock(&test.mutex);

	for (guint index = 0; index < 3; index++) {
		g_assert_cmpint(gst_harness_push(harness,
			makeFrame(0x11 + index, (index + 1) * GST_SECOND)), ==, GST_FLOW_OK);
		waitForCount(&test, FALSE, index + 1);
		deadline = g_get_monotonic_time() + 3 * G_TIME_SPAN_SECOND;
		g_mutex_lock(&test.mutex);
		while (test.validEventCalls < index + 1 &&
			g_cond_wait_until(&test.changed, &test.mutex, deadline))
			;
		g_assert_cmpuint(test.validEventCalls, ==, index + 1);
		g_mutex_unlock(&test.mutex);
		/* The positive PTS-valid event preceding the empty sentinel must
		 * still reach the sink. No invented discontinuity may clear it. */
		gint64 decoderTime = -1;
		g_signal_emit_by_name(harness->element, "get-decoder-time", &decoderTime);
		g_assert_cmpint(decoderTime, ==, (gint64) (index + 1) * GST_SECOND);
	}
	waitForEmptyQueue(harness->element);
	teardownHarness(harness);
	g_assert_cmpuint(test.frames, ==, 3);
	g_assert_cmpuint(test.clears, ==, clears);
	g_assert_cmpuint(test.marker, ==, 0x13);
	g_assert_cmpuint(test.closes, ==, 1);
	clearDevice(&test);
}

static void testWritableReadiness(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	initDevice(&test);
	test.writable = FALSE;
	GstHarness *harness = makeHarness(&test);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x11, GST_SECOND)), ==, GST_FLOW_OK);
	waitForWritablePoll(&test, 3);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.attempts, ==, 0);
	g_assert_cmpuint(test.frames, ==, 0);
	/* An event arriving with writable readiness must be handled without
	 * suppressing the permitted payload submission. */
	test.ptsEvent = TRUE;
	test.writable = TRUE;
	g_mutex_unlock(&test.mutex);
	waitForCount(&test, FALSE, 1);
	waitForEmptyQueue(harness->element);
	teardownHarness(harness);
	g_assert_cmpuint(test.frames, ==, 1);
	g_assert_cmpuint(test.marker, ==, 0x11);
	clearDevice(&test);
}

static void testWritableCancellation(gconstpointer userData)
{
	if (!runIsolated())
		return;
	const gboolean stop = GPOINTER_TO_INT(userData);
	TestDevice test;
	initDevice(&test);
	test.writable = FALSE;
	GstHarness *harness = makeHarness(&test);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x11, GST_SECOND)), ==, GST_FLOW_OK);
	waitForWritablePoll(&test, 3);
	if (!stop) {
		GstSegment segment;
		g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_start()));
		g_assert_true(gst_harness_push_event(harness, gst_event_new_flush_stop(TRUE)));
		gst_segment_init(&segment, GST_FORMAT_TIME);
		g_assert_true(gst_harness_push_event(harness, gst_event_new_segment(&segment)));
		g_mutex_lock(&test.mutex);
		g_assert_cmpuint(test.attempts, ==, 0);
		test.writable = TRUE;
		g_mutex_unlock(&test.mutex);
		g_assert_cmpint(gst_harness_push(harness, makeFrame(0x22, 2 * GST_SECOND)), ==, GST_FLOW_OK);
		waitForCount(&test, FALSE, 1);
		waitForEmptyQueue(harness->element);
	}
	gint64 started = g_get_monotonic_time();
	teardownHarness(harness);
	g_assert_cmpint(g_get_monotonic_time() - started, <, G_TIME_SPAN_SECOND);
	g_assert_cmpuint(test.frames, ==, stop ? 0 : 1);
	if (!stop)
		g_assert_cmpuint(test.marker, ==, 0x22);
	g_assert_cmpuint(test.closes, ==, 1);
	clearDevice(&test);
}

static void testPartialWritableReadiness(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	initDevice(&test);
	test.partialBytes = 3;
	GstHarness *harness = makeHarness(&test);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x33, GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&test, FALSE, 1);
	g_mutex_lock(&test.mutex);
	guint waits = test.writeWaits;
	g_mutex_unlock(&test.mutex);
	waitForWritablePoll(&test, waits + 3);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.attempts, ==, 1);
	g_assert_cmpuint(test.firstPts, ==, 90000);
	test.partialBytes = 0;
	test.writable = TRUE;
	g_mutex_unlock(&test.mutex);
	waitForCount(&test, FALSE, 2);
	waitForEmptyQueue(harness->element);
	guint64 frames = 0, bytes = 0;
	g_object_get(harness->element, "submitted-frames", &frames, "submitted-bytes", &bytes, NULL);
	g_assert_cmpuint(frames, ==, 1);
	g_assert_cmpuint(bytes, ==, 7);
	teardownHarness(harness);
	g_assert_cmpuint(test.attempts, ==, 2);
	g_assert_cmpuint(test.pts, ==, DREAM_VIDEO_PTS_NONE);
	g_assert_cmpuint(test.marker, ==, 0x33);
	clearDevice(&test);
}

static void testWritableTimeout(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	initDevice(&test);
	test.writable = FALSE;
	GstHarness *harness = makeHarness(&test);
	GstDreamVideoSink *sink = GST_DREAM_VIDEO_SINK(harness->element);
	g_mutex_lock(&sink->mutex);
	sink->ioTimeoutMs = 100;
	g_mutex_unlock(&sink->mutex);
	GstBus *bus = gst_bus_new();
	gst_element_set_bus(harness->element, bus);
	GstFlowReturn flow = gst_harness_push(harness, makeFrame(0x44, GST_SECOND));
	g_assert_true(flow == GST_FLOW_OK || flow == GST_FLOW_ERROR);
	GstMessage *message = gst_bus_timed_pop_filtered(bus, 2 * GST_SECOND, GST_MESSAGE_ERROR);
	g_assert_nonnull(message);
	gst_message_unref(message);
	waitForEmptyQueue(harness->element);
	teardownHarness(harness);
	gst_object_unref(bus);
	g_assert_cmpuint(test.attempts, ==, 0);
	g_assert_cmpuint(test.writeWaits, >, 0);
	g_assert_cmpuint(test.writeWaits, <, 100);
	clearDevice(&test);
}

static void testPermanentDecoderError(void)
{
	if (!runIsolated())
		return;
	TestDevice test;
	initDevice(&test);
	test.rejectFrames = TRUE;
	GstHarness *harness = makeHarness(&test);
	GstBus *bus = gst_bus_new();
	gst_element_set_bus(harness->element, bus);
	GstFlowReturn firstFlow = gst_harness_push(harness, makeFrame(0x55, GST_SECOND));
	/* The worker may report the failure before render returns. Both
 * outcomes are valid for the first asynchronous submission. */
	g_assert_true(firstFlow == GST_FLOW_OK || firstFlow == GST_FLOW_ERROR);
	GstMessage *message = gst_bus_timed_pop_filtered(bus, 2 * GST_SECOND, GST_MESSAGE_ERROR);
	g_assert_nonnull(message);
	GError *error = NULL;
	gchar *debug = NULL;
	gst_message_parse_error(message, &error, &debug);
	g_assert_error(error, GST_RESOURCE_ERROR, GST_RESOURCE_ERROR_WRITE);
	g_assert_nonnull(debug);
	g_assert_nonnull(strstr(debug, "VIDEO_SET_FRAME"));
	g_error_free(error);
	g_free(debug);
	gst_message_unref(message);
	waitForEmptyQueue(harness->element);
	g_assert_cmpint(gst_harness_push(harness, makeFrame(0x66, 2 * GST_SECOND)), ==, GST_FLOW_ERROR);
	message = gst_bus_timed_pop_filtered(bus, 100 * GST_MSECOND, GST_MESSAGE_ERROR);
	g_assert_null(message);
	g_mutex_lock(&test.mutex);
	g_assert_cmpuint(test.attempts, ==, 1);
	g_assert_cmpuint(test.frames, ==, 0);
	g_mutex_unlock(&test.mutex);
	gst_element_set_bus(harness->element, NULL);
	teardownHarness(harness);
	gst_object_unref(bus);
	clearDevice(&test);
}

typedef struct {
	TestDevice device;
	GstHarness *harness;
	GstDreamVideoSink *sink;
	GstClock *clock;
	GstBus *bus;
} LatencyFixture;

static gboolean latencyUpstreamQuery(GstPad *pad, GstObject *parent, GstQuery *query)
{
	if (GST_QUERY_TYPE(query) == GST_QUERY_LATENCY) {
		gst_query_set_latency(query, TRUE, 50 * GST_MSECOND, GST_CLOCK_TIME_NONE);
		return TRUE;
	}
	return gst_pad_query_default(pad, parent, query);
}

static void initLatencyFixture(LatencyFixture *fixture)
{
	initDevice(&fixture->device);
	fixture->device.manualPtsEvent = TRUE;
	fixture->harness = makeHarness(&fixture->device);
	fixture->sink = GST_DREAM_VIDEO_SINK(fixture->harness->element);
	fixture->clock = gst_test_clock_new_with_start_time(10 * GST_SECOND);
	g_assert_true(gst_element_set_clock(fixture->harness->element, fixture->clock));
	fixture->bus = gst_bus_new();
	gst_element_set_bus(fixture->harness->element, fixture->bus);
	gst_pad_set_query_function(fixture->harness->srcpad, latencyUpstreamQuery);
}

static void clearLatencyFixture(LatencyFixture *fixture)
{
	teardownHarness(fixture->harness);
	gst_object_unref(fixture->bus);
	gst_object_unref(fixture->clock);
	clearDevice(&fixture->device);
}

static void checkLatencyQuery(LatencyFixture *fixture, GstClockTime expected)
{
	gboolean baseLive, upstreamLive, live;
	GstClockTime minimum, maximum;
	g_assert_true(gst_base_sink_query_latency(GST_BASE_SINK(fixture->sink),
		&baseLive, &upstreamLive, &minimum, &maximum));
	GstQuery *query = gst_query_new_latency();
	g_assert_true(gst_element_query(fixture->harness->element, query));
	gst_query_parse_latency(query, &live, &minimum, &maximum);
	g_assert_cmpint(live, ==, baseLive);
	g_assert_cmpuint(minimum, ==, expected);
	g_assert_cmpuint(maximum, ==, expected);
	gst_query_unref(query);
	/* The measurement is separate from the original constant render delay. */
	g_assert_cmpuint(gst_base_sink_get_render_delay(GST_BASE_SINK(fixture->sink)), ==, GST_SECOND);
}

static void signalLatencyPts(LatencyFixture *fixture, GstClockTime time,
	guint64 pts, gboolean expectMessage, GstClockTime expected)
{
	GstDreamVideoSink *sink = fixture->sink;
	TestDevice *test = &fixture->device;
	/* Publish the deterministic clock and matching hardware event together.
	 * decoder_events samples the clock after acquiring this same I/O lock. */
	g_mutex_lock(&sink->ioMutex);
	gst_test_clock_set_time(GST_TEST_CLOCK(fixture->clock), time);
	g_mutex_lock(&test->mutex);
	guint count = test->validEventCalls + 1;
	test->pts = pts;
	test->ptsEvent = TRUE;
	g_mutex_unlock(&test->mutex);
	g_mutex_unlock(&sink->ioMutex);
	gint64 deadline = g_get_monotonic_time() + 3 * G_TIME_SPAN_SECOND;
	g_mutex_lock(&test->mutex);
	while (test->validEventCalls < count && g_cond_wait_until(&test->changed, &test->mutex, deadline))
		;
	g_assert_cmpuint(test->validEventCalls, >=, count);
	g_mutex_unlock(&test->mutex);
	/* The ioctl callback runs with sink->mutex held. Taking it here waits
	 * for the measurement, including rejected event values, to finish. */
	g_mutex_lock(&sink->mutex);
	g_assert_cmpuint(sink->latency, ==, expected);
	g_mutex_unlock(&sink->mutex);
	GstMessage *message = gst_bus_timed_pop_filtered(fixture->bus,
		expectMessage ? GST_SECOND : 100 * GST_MSECOND, GST_MESSAGE_LATENCY);
	if (expectMessage) {
		g_assert_nonnull(message);
		gst_message_unref(message);
	} else {
		g_assert_null(message);
	}
	checkLatencyQuery(fixture, expected);
}

static void clearStartupLatency(LatencyFixture *fixture)
{
	/* BaseSink emits its own LATENCY message when first preroll completes.
	 * The synchronous first push has returned and manualPtsEvent prevents
	 * decoder measurements, so only startup messages may exist here. */
	g_mutex_lock(&fixture->sink->mutex);
	g_assert_false(fixture->sink->ptsValid);
	g_mutex_unlock(&fixture->sink->mutex);
	GstMessage *message;
	while ((message = gst_bus_pop_filtered(fixture->bus, GST_MESSAGE_LATENCY)))
		gst_message_unref(message);
}

static void latencyFirstFrame(LatencyFixture *fixture, GstClockTime pts)
{
	g_assert_cmpint(gst_harness_push(fixture->harness, makeFrame(0x44, pts)), ==, GST_FLOW_OK);
	waitForCount(&fixture->device, FALSE, 1);
	waitForEmptyQueue(fixture->harness->element);
	clearStartupLatency(fixture);
}

static void testLatencyDefaultQuery(void)
{
	LatencyFixture fixture;
	initLatencyFixture(&fixture);
	latencyFirstFrame(&fixture, 5 * GST_SECOND);
	checkLatencyQuery(&fixture, GST_SECOND);
	gst_base_sink_set_sync(GST_BASE_SINK(fixture.sink), TRUE);
	checkLatencyQuery(&fixture, GST_SECOND);
	gst_base_sink_set_sync(GST_BASE_SINK(fixture.sink), FALSE);
	clearLatencyFixture(&fixture);
}

static void testLatencySubmissionAnchor(void)
{
	LatencyFixture fixture;
	initLatencyFixture(&fixture);
	g_mutex_lock(&fixture.device.mutex);
	fixture.device.writable = FALSE;
	g_mutex_unlock(&fixture.device.mutex);
	g_assert_cmpint(gst_harness_push(fixture.harness, makeFrame(0x44, 5 * GST_SECOND)), ==, GST_FLOW_OK);
	waitForWritablePoll(&fixture.device, 3);
	g_mutex_lock(&fixture.sink->mutex);
	g_assert_cmpuint(fixture.sink->latencyClockTime, ==, GST_CLOCK_TIME_NONE);
	g_mutex_unlock(&fixture.sink->mutex);
	gst_test_clock_set_time(GST_TEST_CLOCK(fixture.clock), 11 * GST_SECOND);
	g_mutex_lock(&fixture.device.mutex);
	fixture.device.writable = TRUE;
	g_mutex_unlock(&fixture.device.mutex);
	waitForCount(&fixture.device, FALSE, 1);
	waitForEmptyQueue(fixture.harness->element);
	g_mutex_lock(&fixture.sink->mutex);
	g_assert_cmpuint(fixture.sink->latencyClockTime, ==, 11 * GST_SECOND);
	g_assert_cmpuint(fixture.sink->latencyPts, ==, 450000);
	g_mutex_unlock(&fixture.sink->mutex);
	clearStartupLatency(&fixture);
	signalLatencyPts(&fixture, 11500 * GST_MSECOND, 468000, TRUE, 300 * GST_MSECOND);
	clearLatencyFixture(&fixture);
}

static void testLatencyInvalidMeasurements(void)
{
	LatencyFixture fixture;
	initLatencyFixture(&fixture);
	latencyFirstFrame(&fixture, 5 * GST_SECOND);
	signalLatencyPts(&fixture, 10500 * GST_MSECOND, 468000, TRUE, 300 * GST_MSECOND);
	/* Missing PTS, negative latency, backwards PTS and excessive latency
	 * must retain the previous estimate and must not post LATENCY. */
	signalLatencyPts(&fixture, 11 * GST_SECOND, DREAM_VIDEO_PTS_NONE, FALSE, 300 * GST_MSECOND);
	signalLatencyPts(&fixture, 12 * GST_SECOND, 720000, FALSE, 300 * GST_MSECOND);
	signalLatencyPts(&fixture, 13 * GST_SECOND, 360000, FALSE, 300 * GST_MSECOND);
	signalLatencyPts(&fixture, 20 * GST_SECOND, 450000, FALSE, 300 * GST_MSECOND);
	clearLatencyFixture(&fixture);
}

static void testLatencyPtsWrap(void)
{
	LatencyFixture fixture;
	initLatencyFixture(&fixture);
	latencyFirstFrame(&fixture, gst_util_uint64_scale(DREAM_VIDEO_PTS_MASK - 45000, 100000, 9));
	g_mutex_lock(&fixture.sink->mutex);
	guint64 firstPts = fixture.sink->latencyPts;
	g_mutex_unlock(&fixture.sink->mutex);
	signalLatencyPts(&fixture, 11300 * GST_MSECOND,
		(firstPts + 90000) & DREAM_VIDEO_PTS_MASK, TRUE, 300 * GST_MSECOND);
	signalLatencyPts(&fixture, 12 * GST_SECOND,
		(firstPts - 90000) & DREAM_VIDEO_PTS_MASK, FALSE, 300 * GST_MSECOND);
	clearLatencyFixture(&fixture);
}

static void testLatencyLifetime(void)
{
	LatencyFixture fixture;
	initLatencyFixture(&fixture);
	latencyFirstFrame(&fixture, 5 * GST_SECOND);
	signalLatencyPts(&fixture, 10500 * GST_MSECOND, 468000, TRUE, 300 * GST_MSECOND);
	g_assert_cmpint(gst_element_set_state(fixture.harness->element, GST_STATE_PAUSED), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_cmpint(gst_element_get_state(fixture.harness->element, NULL, NULL, GST_SECOND), !=, GST_STATE_CHANGE_FAILURE);
	g_assert_cmpint(gst_element_set_state(fixture.harness->element, GST_STATE_PLAYING), !=, GST_STATE_CHANGE_FAILURE);
	g_mutex_lock(&fixture.sink->mutex);
	g_assert_cmpuint(fixture.sink->latencyClockTime, ==, 10 * GST_SECOND);
	g_assert_cmpuint(fixture.sink->latencyPts, ==, 450000);
	g_assert_cmpuint(fixture.sink->latency, ==, 300 * GST_MSECOND);
	g_mutex_unlock(&fixture.sink->mutex);
	g_assert_true(gst_harness_push_event(fixture.harness, gst_event_new_flush_start()));
	g_assert_true(gst_harness_push_event(fixture.harness, gst_event_new_flush_stop(TRUE)));
	g_mutex_lock(&fixture.sink->mutex);
	g_assert_cmpuint(fixture.sink->latencyClockTime, ==, GST_CLOCK_TIME_NONE);
	g_assert_cmpuint(fixture.sink->latencyPts, ==, DREAM_VIDEO_PTS_NONE);
	g_assert_cmpuint(fixture.sink->latency, ==, 300 * GST_MSECOND);
	g_mutex_unlock(&fixture.sink->mutex);
	GstSegment segment;
	gst_segment_init(&segment, GST_FORMAT_TIME);
	g_assert_true(gst_harness_push_event(fixture.harness, gst_event_new_segment(&segment)));
	gst_test_clock_set_time(GST_TEST_CLOCK(fixture.clock), 20 * GST_SECOND);
	g_assert_cmpint(gst_harness_push(fixture.harness, makeFrame(0x55, 8 * GST_SECOND)), ==, GST_FLOW_OK);
	waitForCount(&fixture.device, FALSE, 2);
	waitForEmptyQueue(fixture.harness->element);
	signalLatencyPts(&fixture, 20500 * GST_MSECOND, 729000, TRUE, 400 * GST_MSECOND);
	g_mutex_lock(&fixture.device.mutex);
	fixture.device.discontinuityEvent = TRUE;
	guint clears = fixture.device.clears;
	g_mutex_unlock(&fixture.device.mutex);
	gint64 deadline = g_get_monotonic_time() + 3 * G_TIME_SPAN_SECOND;
	g_mutex_lock(&fixture.device.mutex);
	while (fixture.device.clears == clears && g_cond_wait_until(&fixture.device.changed, &fixture.device.mutex, deadline))
		;
	g_assert_cmpuint(fixture.device.clears, >, clears);
	g_mutex_unlock(&fixture.device.mutex);
	g_mutex_lock(&fixture.sink->mutex);
	g_assert_cmpuint(fixture.sink->latencyClockTime, ==, GST_CLOCK_TIME_NONE);
	g_assert_cmpuint(fixture.sink->latency, ==, 400 * GST_MSECOND);
	g_mutex_unlock(&fixture.sink->mutex);
	clearLatencyFixture(&fixture);
}

static void testLatencyWithoutClock(void)
{
	LatencyFixture fixture;
	initLatencyFixture(&fixture);
	g_assert_true(gst_element_set_clock(fixture.harness->element, NULL));
	latencyFirstFrame(&fixture, 5 * GST_SECOND);
	g_mutex_lock(&fixture.sink->mutex);
	g_assert_cmpuint(fixture.sink->latencyClockTime, ==, GST_CLOCK_TIME_NONE);
	g_mutex_unlock(&fixture.sink->mutex);
	signalLatencyPts(&fixture, 11 * GST_SECOND, 450000, FALSE, GST_SECOND);
	clearLatencyFixture(&fixture);
}

int main(int argc, char **argv)
{
	gst_init(&argc, &argv);
	g_test_init(&argc, &argv, NULL);
	g_test_add_func("/sink/decoder-time-contract", testDecoderTimeContract);
	g_test_add_func("/sink/state-and-pause", testStateAndPause);
	g_test_add_func("/sink/flush-drops-blocked-frame", testFlushDropsBlockedFrame);
	g_test_add_data_func("/sink/segment-discont-preserves-submitted", GINT_TO_POINTER(FALSE), testSegmentDiscontPreservesFrames);
	g_test_add_data_func("/sink/segment-discont-preserves-queued", GINT_TO_POINTER(TRUE), testSegmentDiscontPreservesFrames);
	g_test_add_func("/sink/stop-with-blocked-decoder", testStopWhileDecoderBlocked);
	g_test_add_func("/sink/caps-change", testCapsChange);
	g_test_add_func("/sink/async-eos-only-preroll", testAsyncEosOnlyPreroll);
	g_test_add_func("/sink/async-buffer-preroll-once", testAsyncBufferPrerollOnce);
	g_test_add_data_func("/sink/pause-during-full-queue", GINT_TO_POINTER(FALSE), testFullQueuePause);
	g_test_add_data_func("/sink/preroll-after-full-queue-pause", GINT_TO_POINTER(TRUE), testFullQueuePause);
	g_test_add_func("/sink/dts-timestamp-fallback", testDtsTimestampFallback);
	g_test_add_data_func("/sink/independent-pts-precedence", GINT_TO_POINTER(FALSE), testIndependentPtsPrecedence);
	g_test_add_data_func("/sink/independent-pts-without-dts", GINT_TO_POINTER(TRUE), testIndependentPtsPrecedence);
	g_test_add_func("/sink/timestamp-stream-lifetime", testTimestampStreamLifetime);
	g_test_add_func("/sink/timestamp-queue-order", testTimestampQueueOrder);
	g_test_add_func("/sink/h263-state-lifecycle", testH263StateLifecycle);
	g_test_add_data_func("/sink/spark-missing-size", "video/x-flash-video,flvversion=1", testSparkSize);
	g_test_add_data_func("/sink/spark-wrong-size", "video/x-flash-video,flvversion=1,width=1920,height=1080", testSparkSize);
	g_test_add_data_func("/sink/spark-known-size", "video/x-flash-video,flvversion=1,width=16,height=16,framerate=12/1", testSparkSize);
	g_test_add_func("/sink/vc1-delayed-drain", testVc1DelayedDrain);
	g_test_add_func("/sink/vc1-new-stream-discards-lookahead", testVc1NewStreamDiscardsLookahead);
	g_test_add_func("/sink/eos-with-paused-queue", testEosWithPausedQueue);
	g_test_add_func("/sink/new-stream-drops-old-queue", testNewStreamDropsOldQueue);
	g_test_add_data_func("/sink/discontinuity-recovers-blocked-prepare", GINT_TO_POINTER(TRUE), testBlockedPrepareCancellation);
	g_test_add_data_func("/sink/flush-cancels-blocked-prepare", GINT_TO_POINTER(FALSE), testBlockedPrepareCancellation);
	g_test_add_func("/sink/recovery-before-eos-marker", testRecoveryBeforeEosMarker);
	g_test_add_func("/sink/eos-drain-no-normal-poll", testEosDrainNoNormalPoll);
	g_test_add_func("/sink/eos-marker-retries", testEosMarkerRetries);
	g_test_add_data_func("/sink/eos-marker-retry-timeout", GINT_TO_POINTER(0), testEosMarkerFailure);
	g_test_add_data_func("/sink/eos-marker-io-error", GINT_TO_POINTER(1), testEosMarkerFailure);
	g_test_add_data_func("/sink/eos-marker-memory-error", GINT_TO_POINTER(2), testEosMarkerFailure);
	g_test_add_data_func("/sink/eos-marker-unexpected-result", GINT_TO_POINTER(3), testEosMarkerFailure);
	g_test_add_func("/sink/pause-during-eos-marker-retry", testPauseDuringEosMarkerRetry);
	g_test_add_data_func("/sink/flush-during-eos-marker-retry", GINT_TO_POINTER(FALSE), testCancelEosMarkerRetry);
	g_test_add_data_func("/sink/stop-during-eos-marker-retry", GINT_TO_POINTER(TRUE), testCancelEosMarkerRetry);
	g_test_add_data_func("/sink/eos-progress-deadline", GINT_TO_POINTER(FALSE), testEosProgressDeadline);
	g_test_add_data_func("/sink/eos-progress-wrap", GINT_TO_POINTER(TRUE), testEosProgressDeadline);
	g_test_add_data_func("/sink/eos-progress-reverse", GINT_TO_POINTER(2), testEosProgressDeadline);
	g_test_add_data_func("/sink/eos-stalled-deadline", GINT_TO_POINTER(0), testEosStallDeadline);
	g_test_add_data_func("/sink/eos-invalid-pts-deadline", GINT_TO_POINTER(1), testEosStallDeadline);
	g_test_add_data_func("/sink/eos-backwards-pts-deadline", GINT_TO_POINTER(2), testEosStallDeadline);
	g_test_add_func("/sink/flush-during-eos-drain", testFlushDuringEosDrain);
	g_test_add_func("/sink/pause-during-eos-drain", testPauseDuringEosDrain);
	g_test_add_func("/sink/stop-during-eos-drain", testStopDuringEosDrain);
	g_test_add_func("/sink/permanent-decoder-error", testPermanentDecoderError);
	g_test_add_func("/sink/empty-successful-decoder-event", testEmptySuccessfulDecoderEvent);
	g_test_add_func("/sink/writable-readiness", testWritableReadiness);
	g_test_add_data_func("/sink/flush-cancels-writable-wait", GINT_TO_POINTER(FALSE), testWritableCancellation);
	g_test_add_data_func("/sink/stop-cancels-writable-wait", GINT_TO_POINTER(TRUE), testWritableCancellation);
	g_test_add_func("/sink/partial-writable-readiness", testPartialWritableReadiness);
	g_test_add_func("/sink/writable-timeout", testWritableTimeout);
	g_test_add_func("/sink/latency-default-query", testLatencyDefaultQuery);
	g_test_add_func("/sink/latency-submission-anchor", testLatencySubmissionAnchor);
	g_test_add_func("/sink/latency-invalid-measurements", testLatencyInvalidMeasurements);
	g_test_add_func("/sink/latency-pts-wrap", testLatencyPtsWrap);
	g_test_add_func("/sink/latency-lifetime", testLatencyLifetime);
	g_test_add_func("/sink/latency-without-clock", testLatencyWithoutClock);
	return g_test_run();
}
