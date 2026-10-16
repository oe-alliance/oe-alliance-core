/* SPDX-License-Identifier: LGPL-2.1-or-later */
#include "dream_video_device.h"

#include <errno.h>
#include <gst/gst.h>
#include <libudev.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>

typedef struct {
	unsigned long request;
	int result;
	int errorNumber;
} IoctlStep;

typedef struct {
	const IoctlStep *steps;
	guint count;
	guint calls;
	uintptr_t arguments[32];
	DreamVideoFrame frames[32];
	DreamVideoSysinfo sysinfos[32];
} MockIoctl;

/* Only this callback sees the fake FD. No test opens or closes a decoder. */
static int mock_ioctl(int fd, unsigned long request, uintptr_t argument,
	gpointer userData)
{
	MockIoctl *mock = userData;
	guint index = mock->calls++;
	const IoctlStep *step;

	g_assert_cmpint(fd, ==, 42);
	g_assert_cmpuint(index, <, mock->count);
	g_assert_cmpuint(index, <, G_N_ELEMENTS(mock->arguments));
	step = &mock->steps[index];
	g_assert_cmpuint(request, ==, step->request);
	mock->arguments[index] = argument;
	if (request == DREAM_VIDEO_SET_FRAME)
		mock->frames[index] = *(const DreamVideoFrame *) argument;
	else if (request == DREAM_VIDEO_SET_SYSINFO)
		mock->sysinfos[index] = *(const DreamVideoSysinfo *) argument;
	errno = step->errorNumber;
	return step->result;
}

static void init_device(DreamVideoDevice *device, MockIoctl *mock,
	const IoctlStep *steps, guint count)
{
	dream_video_device_init(device);
	memset(mock, 0, sizeof(*mock));
	mock->steps = steps;
	mock->count = count;
	device->videoFd = 42;
	device->ioctlCall = mock_ioctl;
	device->ioctlData = mock;
}

static void assert_frame(const DreamVideoFrame *frame, const guint8 *data,
	gsize size, guint64 pts)
{
	g_assert_cmpuint(frame->pts, ==, pts);
	g_assert_cmpint(frame->bytes[0], ==, (ssize_t) size);
	g_assert_true(frame->data[0] == data);
	g_assert_cmpint(frame->is_phys_addr[0], ==, 0);
	for (guint i = 1; i < G_N_ELEMENTS(frame->bytes); ++i) {
		g_assert_cmpint(frame->bytes[i], ==, 0);
		g_assert_null(frame->data[i]);
		g_assert_cmpint(frame->is_phys_addr[i], ==, 0);
	}
}

static void test_abi(void)
{
	g_assert_cmpuint(sizeof(void *), ==, 8);
	g_assert_cmpuint(sizeof(DreamVideoFrame), ==, 168);
	g_assert_cmpuint(G_STRUCT_OFFSET(DreamVideoFrame, pts), ==, 0);
	g_assert_cmpuint(G_STRUCT_OFFSET(DreamVideoFrame, bytes), ==, 8);
	g_assert_cmpuint(G_STRUCT_OFFSET(DreamVideoFrame, data), ==, 72);
	g_assert_cmpuint(G_STRUCT_OFFSET(DreamVideoFrame, is_phys_addr), ==, 136);
	g_assert_cmpuint(sizeof(DreamVideoSysinfo), ==, 48);
	g_assert_cmpuint(G_STRUCT_OFFSET(DreamVideoSysinfo, rate), ==, 12);
	g_assert_cmpuint(G_STRUCT_OFFSET(DreamVideoSysinfo, param), ==, 32);
	g_assert_cmpuint(G_STRUCT_OFFSET(DreamVideoSysinfo, ratio64), ==, 40);
	g_assert_cmpuint(DREAM_VIDEO_SET_FRAME, ==, 0xc0a86f40UL);
	g_assert_cmpuint(DREAM_VIDEO_SET_SYSINFO, ==, 0xc0306f41UL);
	g_assert_cmpuint(_IOC_SIZE(DREAM_VIDEO_SET_FRAME), ==, 168);
	g_assert_cmpuint(_IOC_SIZE(DREAM_VIDEO_SET_SYSINFO), ==, 48);
	g_assert_cmpuint(_IOC_TYPE(DREAM_VIDEO_SET_FRAME), ==, 'o');
	g_assert_cmpuint(_IOC_NR(DREAM_VIDEO_SET_FRAME), ==, 64);
	g_assert_cmpuint(_IOC_NR(DREAM_VIDEO_SET_SYSINFO), ==, 65);
}

