/* SPDX-License-Identifier: LGPL-2.1-or-later */
#ifndef DREAM_VIDEO_DEVICE_H
#define DREAM_VIDEO_DEVICE_H

#include <glib.h>
#include <linux/dvb/video.h>
#include <poll.h>
#include <stdint.h>
#include <sys/ioctl.h>
#include <sys/types.h>

/* Dream AArch64 ABI, also used by Enigma2's DREAM_VIDEO_SET_FRAME.
 * These are not the similarly named AMSTREAM ioctls. */
typedef struct {
	uint64_t pts;
	ssize_t bytes[8];
	const uint8_t *data[8];
	int is_phys_addr[8];
} DreamVideoFrame;

typedef struct {
	uint32_t format, width, height, rate, extra, status, ratio;
	void *param;
	uint64_t ratio64;
} DreamVideoSysinfo;

#define DREAM_VIDEO_SET_FRAME _IOWR('o', 64, DreamVideoFrame)
#define DREAM_VIDEO_SET_SYSINFO _IOWR('o', 65, DreamVideoSysinfo)
#define DREAM_VIDEO_PTS_NONE UINT64_MAX
#define DREAM_VIDEO_PTS_MASK UINT64_C(0x1ffffffff)
#define DREAM_VIDEO_EVENT_PROGRESSIVE 0x10
#define DREAM_VIDEO_EVENT_PTS_VALID 0x20
#define DREAM_VIDEO_EVENT_DISCONTINUITY 0x40
#ifndef VIDEO_GET_FRAME_RATE
#define VIDEO_GET_FRAME_RATE _IOR('o', 56, unsigned int)
#endif

G_STATIC_ASSERT(sizeof(void *) == 8);
G_STATIC_ASSERT(sizeof(DreamVideoFrame) == 168);
G_STATIC_ASSERT(G_STRUCT_OFFSET(DreamVideoFrame, data) == 72);
G_STATIC_ASSERT(G_STRUCT_OFFSET(DreamVideoFrame, is_phys_addr) == 136);
G_STATIC_ASSERT(sizeof(DreamVideoSysinfo) == 48);
G_STATIC_ASSERT(G_STRUCT_OFFSET(DreamVideoSysinfo, param) == 32);
G_STATIC_ASSERT(DREAM_VIDEO_SET_FRAME == 0xc0a86f40UL);
G_STATIC_ASSERT(DREAM_VIDEO_SET_SYSINFO == 0xc0306f41UL);

typedef int (*DreamIoctl)(int fd, unsigned long request, uintptr_t argument,
	gpointer userData);

typedef struct DreamVideoDevice DreamVideoDevice;
struct DreamVideoDevice {
	int videoFd, pollFd, wakeFd[2];
	struct udev *udev;
	struct udev_monitor *monitor;
	gboolean playing;
	gint sizePending; /* Atomic: poll completion can race reconfiguration. */
	DreamIoctl ioctlCall;
	gpointer ioctlData;
	/* Internal dependency injection for hardware-free tests, never properties. */
	gboolean (*openHook)(DreamVideoDevice *, GError **);
	void (*closeHook)(DreamVideoDevice *);
	int (*waitHook)(DreamVideoDevice *, gboolean, int);
	int (*pollHook)(DreamVideoDevice *, struct pollfd *, nfds_t, int);
	gssize (*readSysfsHook)(DreamVideoDevice *, const gchar *, gchar *, gsize);
};

typedef enum {
	DREAM_WRITE_DONE,
	DREAM_WRITE_AGAIN,
	DREAM_WRITE_ERROR
} DreamWriteResult;

void dream_video_device_init(DreamVideoDevice *device);
gboolean dream_video_device_open(DreamVideoDevice *device, GError **error);
void dream_video_device_close(DreamVideoDevice *device);
int dream_video_device_ioctl(DreamVideoDevice *device, unsigned long request,
	uintptr_t argument);
gboolean dream_video_device_configure(DreamVideoDevice *device,
	DreamVideoSysinfo *info, guint streamType, gboolean paused, GError **error);
/* One bounded nonblocking attempt; offset is retained across pause/resume.
 * PTS is retained until bytes are accepted. The caller owns data until DONE. */
DreamWriteResult dream_video_device_submit(DreamVideoDevice *device,
	const guint8 *data, gsize size, gsize *offset, guint64 pts, GError **error);
/* One marker attempt. Only a zero result confirms acceptance. */
DreamWriteResult dream_video_device_end(DreamVideoDevice *device, GError **error);
/* Return 1 only for video POLLOUT (or EOS POLLIN), 0 for notifications,
 * wakeups/timeouts, and -1 for errors. A wakeup always takes precedence. */
int dream_video_device_wait(DreamVideoDevice *device, gboolean eos, int timeoutMs);
/* Consume a pending amvideo size notification. Invalid/missing dimensions
 * leave the caller's previous pair unchanged; unknown aspect is reported as 0.
 * Caller serializes this with decoder reconfiguration. */
gboolean dream_video_device_read_size(DreamVideoDevice *device,
	gint *width, gint *height, gint *aspect);
void dream_video_device_wake(DreamVideoDevice *device);
void dream_video_device_drain_wake(DreamVideoDevice *device);
guint64 dream_video_pts_from_ns(guint64 ns);
guint64 dream_video_pts_to_ns(guint64 pts);

#endif
