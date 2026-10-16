/* SPDX-License-Identifier: LGPL-2.1-or-later */
#include "dream_overlay.h"
#include "gstdreamvideosink.h"

#include <gst/check/gsttestclock.h>
#include <string.h>

typedef struct {
	GstDreamOverlayCompositor *compositor;
	GstClock *clock;
	guint8 *surface;
	guint notifications, clears, rectangles;
	DreamOverlayRectangle last;
	gboolean detachOnNotification, clearOnNotification;
} OverlayFixture;

static void haveOverlay(GstDreamOverlayCompositor *compositor, gpointer data, gpointer userData)
{
	OverlayFixture *fixture = userData;
	GList *rectangles = data;
	/* A legacy observer may snapshot public data under the inherited lock.
	 * The compositor must release that lock before invoking a host handler. */
	GST_OBJECT_LOCK(compositor);
	g_assert_nonnull(compositor->vinfo);
	g_assert_cmpuint(compositor->vinfo->size, >, 0);
	GST_OBJECT_UNLOCK(compositor);
	fixture->notifications++;
	if (!rectangles)
		fixture->clears++;
	for (GList *item = rectangles; item; item = item->next) {
		fixture->rectangles++;
		fixture->last = *(DreamOverlayRectangle *) item->data;
	}
	dream_overlay_rectangles_free(rectangles);
	if (fixture->detachOnNotification)
		g_signal_emit_by_name(compositor, "set-pointer", NULL);
	if (fixture->clearOnNotification) {
		fixture->clearOnNotification = FALSE;
		gst_dream_overlay_compositor_free_overlays_stop_syncers(compositor);
	}
}

static void fixtureInit(OverlayFixture *fixture)
{
	GstVideoInfo info;
	GstSegment segment;
	memset(fixture, 0, sizeof(*fixture));
	fixture->compositor = gst_dream_overlay_compositor_new();
	gst_object_ref_sink(fixture->compositor);
	fixture->clock = gst_test_clock_new_with_start_time(0);
	gst_video_info_init(&info);
	g_assert_true(gst_video_info_set_format(&info, GST_VIDEO_FORMAT_ENCODED, 8, 4));
	g_assert_true(gst_dream_overlay_compositor_set_vinfo(fixture->compositor, &info));
	g_assert_cmpint(fixture->compositor->vinfo->stride[0], ==, 256);
	g_assert_cmpuint(fixture->compositor->vinfo->size, ==, 1024);
	fixture->surface = g_malloc0(fixture->compositor->vinfo->size);
	g_signal_connect(fixture->compositor, "have-overlay", G_CALLBACK(haveOverlay), fixture);
	g_signal_emit_by_name(fixture->compositor, "set-pointer", fixture->surface);
	gst_segment_init(&segment, GST_FORMAT_TIME);
	gst_dream_overlay_compositor_set_timing(fixture->compositor, &segment, 0);
	gst_dream_overlay_compositor_set_paused(fixture->compositor, FALSE);
}

static void fixtureClear(OverlayFixture *fixture)
{
	gst_dream_overlay_compositor_free_overlays_stop_syncers(fixture->compositor);
	g_signal_emit_by_name(fixture->compositor, "set-pointer", NULL);
	gst_object_unref(fixture->compositor);
	gst_object_unref(fixture->clock);
	g_free(fixture->surface);
}

static GstBuffer *makeOverlay(GstClockTime pts, gint x, gint y, gfloat alpha)
{
	const guint32 pixels[] = {0xffff0000, 0xff00ff00, 0xff0000ff, 0xffffffff};
	GstBuffer *image = gst_buffer_new_allocate(NULL, sizeof(pixels), NULL);
	g_assert_cmpuint(gst_buffer_fill(image, 0, pixels, sizeof(pixels)), ==, sizeof(pixels));
#if G_BYTE_ORDER == G_LITTLE_ENDIAN
	GstVideoFormat format = GST_VIDEO_FORMAT_BGRA;
#else
	GstVideoFormat format = GST_VIDEO_FORMAT_ARGB;
#endif
	g_assert_nonnull(gst_buffer_add_video_meta(image, GST_VIDEO_FRAME_FLAG_NONE, format, 2, 2));
	GstVideoOverlayRectangle *rectangle = gst_video_overlay_rectangle_new_raw(image,
		x, y, 2, 2, GST_VIDEO_OVERLAY_FORMAT_FLAG_PREMULTIPLIED_ALPHA);
	g_assert_nonnull(rectangle);
	gst_buffer_unref(image);
	g_assert_true(gst_mini_object_is_writable(GST_MINI_OBJECT_CAST(rectangle)));
	gst_video_overlay_rectangle_set_global_alpha(rectangle, alpha);
	GstVideoOverlayComposition *composition = gst_video_overlay_composition_new(rectangle);
	GstBuffer *buffer = gst_buffer_new();
	GST_BUFFER_PTS(buffer) = pts;
	g_assert_nonnull(gst_buffer_add_video_overlay_composition_meta(buffer, composition));
	gst_video_overlay_composition_unref(composition);
	gst_video_overlay_rectangle_unref(rectangle);
	return buffer;
}