static void test_initial_state(void)
{
	DreamVideoDevice device;

	memset(&device, 0xff, sizeof(device));
	dream_video_device_init(&device);
	g_assert_cmpint(device.videoFd, ==, -1);
	g_assert_cmpint(device.pollFd, ==, -1);
	g_assert_cmpint(device.wakeFd[0], ==, -1);
	g_assert_cmpint(device.wakeFd[1], ==, -1);
	g_assert_false(device.playing);
	g_assert_null(device.udev);
	g_assert_null(device.monitor);
	g_assert_nonnull(device.ioctlCall);
	g_assert_null(device.ioctlData);
}

static void test_partial_writes(void)
{
	const IoctlStep steps[] = {
		{DREAM_VIDEO_SET_FRAME, 3, 0},
		{DREAM_VIDEO_SET_FRAME, 2, 0},
		{DREAM_VIDEO_SET_FRAME, 3, 0}
	};
	const guint8 data[] = {1, 2, 3, 4, 5, 6, 7, 8};
	DreamVideoDevice device;
	MockIoctl mock;
	GError *error = NULL;
	gsize offset = 0;

	init_device(&device, &mock, steps, G_N_ELEMENTS(steps));
	g_assert_cmpint(dream_video_device_submit(&device, data, sizeof(data),
		&offset, 90000, &error), ==, DREAM_WRITE_AGAIN);
	g_assert_no_error(error);
	g_assert_cmpuint(offset, ==, 3);
	assert_frame(&mock.frames[0], data, 8, 90000);
	g_assert_cmpint(dream_video_device_submit(&device, data, sizeof(data),
		&offset, 90000, &error), ==, DREAM_WRITE_AGAIN);
	g_assert_no_error(error);
	g_assert_cmpuint(offset, ==, 5);
	assert_frame(&mock.frames[1], data + 3, 5, DREAM_VIDEO_PTS_NONE);
	g_assert_cmpint(dream_video_device_submit(&device, data, sizeof(data),
		&offset, 90000, &error), ==, DREAM_WRITE_DONE);
	g_assert_no_error(error);
	g_assert_cmpuint(offset, ==, sizeof(data));
	assert_frame(&mock.frames[2], data + 5, 3, DREAM_VIDEO_PTS_NONE);
	/* Re-entering a completed frame must never submit it a second time. */
	g_assert_cmpint(dream_video_device_submit(&device, data, sizeof(data),
		&offset, 90000, &error), ==, DREAM_WRITE_DONE);
	g_assert_no_error(error);
	g_assert_cmpuint(mock.calls, ==, G_N_ELEMENTS(steps));
}

static void test_retry_and_zero_progress(void)
{
	const IoctlStep steps[] = {
		{DREAM_VIDEO_SET_FRAME, -1, EAGAIN},
		{DREAM_VIDEO_SET_FRAME, 0, 0},
		{DREAM_VIDEO_SET_FRAME, -1, EINTR},
		{DREAM_VIDEO_SET_FRAME, -1, EINTR},
		{DREAM_VIDEO_SET_FRAME, 2, 0}
	};
	const guint8 data[] = {0x65, 0xaa};
	DreamVideoDevice device;
	MockIoctl mock;
	GError *error = NULL;
	gsize offset = 0;

	init_device(&device, &mock, steps, G_N_ELEMENTS(steps));
	for (guint i = 0; i < 2; ++i) {
		g_assert_cmpint(dream_video_device_submit(&device, data, sizeof(data),
			&offset, 12345, &error), ==, DREAM_WRITE_AGAIN);
		g_assert_no_error(error);
		g_assert_cmpuint(offset, ==, 0);
		g_assert_cmpuint(mock.calls, ==, i + 1);
	}
	g_assert_cmpint(dream_video_device_submit(&device, data, sizeof(data),
		&offset, 12345, &error), ==, DREAM_WRITE_DONE);
	g_assert_no_error(error);
	g_assert_cmpuint(offset, ==, sizeof(data));
	g_assert_cmpuint(mock.calls, ==, G_N_ELEMENTS(steps));
	/* A retry before ANY accepted byte still carries the original PTS. */
	for (guint i = 0; i < mock.calls; ++i)
		assert_frame(&mock.frames[i], data, sizeof(data), 12345);
}

