/* SPDX-License-Identifier: LGPL-2.1-or-later */
#include "dream_video_device.h"

#include <errno.h>
#include <fcntl.h>
#include <gst/gst.h>
#include <libudev.h>
#include <poll.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>

static int system_ioctl(int fd, unsigned long request, uintptr_t argument,
	gpointer userData)
{
	(void) userData;
	return ioctl(fd, request, argument);
}

static void device_error(GError **error, const char *operation)
{
	int savedErrno = errno;
	g_set_error(error, G_FILE_ERROR, g_file_error_from_errno(savedErrno),
		"%s: %s", operation, g_strerror(savedErrno));
}

void dream_video_device_init(DreamVideoDevice *device)
{
	memset(device, 0, sizeof(*device));
	device->videoFd = device->pollFd = -1;
	device->wakeFd[0] = device->wakeFd[1] = -1;
	device->ioctlCall = system_ioctl;
}

int dream_video_device_ioctl(DreamVideoDevice *device, unsigned long request,
	uintptr_t argument)
{
	int result;
	/* Cap interruptions too: a signal storm must not prevent shutdown. */
	guint attempts = 0;
	do {
		result = device->ioctlCall(device->videoFd, request, argument, device->ioctlData);
	} while (result < 0 && errno == EINTR && ++attempts < 8);
	return result;
}

gboolean dream_video_device_open(DreamVideoDevice *device, GError **error)
{
	if (device->openHook)
		return device->openHook(device, error);
	device->videoFd = open("/dev/dvb/adapter0/video0", O_RDWR | O_NONBLOCK | O_CLOEXEC);
	if (device->videoFd < 0) {
		device_error(error, "open /dev/dvb/adapter0/video0");
		return FALSE;
	}
	device->pollFd = open("/dev/amvideo_poll", O_RDWR | O_NONBLOCK | O_CLOEXEC);
	if (device->pollFd < 0) {
		device_error(error, "open /dev/amvideo_poll");
		goto fail;
	}
	if (socketpair(AF_UNIX, SOCK_STREAM | SOCK_NONBLOCK | SOCK_CLOEXEC, 0, device->wakeFd) < 0) {
		device_error(error, "create decoder wake channel");
		goto fail;
	}
	device->udev = udev_new();
	if (device->udev)
		device->monitor = udev_monitor_new_from_netlink(device->udev, "udev");
	if (device->monitor) {
		/* Filter later by event properties. Dream firmware differs in subsystem. */
		if (udev_monitor_enable_receiving(device->monitor) < 0) {
			udev_monitor_unref(device->monitor);
			device->monitor = NULL;
		}
	}
	if (dream_video_device_ioctl(device, VIDEO_SELECT_SOURCE, VIDEO_SOURCE_MEMORY) < 0) {
		device_error(error, "VIDEO_SELECT_SOURCE memory");
		goto fail;
	}
	return TRUE;
fail:
	dream_video_device_close(device);
	return FALSE;
}

void dream_video_device_close(DreamVideoDevice *device)
{
	if (device->closeHook) {
		device->closeHook(device);
		return;
	}
	if (device->videoFd >= 0) {
		if (device->playing)
			dream_video_device_ioctl(device, VIDEO_STOP, 0);
		dream_video_device_ioctl(device, VIDEO_SLOWMOTION, 0);
		dream_video_device_ioctl(device, VIDEO_FAST_FORWARD, 0);
		dream_video_device_ioctl(device, VIDEO_SELECT_SOURCE, VIDEO_SOURCE_DEMUX);
		close(device->videoFd);
	}
	if (device->pollFd >= 0)
		close(device->pollFd);
	for (guint i = 0; i < 2; ++i) {
		if (device->wakeFd[i] >= 0)
			close(device->wakeFd[i]);
		device->wakeFd[i] = -1;
	}
	/* The monitor owns its FD; do not close it a second time. */
	if (device->monitor)
		udev_monitor_unref(device->monitor);
	if (device->udev)
		udev_unref(device->udev);
	device->monitor = NULL;
	device->udev = NULL;
	device->videoFd = device->pollFd = -1;
	device->playing = FALSE;
	g_atomic_int_set(&device->sizePending, FALSE);
}