static guint32 pixelAt(OverlayFixture *fixture, guint x, guint y)
{
	guint32 pixel;
	memcpy(&pixel, fixture->surface + y * fixture->compositor->vinfo->stride[0] + x * 4, sizeof(pixel));
	return pixel;
}

static void processAt(OverlayFixture *fixture, GstClockTime time)
{
	GstTestClock *clock = GST_TEST_CLOCK(fixture->clock);
	g_assert_true(gst_test_clock_peek_next_pending_id(clock, NULL));
	g_assert_cmpuint(gst_test_clock_get_next_entry_time(clock), ==, time);
	gst_test_clock_set_time(clock, time);
	GstClockID id = gst_test_clock_process_next_clock_id(clock);
	g_assert_nonnull(id);
	gst_clock_id_unref(id);
}

static void testCompositorSignal(void)
{
	/* Static core linkage avoids loading a second copy of the same GTypes. */
	GstElement *sink = g_object_new(GST_TYPE_DREAM_VIDEO_SINK, NULL);
	gpointer compositor = NULL;
	GSignalQuery query = {0};
	guint signal = g_signal_lookup("get-overlay-compositor", G_OBJECT_TYPE(sink));
	g_assert_cmpuint(signal, !=, 0);
	g_signal_query(signal, &query);
	g_assert_cmpuint(query.return_type, ==, G_TYPE_POINTER);
	g_assert_cmpuint(query.n_params, ==, 0);
	g_signal_emit(sink, signal, 0, &compositor);
	g_assert_nonnull(compositor);
	g_assert_true(G_TYPE_CHECK_INSTANCE_TYPE(compositor, GST_TYPE_DREAM_OVERLAY_COMPOSITOR));
	guint have = g_signal_lookup("have-overlay", G_OBJECT_TYPE(compositor));
	g_signal_query(have, &query);
	g_assert_cmpuint(query.return_type, ==, G_TYPE_NONE);
	g_assert_cmpuint(query.n_params, ==, 1);
	g_assert_cmpuint(query.param_types[0], ==, G_TYPE_POINTER);
	guint set = g_signal_lookup("set-pointer", G_OBJECT_TYPE(compositor));
	g_signal_query(set, &query);
	g_assert_true((query.signal_flags & G_SIGNAL_ACTION) != 0);
	g_assert_cmpuint(query.param_types[0], ==, G_TYPE_POINTER);
	gst_object_unref(sink);
}

static void testOverlayNegotiation(void)
{
	GstElement *sink = g_object_new(GST_TYPE_DREAM_VIDEO_SINK, NULL);
	GstPad *pad = gst_element_get_static_pad(sink, "sink");
	GstCaps *plain = gst_caps_from_string("video/x-h264,parsed=(boolean)true,stream-format=byte-stream,alignment=au,width=1280,height=720,framerate=25/1");
	GstCaps *overlay = gst_caps_copy(plain);
	gst_caps_set_features(overlay, 0,
		gst_caps_features_new(GST_CAPS_FEATURE_META_GST_VIDEO_OVERLAY_COMPOSITION, NULL));
	g_assert_true(gst_pad_query_accept_caps(pad, plain));
	g_assert_true(gst_pad_query_accept_caps(pad, overlay));
	gst_caps_features_add(gst_caps_get_features(overlay, 0), GST_CAPS_FEATURE_MEMORY_SYSTEM_MEMORY);
	g_assert_true(gst_pad_query_accept_caps(pad, overlay));
	GstQuery *query = gst_query_new_allocation(overlay, FALSE);
	GstBaseSinkClass *klass = GST_BASE_SINK_GET_CLASS(sink);
	g_assert_nonnull(klass->propose_allocation);
	g_assert_true(klass->propose_allocation(GST_BASE_SINK(sink), query));
	g_assert_true(gst_query_find_allocation_meta(query,
		GST_VIDEO_OVERLAY_COMPOSITION_META_API_TYPE, NULL));
	gst_query_unref(query);
	gst_caps_unref(plain);
	gst_caps_unref(overlay);
	gst_object_unref(pad);
	gst_object_unref(sink);
}