static void test_bounded_interruptions(void)
{
	IoctlStep steps[9];
	const guint8 data[] = {0x65};
	DreamVideoDevice device;
	MockIoctl mock;
	GError *error = NULL;
	gsize offset = 0;

	for (guint i = 0; i < 8; ++i)
		steps[i] = (IoctlStep) {DREAM_VIDEO_SET_FRAME, -1, EINTR};
	steps[8] = (IoctlStep) {DREAM_VIDEO_SET_FRAME, 1, 0};
	init_device(&device, &mock, steps, G_N_ELEMENTS(steps));
	g_assert_cmpint(dream_video_device_submit(&device, data, sizeof(data),
		&offset, 90000, &error), ==, DREAM_WRITE_AGAIN);
	g_assert_no_error(error);
	g_assert_cmpuint(offset, ==, 0);
	g_assert_cmpuint(mock.calls, ==, 8);
	g_assert_cmpint(dream_video_device_submit(&device, data, sizeof(data),
		&offset, 90000, &error), ==, DREAM_WRITE_DONE);
	g_assert_no_error(error);
	g_assert_cmpuint(mock.calls, ==, 9);
}

static void test_driver_overflow(void)
{
	const IoctlStep steps[] = {{DREAM_VIDEO_SET_FRAME, 5, 0}};
	const guint8 data[] = {1, 2, 3, 4, 5, 6};
	DreamVideoDevice device;
	MockIoctl mock;
	GError *error = NULL;
	gsize offset = 2;

	init_device(&device, &mock, steps, G_N_ELEMENTS(steps));
	g_assert_cmpint(dream_video_device_submit(&device, data, sizeof(data),
		&offset, 90000, &error), ==, DREAM_WRITE_ERROR);
	g_assert_error(error, G_FILE_ERROR, G_FILE_ERROR_FAILED);
	g_assert_cmpuint(offset, ==, 2);
	g_assert_cmpuint(mock.calls, ==, 1);
	assert_frame(&mock.frames[0], data + 2, 4, DREAM_VIDEO_PTS_NONE);
	g_clear_error(&error);
}

static void test_permanent_errors(void)
{
	const int errors[] = {ENOMEM, EIO, EBADF, ENODEV};
	const guint8 data[] = {1, 2, 3};

	for (guint i = 0; i < G_N_ELEMENTS(errors); ++i) {
		const IoctlStep steps[] = {{DREAM_VIDEO_SET_FRAME, -1, errors[i]}};
		DreamVideoDevice device;
		MockIoctl mock;
		GError *error = NULL;
		gsize offset = 1;

		init_device(&device, &mock, steps, G_N_ELEMENTS(steps));
		g_assert_cmpint(dream_video_device_submit(&device, data, sizeof(data),
			&offset, 90000, &error), ==, DREAM_WRITE_ERROR);
		g_assert_error(error, G_FILE_ERROR, (gint) g_file_error_from_errno(errors[i]));
		g_assert_nonnull(strstr(error->message, "VIDEO_SET_FRAME"));
		g_assert_cmpuint(offset, ==, 1);
		g_assert_cmpuint(mock.calls, ==, 1);
		g_clear_error(&error);
	}
}

static void test_invalid_bounds(void)
{
	const guint8 data[] = {1, 2};
	DreamVideoDevice device;
	MockIoctl mock;
	GError *error = NULL;
	gsize offset = 3;

	init_device(&device, &mock, NULL, 0);
	g_assert_cmpint(dream_video_device_submit(&device, data, sizeof(data),
		NULL, 0, &error), ==, DREAM_WRITE_ERROR);
	g_assert_error(error, G_FILE_ERROR, G_FILE_ERROR_INVAL);
	g_clear_error(&error);
	g_assert_cmpint(dream_video_device_submit(&device, data, sizeof(data),
		&offset, 0, &error), ==, DREAM_WRITE_ERROR);
	g_assert_error(error, G_FILE_ERROR, G_FILE_ERROR_INVAL);
	g_assert_cmpuint(offset, ==, 3);
	g_clear_error(&error);
	offset = 0;
	g_assert_cmpint(dream_video_device_submit(&device, NULL, 1,
		&offset, 0, &error), ==, DREAM_WRITE_ERROR);
	g_assert_error(error, G_FILE_ERROR, G_FILE_ERROR_INVAL);
	g_clear_error(&error);
	g_assert_cmpint(dream_video_device_submit(&device, data, (gsize) G_MAXSSIZE + 1,
		&offset, 0, &error), ==, DREAM_WRITE_ERROR);
	g_assert_error(error, G_FILE_ERROR, G_FILE_ERROR_INVAL);
	g_clear_error(&error);
	g_assert_cmpint(dream_video_device_submit(&device, NULL, 0,
		&offset, 0, &error), ==, DREAM_WRITE_DONE);
	g_assert_no_error(error);
	g_assert_cmpuint(mock.calls, ==, 0);
}