gboolean dream_video_device_configure(DreamVideoDevice *device,
	DreamVideoSysinfo *info, guint streamType, gboolean paused, GError **error)
{
	if (device->playing) {
		if (dream_video_device_ioctl(device, VIDEO_STOP, 0) < 0) {
			device_error(error, "VIDEO_STOP for caps change");
			return FALSE;
		}
		device->playing = FALSE;
	}
	if (dream_video_device_ioctl(device, DREAM_VIDEO_SET_SYSINFO, (uintptr_t) info) < 0) {
		device_error(error, "VIDEO_SET_DEC_SYSINFO");
		return FALSE;
	}
	if (dream_video_device_ioctl(device, VIDEO_SET_STREAMTYPE, streamType) < 0) {
		device_error(error, "VIDEO_SET_STREAMTYPE");
		return FALSE;
	}
	if (dream_video_device_ioctl(device, VIDEO_PLAY, 0) < 0) {
		device_error(error, "VIDEO_PLAY");
		return FALSE;
	}
	device->playing = TRUE;
	if (paused && dream_video_device_ioctl(device, VIDEO_FREEZE, 0) < 0) {
		device_error(error, "VIDEO_FREEZE");
		return FALSE;
	}
	return TRUE;
}

DreamWriteResult dream_video_device_submit(DreamVideoDevice *device,
	const guint8 *data, gsize size, gsize *offset, guint64 pts, GError **error)
{
	DreamVideoFrame frame = {0};
	int accepted;
	if (!offset || *offset > size || (!data && size) || size > G_MAXSSIZE) {
		g_set_error_literal(error, G_FILE_ERROR, G_FILE_ERROR_INVAL, "Invalid decoder frame bounds");
		return DREAM_WRITE_ERROR;
	}
	if (*offset == size)
		return DREAM_WRITE_DONE;
	frame.pts = *offset ? DREAM_VIDEO_PTS_NONE : pts;
	frame.bytes[0] = (ssize_t) (size - *offset);
	frame.data[0] = data + *offset;
	accepted = dream_video_device_ioctl(device, DREAM_VIDEO_SET_FRAME, (uintptr_t) &frame);
	if (accepted < 0) {
		if (errno == EAGAIN || errno == EINTR)
			return DREAM_WRITE_AGAIN;
		device_error(error, "VIDEO_SET_FRAME");
		return DREAM_WRITE_ERROR;
	}
	if ((gsize) accepted > size - *offset) {
		g_set_error_literal(error, G_FILE_ERROR, G_FILE_ERROR_FAILED,
			"Decoder accepted more bytes than submitted");
		return DREAM_WRITE_ERROR;
	}
	*offset += (gsize) accepted;
	return *offset == size ? DREAM_WRITE_DONE : DREAM_WRITE_AGAIN;
}

DreamWriteResult dream_video_device_end(DreamVideoDevice *device, GError **error)
{
	DreamVideoFrame frame = {0};
	int result = dream_video_device_ioctl(device, DREAM_VIDEO_SET_FRAME, (uintptr_t) &frame);
	if (result < 0) {
		if (errno == EAGAIN || errno == EINTR)
			return DREAM_WRITE_AGAIN;
		device_error(error, "VIDEO_SET_FRAME end of stream");
		return DREAM_WRITE_ERROR;
	}
	if (result != 0) {
		g_set_error_literal(error, G_FILE_ERROR, G_FILE_ERROR_IO,
			"VIDEO_SET_FRAME end of stream returned an unexpected result");
		return DREAM_WRITE_ERROR;
	}
	return DREAM_WRITE_DONE;
}