static void testDrawAndHide(void)
{
	OverlayFixture fixture;
	fixtureInit(&fixture);
	GstBuffer *buffer = makeOverlay(GST_SECOND, 1, 1, 1.0f);
	g_assert_true(gst_dream_overlay_compositor_save_overlays(fixture.compositor, fixture.clock, 0, buffer));
	gst_buffer_unref(buffer);
	g_assert_cmpuint(pixelAt(&fixture, 1, 1), ==, 0);
	processAt(&fixture, 950 * GST_MSECOND);
	g_assert_cmpuint(pixelAt(&fixture, 1, 1), ==, 0xffff0000);
	g_assert_cmpuint(pixelAt(&fixture, 2, 1), ==, 0xff00ff00);
	g_assert_cmpuint(pixelAt(&fixture, 1, 2), ==, 0xff0000ff);
	g_assert_cmpuint(pixelAt(&fixture, 2, 2), ==, 0xffffffff);
	g_assert_cmpuint(fixture.notifications, ==, 1);
	g_assert_cmpuint(fixture.rectangles, ==, 1);
	g_assert_cmpint(fixture.last.x, ==, 1);
	buffer = gst_buffer_new();
	GST_BUFFER_PTS(buffer) = 2 * GST_SECOND;
	g_assert_true(gst_dream_overlay_compositor_save_overlays(fixture.compositor, fixture.clock, GST_SECOND, buffer));
	gst_buffer_unref(buffer);
	processAt(&fixture, 1900 * GST_MSECOND);
	g_assert_cmpuint(pixelAt(&fixture, 1, 1), ==, 0);
	g_assert_cmpuint(fixture.notifications, ==, 2);
	g_assert_cmpuint(fixture.rectangles, ==, 2);
	fixtureClear(&fixture);
}

static void testClipAndAlpha(void)
{
	OverlayFixture fixture;
	fixtureInit(&fixture);
	GstBuffer *buffer = makeOverlay(GST_SECOND, -1, -1, 0.5f);
	g_assert_true(gst_dream_overlay_compositor_save_overlays(fixture.compositor, fixture.clock, 0, buffer));
	gst_buffer_unref(buffer);
	processAt(&fixture, 950 * GST_MSECOND);
	guint32 pixel = pixelAt(&fixture, 0, 0);
	g_assert_cmpuint(pixel >> 24, >=, 127);
	g_assert_cmpuint(pixel >> 24, <=, 128);
	g_assert_cmpuint(pixel & 0xff, >=, 127);
	g_assert_cmpuint(pixel & 0xff, <=, 128);
	g_assert_cmpuint(pixelAt(&fixture, 1, 0), ==, 0);
	g_assert_cmpint(fixture.last.x, ==, 0);
	g_assert_cmpint(fixture.last.y, ==, 0);
	g_assert_cmpint(fixture.last.width, ==, 1);
	g_assert_cmpint(fixture.last.height, ==, 1);
	fixtureClear(&fixture);
}

static void testPauseAndResume(void)
{
	OverlayFixture fixture;
	fixtureInit(&fixture);
	GstBuffer *buffer = makeOverlay(2 * GST_SECOND, 0, 0, 1.0f);
	g_assert_true(gst_dream_overlay_compositor_save_overlays(fixture.compositor, fixture.clock, 0, buffer));
	gst_buffer_unref(buffer);
	gst_test_clock_set_time(GST_TEST_CLOCK(fixture.clock), GST_SECOND);
	gst_dream_overlay_compositor_set_paused(fixture.compositor, TRUE);
	g_assert_cmpuint(gst_test_clock_peek_id_count(GST_TEST_CLOCK(fixture.clock)), ==, 0);
	gst_test_clock_set_time(GST_TEST_CLOCK(fixture.clock), 10 * GST_SECOND);
	g_assert_cmpuint(fixture.notifications, ==, 0);
	gst_dream_overlay_compositor_set_paused(fixture.compositor, FALSE);
	processAt(&fixture, 10950 * GST_MSECOND);
	g_assert_cmpuint(pixelAt(&fixture, 0, 0), ==, 0xffff0000);
	fixtureClear(&fixture);
}