static void test_timestamp_scaling(void)
{
	const guint64 values[] = {
		0, 1, 11111, 11112, GST_SECOND, UINT64_C(95443717688888),
		UINT64_C(95443717688889), UINT64_C(1000000000000000), G_MAXUINT64 - 1
	};

	g_assert_cmpuint(dream_video_pts_from_ns(GST_CLOCK_TIME_NONE), ==, DREAM_VIDEO_PTS_NONE);
	g_assert_cmpuint(dream_video_pts_to_ns(DREAM_VIDEO_PTS_NONE), ==, GST_CLOCK_TIME_NONE);
	g_assert_cmpuint(dream_video_pts_from_ns(0), ==, 0);
	g_assert_cmpuint(dream_video_pts_to_ns(0), ==, 0);
	g_assert_cmpuint(dream_video_pts_from_ns(GST_SECOND), ==, 90000);
	g_assert_cmpuint(dream_video_pts_to_ns(90000), ==, GST_SECOND);
	for (guint i = 0; i < G_N_ELEMENTS(values); ++i) {
		guint64 ns = values[i];
		/* Independent quotient/remainder formula avoids ns * 9 overflow. */
		guint64 expected = ((ns / 100000) * 9 + (ns % 100000) * 9 / 100000)
			& DREAM_VIDEO_PTS_MASK;
		g_assert_cmpuint(dream_video_pts_from_ns(ns), ==, expected);
	}
	g_assert_cmpuint(dream_video_pts_from_ns(UINT64_C(95443717688888)), ==, DREAM_VIDEO_PTS_MASK);
	g_assert_cmpuint(dream_video_pts_from_ns(UINT64_C(95443717688889)), ==, 0);
	g_assert_cmpuint(dream_video_pts_to_ns(DREAM_VIDEO_PTS_MASK + 1), ==, 0);
	g_assert_cmpuint(dream_video_pts_to_ns((DREAM_VIDEO_PTS_MASK + 1) + 90000), ==, GST_SECOND);
	g_assert_cmpuint(dream_video_pts_to_ns(DREAM_VIDEO_PTS_MASK), ==,
		DREAM_VIDEO_PTS_MASK * 100000 / 9);
}

static void test_configure_order(void)
{
	const IoctlStep first[] = {
		{DREAM_VIDEO_SET_SYSINFO, 0, 0}, {VIDEO_SET_STREAMTYPE, 0, 0},
		{VIDEO_PLAY, 0, 0}, {VIDEO_FREEZE, 0, 0}
	};
	const IoctlStep repeat[] = {
		{VIDEO_STOP, 0, 0}, {DREAM_VIDEO_SET_SYSINFO, 0, 0},
		{VIDEO_SET_STREAMTYPE, 0, 0}, {VIDEO_PLAY, 0, 0}
	};
	DreamVideoSysinfo info = {.format = 4, .width = 1920, .height = 1080, .rate = 3203};
	DreamVideoDevice device;
	MockIoctl mock;
	GError *error = NULL;

	init_device(&device, &mock, first, G_N_ELEMENTS(first));
	g_assert_true(dream_video_device_configure(&device, &info, 1, TRUE, &error));
	g_assert_no_error(error);
	g_assert_true(device.playing);
	g_assert_cmpuint(mock.calls, ==, G_N_ELEMENTS(first));
	g_assert_true(mock.arguments[0] == (uintptr_t) &info);
	g_assert_cmpuint(mock.sysinfos[0].format, ==, info.format);
	g_assert_cmpuint(mock.sysinfos[0].width, ==, info.width);
	g_assert_cmpuint(mock.sysinfos[0].height, ==, info.height);
	g_assert_cmpuint(mock.sysinfos[0].rate, ==, info.rate);
	g_assert_cmpuint(mock.arguments[1], ==, 1);
	g_assert_cmpuint(mock.arguments[2], ==, 0);
	g_assert_cmpuint(mock.arguments[3], ==, 0);
	init_device(&device, &mock, repeat, G_N_ELEMENTS(repeat));
	device.playing = TRUE;
	g_assert_true(dream_video_device_configure(&device, &info, 22, FALSE, &error));
	g_assert_no_error(error);
	g_assert_true(device.playing);
	g_assert_cmpuint(mock.calls, ==, G_N_ELEMENTS(repeat));
	g_assert_cmpuint(mock.arguments[0], ==, 0);
	g_assert_true(mock.arguments[1] == (uintptr_t) &info);
	g_assert_cmpuint(mock.sysinfos[1].format, ==, info.format);
	g_assert_cmpuint(mock.sysinfos[1].rate, ==, info.rate);
	g_assert_cmpuint(mock.arguments[2], ==, 22);
}