int dream_video_device_wait(DreamVideoDevice *device, gboolean eos, int timeoutMs)
{
	if (device->waitHook)
		return device->waitHook(device, eos, timeoutMs);
	struct pollfd fds[4] = {
		{device->wakeFd[0], POLLIN, 0},
		{device->videoFd, eos ? POLLIN : POLLOUT | POLLPRI, 0},
		{eos ? -1 : device->pollFd, POLLIN, 0},
		{!eos && device->monitor ? udev_monitor_get_fd(device->monitor) : -1, POLLIN, 0}
	};
	int result = device->pollHook ? device->pollHook(device, fds, G_N_ELEMENTS(fds), timeoutMs) :
		poll(fds, G_N_ELEMENTS(fds), timeoutMs);
	if (result < 0)
		return errno == EINTR ? 0 : -1;
	if (fds[0].revents & POLLIN)
		dream_video_device_drain_wake(device);
	for (guint i = 0; i < G_N_ELEMENTS(fds); ++i) {
		if (fds[i].revents & (POLLERR | POLLHUP | POLLNVAL)) {
			errno = EIO;
			return -1;
		}
	}
	if (!eos && (fds[2].revents & POLLIN))
		g_atomic_int_set(&device->sizePending, TRUE);
	/* amvideo_poll and POLLPRI notify events, not writable capacity. Some
	 * drivers accept SET_FRAME even without POLLOUT, overfilling their
	 * preferred buffer level; the caller must honor this readiness gate. */
	return (fds[1].revents & (eos ? POLLIN : POLLOUT)) && !(fds[0].revents & POLLIN) ? 1 : 0;
}

static gssize read_sysfs(DreamVideoDevice *device, const gchar *path,
	gchar *buffer, gsize capacity)
{
	if (device->readSysfsHook)
		return device->readSysfsHook(device, path, buffer, capacity);
	int fd = open(path, O_RDONLY | O_CLOEXEC);
	if (fd < 0)
		return -1;
	gssize length;
	guint attempts = 0;
	do {
		length = read(fd, buffer, capacity);
	} while (length < 0 && errno == EINTR && ++attempts < 8);
	close(fd);
	return length;
}

static gboolean read_sysfs_integer(DreamVideoDevice *device, const gchar *path,
	guint base, gint64 *value)
{
	gchar text[64], *end;
	gssize length = read_sysfs(device, path, text, sizeof(text) - 1);
	/* A full buffer may contain an incomplete numeric value. Do not guess. */
	if (length <= 0 || length >= (gssize) sizeof(text) - 1 || memchr(text, '\0', length))
		return FALSE;
	text[length] = '\0';
	errno = 0;
	gint64 parsed = g_ascii_strtoll(text, &end, base);
	if (errno == ERANGE || end == text)
		return FALSE;
	while (g_ascii_isspace(*end))
		end++;
	if (*end)
		return FALSE;
	*value = parsed;
	return TRUE;
}

gboolean dream_video_device_read_size(DreamVideoDevice *device,
	gint *width, gint *height, gint *aspect)
{
	gint64 newWidth, newHeight, newAspect;
	if (!width || !height || !aspect ||
		!g_atomic_int_compare_and_exchange(&device->sizePending, TRUE, FALSE))
		return FALSE;
	*aspect = 0;
	if (read_sysfs_integer(device, "/sys/class/video/frame_aspect_ratio", 16, &newAspect) &&
		newAspect >= 0 && newAspect <= G_MAXINT)
		*aspect = newAspect == 0xc0 ? 2 : 3;
	if (!read_sysfs_integer(device, "/sys/class/video/frame_width", 10, &newWidth) ||
		!read_sysfs_integer(device, "/sys/class/video/frame_height", 10, &newHeight) ||
		newWidth < 1 || newWidth > 4096 || newHeight < 1 || newHeight > 4096)
		return FALSE;
	*width = (gint) newWidth;
	*height = (gint) newHeight;
	return TRUE;
}

void dream_video_device_wake(DreamVideoDevice *device)
{
	const guint8 command = 1;
	if (device->wakeFd[1] >= 0)
		(void) send(device->wakeFd[1], &command, 1, MSG_NOSIGNAL | MSG_DONTWAIT);
}

void dream_video_device_drain_wake(DreamVideoDevice *device)
{
	guint8 bytes[64];
	while (recv(device->wakeFd[0], bytes, sizeof(bytes), MSG_DONTWAIT) > 0)
		;
}

guint64 dream_video_pts_from_ns(guint64 ns)
{
	return ns == GST_CLOCK_TIME_NONE ? DREAM_VIDEO_PTS_NONE :
		gst_util_uint64_scale(ns, 9, 100000) & DREAM_VIDEO_PTS_MASK;
}

guint64 dream_video_pts_to_ns(guint64 pts)
{
	return pts == DREAM_VIDEO_PTS_NONE ? GST_CLOCK_TIME_NONE :
		gst_util_uint64_scale(pts & DREAM_VIDEO_PTS_MASK, 100000, 9);
}