static void weakDestroyed(gpointer data, GObject *object)
{
	(void) object;
	*(gboolean *) data = TRUE;
}

static void testCancelAndOwnership(void)
{
	OverlayFixture fixture;
	gboolean destroyed = FALSE;
	fixtureInit(&fixture);
	g_object_weak_ref(G_OBJECT(fixture.compositor), weakDestroyed, &destroyed);
	GstBuffer *buffer = makeOverlay(5 * GST_SECOND, 0, 0, 1.0f);
	g_assert_true(gst_dream_overlay_compositor_save_overlays(fixture.compositor, fixture.clock, 0, buffer));
	gst_buffer_unref(buffer);
	gst_dream_overlay_compositor_free_overlays_stop_syncers(fixture.compositor);
	g_assert_cmpuint(gst_test_clock_peek_id_count(GST_TEST_CLOCK(fixture.clock)), ==, 0);
	g_assert_cmpuint(fixture.clears, ==, 1);
	gst_test_clock_set_time(GST_TEST_CLOCK(fixture.clock), 10 * GST_SECOND);
	g_assert_cmpuint(pixelAt(&fixture, 0, 0), ==, 0);
	fixtureClear(&fixture);
	g_assert_true(destroyed);
}

static void testSegmentAndDetach(void)
{
	OverlayFixture fixture;
	GstSegment segment;
	fixtureInit(&fixture);
	gst_segment_init(&segment, GST_FORMAT_TIME);
	segment.start = 100 * GST_SECOND;
	segment.time = 100 * GST_SECOND;
	segment.rate = 2.0;
	gst_dream_overlay_compositor_set_timing(fixture.compositor, &segment, 0);
	GstBuffer *buffer = makeOverlay(104 * GST_SECOND, 0, 0, 1.0f);
	g_assert_true(gst_dream_overlay_compositor_save_overlays(fixture.compositor, fixture.clock,
		100 * GST_SECOND, buffer));
	gst_buffer_unref(buffer);
	fixture.detachOnNotification = TRUE;
	processAt(&fixture, 1950 * GST_MSECOND);
	g_assert_cmpuint(pixelAt(&fixture, 0, 0), ==, 0xffff0000);
	memset(fixture.surface, 0x5a, fixture.compositor->vinfo->size);
	gst_dream_overlay_compositor_free_overlays_stop_syncers(fixture.compositor);
	g_assert_cmpuint(fixture.surface[0], ==, 0x5a);
	fixtureClear(&fixture);
}

static void testNoOverlayWithoutTiming(void)
{
	GstDreamOverlayCompositor *compositor = gst_dream_overlay_compositor_new();
	GstBuffer *buffer = gst_buffer_new();
	g_assert_true(gst_dream_overlay_compositor_save_overlays(compositor, NULL,
		GST_CLOCK_TIME_NONE, buffer));
	gst_buffer_unref(buffer);
	gst_object_unref(compositor);
}

static void testResizeDetachesSurface(void)
{
	OverlayFixture fixture;
	GstVideoInfo info;
	fixtureInit(&fixture);
	guint8 *oldSurface = fixture.surface;
	gst_video_info_init(&info);
	g_assert_true(gst_video_info_set_format(&info, GST_VIDEO_FORMAT_ENCODED, 65, 5));
	info.fps_n = 25;
	info.fps_d = 1;
	g_assert_true(gst_dream_overlay_compositor_set_vinfo(fixture.compositor, &info));
	g_assert_cmpint(fixture.compositor->vinfo->width, ==, 128);
	g_assert_cmpint(fixture.compositor->vinfo->stride[0], ==, 512);
	g_assert_cmpuint(fixture.compositor->vinfo->size, ==, 2560);
	g_assert_cmpint(fixture.compositor->vinfo->fps_n, ==, 25);
	g_assert_cmpint(GST_VIDEO_INFO_FORMAT(fixture.compositor->vinfo), ==, GST_VIDEO_FORMAT_ENCODED);
	memset(oldSurface, 0x5a, 1024);
	GstBuffer *buffer = makeOverlay(GST_SECOND, 0, 0, 1.0f);
	g_assert_true(gst_dream_overlay_compositor_save_overlays(fixture.compositor, fixture.clock, 0, buffer));
	gst_buffer_unref(buffer);
	processAt(&fixture, 950 * GST_MSECOND);
	for (guint index = 0; index < 1024; index++)
		g_assert_cmpuint(oldSurface[index], ==, 0x5a);
	fixture.surface = g_malloc0(fixture.compositor->vinfo->size);
	g_signal_emit_by_name(fixture.compositor, "set-pointer", fixture.surface);
	g_assert_cmpuint(pixelAt(&fixture, 0, 0), ==, 0xffff0000);
	g_free(oldSurface);
	fixtureClear(&fixture);
}