static void test_configure_errors(void)
{
	const unsigned long requests[] = {
		VIDEO_STOP, DREAM_VIDEO_SET_SYSINFO, VIDEO_SET_STREAMTYPE, VIDEO_PLAY, VIDEO_FREEZE
	};
	DreamVideoSysinfo info = {.format = 4, .width = 1280, .height = 720, .rate = 3840};

	for (guint fail = 0; fail < G_N_ELEMENTS(requests); ++fail) {
		IoctlStep steps[G_N_ELEMENTS(requests)];
		DreamVideoDevice device;
		MockIoctl mock;
		GError *error = NULL;

		for (guint i = 0; i <= fail; ++i)
			steps[i] = (IoctlStep) {requests[i], i == fail ? -1 : 0, i == fail ? EIO : 0};
		init_device(&device, &mock, steps, fail + 1);
		device.playing = TRUE;
		g_assert_false(dream_video_device_configure(&device, &info, 1, TRUE, &error));
		g_assert_error(error, G_FILE_ERROR, G_FILE_ERROR_IO);
		g_assert_cmpuint(mock.calls, ==, fail + 1);
		/* Failed STOP leaves the old decoder active. Failed FREEZE happens
		 * after successful PLAY; close must still know it needs VIDEO_STOP. */
		g_assert_cmpint(device.playing, ==, fail == 0 || fail == 4);
		g_clear_error(&error);
	}
}

static void test_end_of_stream(void)
{
	const IoctlStep steps[] = {
		{DREAM_VIDEO_SET_FRAME, -1, EAGAIN},
		/* The shared ioctl wrapper retries EINTR up to eight times. */
		{DREAM_VIDEO_SET_FRAME, -1, EINTR},
		{DREAM_VIDEO_SET_FRAME, -1, EINTR},
		{DREAM_VIDEO_SET_FRAME, -1, EINTR},
		{DREAM_VIDEO_SET_FRAME, -1, EINTR},
		{DREAM_VIDEO_SET_FRAME, -1, EINTR},
		{DREAM_VIDEO_SET_FRAME, -1, EINTR},
		{DREAM_VIDEO_SET_FRAME, -1, EINTR},
		{DREAM_VIDEO_SET_FRAME, -1, EINTR},
		{DREAM_VIDEO_SET_FRAME, 0, 0},
		{DREAM_VIDEO_SET_FRAME, -1, EIO},
		{DREAM_VIDEO_SET_FRAME, -1, ENOMEM},
		{DREAM_VIDEO_SET_FRAME, 1, 0}
	};
	const DreamWriteResult results[] = {DREAM_WRITE_AGAIN, DREAM_WRITE_AGAIN,
		DREAM_WRITE_DONE, DREAM_WRITE_ERROR, DREAM_WRITE_ERROR, DREAM_WRITE_ERROR};
	const guint calls[] = {1, 9, 10, 11, 12, 13};
	const gint errors[] = {0, 0, 0, G_FILE_ERROR_IO, G_FILE_ERROR_NOMEM, G_FILE_ERROR_IO};
	const DreamVideoFrame empty = {0};
	DreamVideoDevice device;
	MockIoctl mock;
	GError *error = NULL;

	init_device(&device, &mock, steps, G_N_ELEMENTS(steps));
	for (guint index = 0; index < G_N_ELEMENTS(results); index++) {
		g_assert_cmpint(dream_video_device_end(&device, &error), ==, results[index]);
		g_assert_cmpuint(mock.calls, ==, calls[index]);
		if (results[index] == DREAM_WRITE_ERROR) {
			g_assert_error(error, G_FILE_ERROR, errors[index]);
			g_assert_nonnull(strstr(error->message, "end of stream"));
			g_clear_error(&error);
		} else {
			g_assert_no_error(error);
		}
	}
	g_assert_cmpuint(mock.calls, ==, G_N_ELEMENTS(steps));
	for (guint index = 0; index < mock.calls; index++)
		g_assert_cmpmem(&mock.frames[index], sizeof(empty), &empty, sizeof(empty));
}

typedef struct {
	const gchar *width, *height, *aspect;
	guint calls;
} MockSysfs;

static gssize mock_read_sysfs(DreamVideoDevice *device, const gchar *path,
	gchar *buffer, gsize capacity)
{
	MockSysfs *mock = device->ioctlData;
	const gchar *value;
	mock->calls++;
	if (g_str_equal(path, "/sys/class/video/frame_width"))
		value = mock->width;
	else if (g_str_equal(path, "/sys/class/video/frame_height"))
		value = mock->height;
	else {
		g_assert_cmpstr(path, ==, "/sys/class/video/frame_aspect_ratio");
		value = mock->aspect;
	}
	if (!value)
		return -1;
	gsize length = MIN(strlen(value), capacity);
	memcpy(buffer, value, length);
	return (gssize) length;
}

static void test_size_notifications(void)
{
	DreamVideoDevice device;
	MockSysfs mock = {"1920\n", "1080\n", "0xc0\n", 0};
	gint width = 640, height = 480, aspect = -1;
	dream_video_device_init(&device);
	device.ioctlData = &mock;
	device.readSysfsHook = mock_read_sysfs;
	g_assert_false(dream_video_device_read_size(&device, &width, &height, &aspect));
	g_assert_cmpuint(mock.calls, ==, 0);
	g_assert_cmpint(aspect, ==, -1);
	g_atomic_int_set(&device.sizePending, TRUE);
	g_assert_true(dream_video_device_read_size(&device, &width, &height, &aspect));
	g_assert_cmpint(width, ==, 1920);
	g_assert_cmpint(height, ==, 1080);
	g_assert_cmpint(aspect, ==, 2);
	g_assert_cmpuint(mock.calls, ==, 3);
	g_assert_false(dream_video_device_read_size(&device, &width, &height, &aspect));
	g_assert_cmpuint(mock.calls, ==, 3);
	const gchar *values[] = {"c0", "90", "0", "-1", NULL, "no value", "ffffffff", "c0tail"};
	const gint expected[] = {2, 3, 3, 0, 0, 0, 0, 0};
	for (guint i = 0; i < G_N_ELEMENTS(values); i++) {
		mock.aspect = values[i];
		g_atomic_int_set(&device.sizePending, TRUE);
		g_assert_true(dream_video_device_read_size(&device, &width, &height, &aspect));
		g_assert_cmpint(aspect, ==, expected[i]);
	}
}

static void test_invalid_size(void)
{
	const gchar *values[] = {NULL, "", "0", "-1", "4097", "1x", "1 2", "0x400", "9999999999999999999999999999", "\n"};
	DreamVideoDevice device;
	MockSysfs mock = {"1280", "720", "90", 0};
	dream_video_device_init(&device);
	device.ioctlData = &mock;
	device.readSysfsHook = mock_read_sysfs;
	for (guint field = 0; field < 2; field++) {
		for (guint i = 0; i < G_N_ELEMENTS(values); i++) {
			gint width = 1920, height = 1080, aspect = -1;
			mock.width = field ? "1280" : values[i];
			mock.height = field ? values[i] : "720";
			g_atomic_int_set(&device.sizePending, TRUE);
			g_assert_false(dream_video_device_read_size(&device, &width, &height, &aspect));
			g_assert_cmpint(width, ==, 1920);
			g_assert_cmpint(height, ==, 1080);
			g_assert_false(g_atomic_int_get(&device.sizePending));
		}
	}
	gchar oversized[80];
	memset(oversized, '1', sizeof(oversized) - 1);
	oversized[sizeof(oversized) - 1] = '\0';
	mock.width = oversized;
	mock.height = "720";
	gint width = 1920, height = 1080, aspect = -1;
	g_atomic_int_set(&device.sizePending, TRUE);
	g_assert_false(dream_video_device_read_size(&device, &width, &height, &aspect));
	g_assert_cmpint(width, ==, 1920);
	mock.width = "4096";
	mock.height = "1";
	mock.aspect = NULL;
	g_atomic_int_set(&device.sizePending, TRUE);
	g_assert_true(dream_video_device_read_size(&device, &width, &height, &aspect));
	g_assert_cmpint(width, ==, 4096);
	g_assert_cmpint(height, ==, 1);
	g_assert_cmpint(aspect, ==, 0);
}