static void testQueueBounds(void)
{
	OverlayFixture fixture;
	fixtureInit(&fixture);
	for (guint index = 1; index <= 128; index++) {
		GstBuffer *buffer = makeOverlay(index * GST_MSECOND, 0, 0, 1.0f);
		g_assert_true(gst_dream_overlay_compositor_save_overlays(fixture.compositor, fixture.clock, 0, buffer));
		gst_buffer_unref(buffer);
	}
	GstBuffer *buffer = makeOverlay(GST_SECOND, 0, 0, 1.0f);
	g_assert_false(gst_dream_overlay_compositor_save_overlays(fixture.compositor, fixture.clock, 0, buffer));
	gst_buffer_unref(buffer);
	g_assert_cmpuint(gst_test_clock_peek_id_count(GST_TEST_CLOCK(fixture.clock)), ==, 128);
	gst_dream_overlay_compositor_set_paused(fixture.compositor, TRUE);
	gst_dream_overlay_compositor_free_overlays_stop_syncers(fixture.compositor);
	g_assert_cmpuint(gst_test_clock_peek_id_count(GST_TEST_CLOCK(fixture.clock)), ==, 0);
	/* Clearing must also reset the remembered sequence for metadata-free video. */
	buffer = gst_buffer_new();
	g_assert_true(gst_dream_overlay_compositor_save_overlays(fixture.compositor, NULL, GST_CLOCK_TIME_NONE, buffer));
	gst_buffer_unref(buffer);
	fixtureClear(&fixture);
}

static void testClearFromCallback(void)
{
	OverlayFixture fixture;
	gboolean destroyed = FALSE;
	fixtureInit(&fixture);
	g_object_weak_ref(G_OBJECT(fixture.compositor), weakDestroyed, &destroyed);
	for (guint index = 1; index <= 2; index++) {
		GstBuffer *buffer = makeOverlay(index * GST_SECOND, 0, 0, 1.0f);
		g_assert_true(gst_dream_overlay_compositor_save_overlays(fixture.compositor, fixture.clock, 0, buffer));
		gst_buffer_unref(buffer);
	}
	fixture.clearOnNotification = TRUE;
	processAt(&fixture, 950 * GST_MSECOND);
	g_assert_cmpuint(fixture.notifications, ==, 2);
	g_assert_cmpuint(fixture.clears, ==, 1);
	g_assert_cmpuint(pixelAt(&fixture, 0, 0), ==, 0);
	g_assert_cmpuint(gst_test_clock_peek_id_count(GST_TEST_CLOCK(fixture.clock)), ==, 0);
	fixtureClear(&fixture);
	g_assert_true(destroyed);
}

int main(int argc, char **argv)
{
	gst_init(&argc, &argv);
	g_test_init(&argc, &argv, NULL);
	g_test_add_func("/overlay/public-signals", testCompositorSignal);
	g_test_add_func("/overlay/negotiation", testOverlayNegotiation);
	g_test_add_func("/overlay/draw-and-hide", testDrawAndHide);
	g_test_add_func("/overlay/clip-and-alpha", testClipAndAlpha);
	g_test_add_func("/overlay/pause-and-resume", testPauseAndResume);
	g_test_add_func("/overlay/cancel-and-ownership", testCancelAndOwnership);
	g_test_add_func("/overlay/segment-and-detach", testSegmentAndDetach);
	g_test_add_func("/overlay/no-meta-no-timing", testNoOverlayWithoutTiming);
	g_test_add_func("/overlay/resize-detaches-surface", testResizeDetachesSurface);
	g_test_add_func("/overlay/bounded-paused-queue", testQueueBounds);
	g_test_add_func("/overlay/clear-from-host-callback", testClearFromCallback);
	return g_test_run();
}