static void test_size_poll(void)
{
	DreamVideoDevice device;
	int descriptors[2];
	const gchar byte = 'x';
	dream_video_device_init(&device);
	g_assert_cmpint(pipe(descriptors), ==, 0);
	device.pollFd = descriptors[0];
	g_assert_cmpint(write(descriptors[1], &byte, 1), ==, 1);
	g_assert_cmpint(dream_video_device_wait(&device, FALSE, 0), ==, 0);
	g_assert_true(g_atomic_int_get(&device.sizePending));
	g_atomic_int_set(&device.sizePending, FALSE);
	/* amvideo readiness must not be mistaken for decoder drain completion. */
	g_assert_cmpint(dream_video_device_wait(&device, TRUE, 0), ==, 0);
	g_assert_false(g_atomic_int_get(&device.sizePending));
	close(descriptors[0]);
	close(descriptors[1]);
}

typedef struct {
	short videoEvents, wakeEvents, sizeEvents;
	gboolean eos;
	int result, errorNumber;
} MockPoll;

static int mock_poll(DreamVideoDevice *device, struct pollfd *fds, nfds_t count, int timeoutMs)
{
	MockPoll *mock = device->ioctlData;
	g_assert_cmpuint(count, ==, 4);
	g_assert_cmpint(timeoutMs, ==, 20);
	g_assert_cmpint(fds[1].fd, ==, 42);
	g_assert_cmpint(fds[1].events, ==, mock->eos ? POLLIN : POLLOUT | POLLPRI);
	fds[0].revents = mock->wakeEvents;
	fds[1].revents = mock->videoEvents;
	fds[2].revents = mock->sizeEvents;
	errno = mock->errorNumber;
	return mock->result;
}

static void test_poll_readiness(void)
{
	const struct {
		short videoEvents, wakeEvents, sizeEvents;
		gboolean eos;
		int expected;
	} cases[] = {
		{POLLOUT, 0, 0, FALSE, 1},
		{POLLPRI, 0, 0, FALSE, 0},
		{POLLOUT | POLLPRI, 0, 0, FALSE, 1},
		{POLLIN, 0, 0, FALSE, 0},
		{POLLIN, 0, 0, TRUE, 1},
		{POLLOUT, 0, 0, TRUE, 0},
		{0, 0, POLLIN, FALSE, 0},
		{POLLOUT, POLLIN, 0, FALSE, 0},
		{POLLIN, POLLIN, 0, TRUE, 0},
		{POLLOUT | POLLERR, 0, 0, FALSE, -1},
		{0, 0, 0, FALSE, 0}
	};
	DreamVideoDevice device;
	MockPoll mock = {0};
	int wake[2];
	dream_video_device_init(&device);
	g_assert_cmpint(socketpair(AF_UNIX, SOCK_STREAM | SOCK_NONBLOCK, 0, wake), ==, 0);
	device.videoFd = 42; /* Only mock_poll sees this descriptor. */
	device.wakeFd[0] = wake[0];
	device.wakeFd[1] = wake[1];
	device.ioctlData = &mock;
	device.pollHook = mock_poll;
	for (guint index = 0; index < G_N_ELEMENTS(cases); index++) {
		mock = (MockPoll) {cases[index].videoEvents, cases[index].wakeEvents,
			cases[index].sizeEvents, cases[index].eos, 1, 0};
		g_atomic_int_set(&device.sizePending, FALSE);
		if (mock.wakeEvents)
			dream_video_device_wake(&device);
		g_assert_cmpint(dream_video_device_wait(&device, mock.eos, 20), ==, cases[index].expected);
		if (cases[index].expected < 0)
			g_assert_cmpint(errno, ==, EIO);
		g_assert_cmpint(g_atomic_int_get(&device.sizePending), ==, !mock.eos && mock.sizeEvents != 0);
	}
	mock = (MockPoll) {0, 0, 0, FALSE, -1, EINTR};
	g_assert_cmpint(dream_video_device_wait(&device, FALSE, 20), ==, 0);
	mock.errorNumber = EIO;
	g_assert_cmpint(dream_video_device_wait(&device, FALSE, 20), ==, -1);
	close(wake[0]);
	close(wake[1]);
}

static int mock_auxiliary_poll(DreamVideoDevice *device, struct pollfd *fds, nfds_t count, int timeoutMs)
{
	gboolean eos = GPOINTER_TO_INT(device->ioctlData);
	g_assert_cmpuint(count, ==, 4);
	g_assert_cmpint(timeoutMs, ==, 250);
	g_assert_cmpint(fds[0].fd, ==, device->wakeFd[0]);
	g_assert_cmpint(fds[1].fd, ==, device->videoFd);
	g_assert_cmpint(fds[1].events, ==, eos ? POLLIN : POLLOUT | POLLPRI);
	g_assert_cmpint(fds[2].fd, ==, eos ? -1 : device->pollFd);
	g_assert_cmpint(fds[3].fd, ==, eos ? -1 : udev_monitor_get_fd(device->monitor));
	return 0;
}

static void test_eos_excludes_auxiliary_fds(void)
{
	DreamVideoDevice device;
	dream_video_device_init(&device);
	struct udev *udev = udev_new();
	struct udev_monitor *monitor = udev ? udev_monitor_new_from_netlink(udev, "udev") : NULL;
	if (!monitor) {
		/* No receiver or udev daemon is required, only a local netlink
		 * socket. Sandboxed test environments may prohibit creating one. */
		g_test_skip("Local udev monitor socket unavailable");
		if (udev)
			udev_unref(udev);
		return;
	}
	g_assert_cmpint(udev_monitor_get_fd(monitor), >=, 0);
	device.videoFd = 42;
	device.pollFd = 44;
	device.monitor = monitor;
	device.pollHook = mock_auxiliary_poll;
	/* Only the mock sees these descriptors. Normal operation retains both
	 * auxiliary sources; EOS excludes them rather than repeatedly waking
	 * on an unread event that the drain phase intentionally does not read. */
	device.ioctlData = GINT_TO_POINTER(FALSE);
	g_assert_cmpint(dream_video_device_wait(&device, FALSE, 250), ==, 0);
	device.ioctlData = GINT_TO_POINTER(TRUE);
	g_assert_cmpint(dream_video_device_wait(&device, TRUE, 250), ==, 0);
	udev_monitor_unref(monitor);
	udev_unref(udev);
}

int main(int argc, char **argv)
{
	gst_init(&argc, &argv);
	g_test_init(&argc, &argv, NULL);
	g_test_add_func("/device/abi", test_abi);
	g_test_add_func("/device/initial-state", test_initial_state);
	g_test_add_func("/device/partial-writes", test_partial_writes);
	g_test_add_func("/device/retry-and-zero-progress", test_retry_and_zero_progress);
	g_test_add_func("/device/bounded-interruptions", test_bounded_interruptions);
	g_test_add_func("/device/driver-overflow", test_driver_overflow);
	g_test_add_func("/device/permanent-errors", test_permanent_errors);
	g_test_add_func("/device/invalid-bounds", test_invalid_bounds);
	g_test_add_func("/device/timestamp-scaling", test_timestamp_scaling);
	g_test_add_func("/device/configure-order", test_configure_order);
	g_test_add_func("/device/configure-errors", test_configure_errors);
	g_test_add_func("/device/end-of-stream", test_end_of_stream);
	g_test_add_func("/device/size-notifications", test_size_notifications);
	g_test_add_func("/device/invalid-size", test_invalid_size);
	g_test_add_func("/device/size-poll", test_size_poll);
	g_test_add_func("/device/poll-readiness", test_poll_readiness);
	g_test_add_func("/device/eos-excludes-auxiliary-fds", test_eos_excludes_auxiliary_fds);
	return g_test_run();
}
