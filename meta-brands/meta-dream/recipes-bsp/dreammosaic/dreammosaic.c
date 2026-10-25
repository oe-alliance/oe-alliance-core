// SPDX-License-Identifier: GPL-2.0
/*
 * Mosaic decoders for the Dreambox One/Two.
 *
 * The driver modules of the box offer two video decoders (video0, video1).
 * This module adds four more as video2..video5 for a mosaic of up to four
 * services. The player writes the video elementary stream of each service
 * frame by frame (VIDEO_SET_FRAME, as for the PiP decoder); every device
 * runs a multi instance decoder of the media modules. In mosaic mode
 * (/proc/stb/video/decodermode) the frames of the four decoders are scaled
 * with ge2d into the windows set in /proc/stb/vmpeg/N/dst_* and the result
 * goes to the main video layer.
 *
 * Timing, as a receiver plays a single service: every window runs the
 * system time clock of its service, taken from the PCR the player hands
 * with each frame. Its input goes to the decoder shortly before it is due
 * and a picture is shown when the clock reaches its PTS. The window playing
 * the audio runs the system clock the audio output is paced by. A new
 * window of the audio only moves the system clock; the pictures of all
 * windows go on as before.
 */

#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/fs.h>
#include <linux/miscdevice.h>
#include <linux/mm.h>
#include <linux/mutex.h>
#include <linux/spinlock.h>
#include <linux/kthread.h>
#include <linux/wait.h>
#include <linux/delay.h>
#include <linux/uaccess.h>
#include <linux/proc_fs.h>
#include <linux/seq_file.h>
#include <linux/poll.h>
#include <linux/dvb/video.h>
#include <linux/amlogic/media/utils/amstream.h>
#include <linux/amlogic/media/utils/vformat.h>
#include <linux/amlogic/media/vfm/vframe.h>
#include <linux/amlogic/media/vfm/vframe_provider.h>
#include <linux/amlogic/media/vfm/vframe_receiver.h>
#include <linux/amlogic/media/vfm/vfm_ext.h>
#include <linux/amlogic/media/ge2d/ge2d.h>
#include <linux/amlogic/media/ge2d/ge2d_func.h>
#include <linux/amlogic/media/canvas/canvas.h>
#include <linux/amlogic/media/canvas/canvas_mgr.h>
#include <linux/amlogic/media/codec_mm/codec_mm.h>
#include <linux/amlogic/media/vout/vout_notify.h>
#include <linux/amlogic/media/video_sink/video.h>
#include <linux/hrtimer.h>
#include <linux/list.h>
#include <linux/slab.h>
#include <linux/vmalloc.h>
#include <linux/amlogic/media/frame_sync/timestamp.h>

#include "streambuf.h"

/*
 * Decoder of the media modules in the driver release. Its struct vdec_s
 * differs from the published sources of that version, so it stays opaque:
 * the module uses the exported functions and the fields below at the
 * offsets the driver release uses (vdec_create and vdec_init).
 */
struct vdec_s;

enum vdec_type_e {
	VDEC_1 = 0,
	VDEC_HCODEC,
	VDEC_2,
	VDEC_HEVC,
	VDEC_HEVCB,
	VDEC_MAX
};

#define VDEC_SYS_INFO		536
#define VDEC_VF_RECEIVER_NAME	0x10cf8
/* the configuration text the decoders read at their start, and its size */
#define VDEC_CONFIG		0x10dc1
#define VDEC_CONFIG_LEN		0x11dc4
#define VDEC_CONFIG_SIZE	4096

extern struct vdec_s *vdec_create(struct stream_port_s *port,
				  struct vdec_s *master);
extern int vdec_set_format(struct vdec_s *vdec, int format);
extern int vdec_set_pts(struct vdec_s *vdec, u32 pts);
extern int vdec_set_video_path(struct vdec_s *vdec, int video_path);
extern s32 vdec_init(struct vdec_s *vdec, int is_4k);
extern int vdec_connect(struct vdec_s *vdec);
extern void vdec_release(struct vdec_s *vdec);
extern int vdec_write_vframe(struct vdec_s *vdec, const char *buf,
			     size_t count);
extern void vdec_poweron(enum vdec_type_e core);
extern int amports_switch_gate(const char *name, int enable);

static struct dec_sysinfo *vdec_sys_info(struct vdec_s *vdec)
{
	return *(struct dec_sysinfo **)((u8 *)vdec + VDEC_SYS_INFO);
}

static void vdec_set_receiver_name(struct vdec_s *vdec, char *name)
{
	*(char **)((u8 *)vdec + VDEC_VF_RECEIVER_NAME) = name;
}

static void vdec_set_config(struct vdec_s *vdec, const char *config)
{
	*(int *)((u8 *)vdec + VDEC_CONFIG_LEN) =
		strscpy((char *)vdec + VDEC_CONFIG, config, VDEC_CONFIG_SIZE);
}

#define DECODERS	4
#define FIRST_DEVICE	2
#define OUT_BUFFERS	4
#define OUT_WIDTH	1920
#define OUT_HEIGHT	1080
/* window coordinates of the player */
#define DST_WIDTH	720
#define DST_HEIGHT	576

#define PROVIDER_NAME	"dmosaic"
#define MAP_NAME	"dmosaic-map"

/* frame input of the Dreambox video decoders */
struct video_frame {
	s64 pts;
	s64 size[8];
	const u8 __user *data[8];
	/* [0]: the PCR (90 kHz) of the service when the frame arrived, with
	 * VIDEO_FRAME_PCR set */
	s64 reserved[4];
};

#define VIDEO_FRAME_PCR	(1ULL << 63)

#define VIDEO_SET_FRAME _IOWR('o', 64, struct video_frame)

#ifndef VIDEO_GET_FRAME_RATE
#define VIDEO_GET_FRAME_RATE _IOR('o', 56, unsigned int)
#endif

/* stream types of the player */
#define STREAMTYPE_MPEG2	0
#define STREAMTYPE_H264		1
#define STREAMTYPE_MPEG1	6
#define STREAMTYPE_H265		7
#define STREAMTYPE_H265_ALT	22

/* ---- timing, 90 kHz ---- */

/* the input goes to the decoder at least this long before it is due: the
 * decoder order (a reference picture before the pictures shown earlier) and
 * the decoding of four services at a time. A window whose pictures still
 * come late gets its input earlier, up to LEAD_MAX. */
static unsigned int feed_lead = 27000;
module_param(feed_lead, uint, 0644);
MODULE_PARM_DESC(feed_lead, "least input to the decoder ahead of its PTS, 90 kHz");
#define LEAD_MAX	54000
/* a window without a picture ready at LEAD_MAX, as the decoder takes its
 * turns with the others, shows them later, up to DELAY_MAX: no more than its
 * cache holds. Not the window of the audio: the system clock would jump. */
#define DELAY_MAX	9000
/* evaluations a window keeps a raised lead before the next step */
#define LEAD_HOLD	3

/* a picture is shown this long after its PTS on the clock of its service,
 * the audio as well: the audio output keeps this much more ahead, enough
 * for the stall while further decoders start */
static unsigned int show_delay = 9000;
module_param(show_delay, uint, 0644);
MODULE_PARM_DESC(show_delay, "pictures shown after their PTS, 90 kHz");

/* the audio output runs this long behind the clock of its window: the
 * picture is composed at the tick it is due and shown one output period
 * after its buffer goes out */
static unsigned int scr_latency = 2700;
module_param(scr_latency, uint, 0644);
MODULE_PARM_DESC(scr_latency, "system clock behind the clock of the window of the audio, 90 kHz");

/* a PCR this far off the running clock is a new time base: a stream relay
 * delivers in bursts of a second */
#define PCR_JUMP	(90000 * 3)
/* the clock follows the PCR that arrived latest in each PCR_WINDOW: all
 * input is there before its time. From PCR_SETTLE on it goes back at once,
 * ahead when two windows agree; below by PCR_STEP_MAX: the audio output
 * wants a calm clock. */
#define PCR_WINDOW	(90000 * 2)
#define PCR_STEP_MAX	180
#define PCR_SETTLE	(90000 / 5)
/* without a PCR the clock of a service starts this far before the PTS of
 * its first picture, and follows the input when it is off by INPUT_JUMP */
#define INPUT_JUMP	(90000 / 2)
#define NO_PCR_LEAD	(90000 * 3 / 5)
/* a picture later than three quarters of an output period is left out when
 * the next is due as well: the window keeps to its time instead of staying a
 * picture behind; a picture due at the edge of a period stays */
static unsigned int skip_late = 1350;
module_param(skip_late, uint, 0644);
MODULE_PARM_DESC(skip_late, "pictures later than this are left out for the next, 90 kHz");
/* a picture later than this counts as late */
#define LATE		1800

static unsigned int debug;
module_param(debug, uint, 0644);

/* debug: PTS of the audio output (sound/soc/amlogic/auge/spdif.c) */
extern int aml_spdif_get_pts(u64 *pts);
MODULE_PARM_DESC(debug, "print decoder and compositor events");

struct mosaic_window {
	int left, top, width, height;
};

/*
 * A picture waits for its time in the window cache, not in the decoder: the
 * decoder gets its buffer back at once and decodes on, instead of waiting
 * for the window to show the picture it holds.
 */
#define RING_SLOTS	8
/* pictures in the cache: the two fields of a picture share a slot */
#define FIFO_MAX	(RING_SLOTS * 2)
#define SLOT_WIDTH	(OUT_WIDTH / 2)
#define SLOT_HEIGHT	(OUT_HEIGHT / 2)

struct mosaic_slot {
	unsigned long phys;
};

/* a picture in the cache: its slot (the two fields of an interlaced picture
 * share one) and its PTS */
struct mosaic_entry {
	int slot;
	u32 pts;
};

/* input of a window, waiting for its time to go to the decoder */
struct chunk {
	struct list_head list;
	s64 pts;
	size_t size, done;
	u8 data[];
};

#define QUEUE_MAX_BYTES	(12 << 20)

struct mosaic_dec {
	int index;
	char name[16];
	char nodename[32];
	char recv_name[16];
	struct miscdevice misc;
	/* the decoder and the input queue */
	struct mutex lock;
	struct stream_port_s port;
	struct vdec_s *vdec;
	int vformat;
	bool playing;
	bool frozen;

	/* clock of the service: stc_base at stc_time (now_90k); stc_valid
	 * once set, from the PCR or (stc_pcr false) from the input */
	spinlock_t clock_lock;
	bool stc_valid, stc_pcr;
	u32 stc_base;
	s64 stc_time;
	/* the latest PCR against the clock since pcr_window began */
	s32 pcr_late, pcr_late_prev;
	s64 pcr_window;
	/* plays the audio: its clock runs the system clock */
	bool clock;

	struct list_head queue;
	size_t queue_bytes;
	/* PTS of the input queued last, for chunks without one */
	s64 last_in_pts;

	/* frames of the decoder */
	struct vframe_receiver_s recv;
	spinlock_t vf_lock;
	/* the window cache: decoded pictures scaled to the window, in the
	 * order of their PTS, and the one on screen (-1 none) */
	struct mosaic_slot slot[RING_SLOTS];
	struct mosaic_entry fifo[FIFO_MAX];
	int fifo_head, fifo_count;
	int show;
	/* the player stopped the window: the compositor empties it */
	bool clear;
	/* the buffer of the decoder scaled last and its slot: the two fields
	 * of an interlaced picture share one buffer, one scaling serves both */
	unsigned long last_buf;
	int last_slot;
	bool last_reusable;
	u32 reused;
	bool last_valid;
	u32 last_pts, last_dur;
	u32 pts;
	u32 width, height, duration;

	/* the input reached an entry point after the start of the decoder */
	bool synced;
	/* input ahead of its PTS for this window, 0 for feed_lead; since the
	 * last evaluation: the latest picture, the fewest pictures ready */
	s32 lead;
	unsigned int lead_hold;
	s32 win_worst, win_early;
	unsigned int win_ready;
	/* shown this long after the PTS, besides show_delay */
	s32 delay;
	/* pictures left out for the decoder load */
	bool drop_pes;
	unsigned int drop_acc;
	/* HEVC: the highest temporal layer, pictures looked at for it, those
	 * no other refers to */
	u8 hevc_max_tid;
	unsigned int hevc_seen, hevc_unref;
	/* HEVC without such pictures: keyframes only under full load, the
	 * way back from the next keyframe on, without its leading pictures */
	bool key_only, key_resync, skip_rasl;

	/* debug, since the last report */
	u32 frames, shown, late, skipped, dropped, clock_jumps, fed_late;
	/* debug: the earliest a waiting picture was ready, its PTS step */
	s32 early_max;
	u32 pts_zero;
	/* debug: the clock moved ahead by a new PCR, the most and how often;
	 * the PTS step between cached pictures */
	s32 fwd_max, back_max, step_min, step_max;
	u32 fwd_n, back_n;
	u32 cache_pts;
	/* debug: picture against the audio output when shown, window of the
	 * audio */
	s32 av_min, av_max;
	s32 lead_min, lead_max, ahead_min, ahead_max;
	s64 rep_time;

	struct mosaic_window dst, dst_pending;
	struct proc_dir_entry *proc_dir;
	bool registered;
};

struct mosaic_out {
	struct vframe_s vf;
	unsigned long phys;
};

static struct mosaic_dec decs[DECODERS];
static DEFINE_MUTEX(mode_lock);
static bool mosaic_on;

static struct mosaic_out out[OUT_BUFFERS];
static DEFINE_SPINLOCK(out_lock);
static struct vframe_s *out_ready[OUT_BUFFERS];
static int ready_head, ready_count;
static unsigned int out_free_mask;
static struct vframe_provider_s provider;

static struct task_struct *compose_task;
static struct task_struct *feed_task;
static DECLARE_WAIT_QUEUE_HEAD(feed_wait);
/* layout of the windows; an output buffer of an older layout is cleared */
static unsigned int layout_gen = 1;
static unsigned int out_gen[OUT_BUFFERS];
/* the vsync of the output: amvideo takes a picture there */
static u64 vsync_ns;
/* composed, goes out right after the next vsync; out_epoch tells a buffer
 * of an earlier start of the mosaic */
static int out_pending = -1;
static unsigned int out_epoch, pending_epoch;
/* debug */
static u32 out_busy, out_replaced, ticks;

static u64 output_period_ns(void);
static struct ge2d_context_s *ge2d;
static struct config_para_ex_s ge2d_config;
static int canvas_src[3] = { -1, -1, -1 };
static int canvas_dst[2] = { -1, -1 };

static s64 now_90k(void)
{
	return div_s64(ktime_to_us(ktime_get()) * 9, 100);
}

/* ---- clock of a service ---- */

/* the clock of the service at the time now, false while it has none */
static bool dec_stc(struct mosaic_dec *d, s64 now, u32 *stc)
{
	unsigned long flags;
	bool valid;

	spin_lock_irqsave(&d->clock_lock, flags);
	valid = d->stc_valid;
	if (valid)
		*stc = d->stc_base + (u32)(now - d->stc_time);
	spin_unlock_irqrestore(&d->clock_lock, flags);
	return valid;
}

static void dec_stc_set(struct mosaic_dec *d, u32 stc, s64 now, bool pcr)
{
	unsigned long flags;

	spin_lock_irqsave(&d->clock_lock, flags);
	if (d->stc_valid)
		d->clock_jumps++;
	d->stc_base = stc;
	d->stc_time = now;
	d->stc_pcr = pcr;
	d->stc_valid = true;
	d->pcr_late = S32_MAX;
	d->pcr_late_prev = 0;
	d->pcr_window = now;
	spin_unlock_irqrestore(&d->clock_lock, flags);
}

static void dec_stc_reset(struct mosaic_dec *d)
{
	unsigned long flags;

	spin_lock_irqsave(&d->clock_lock, flags);
	d->stc_valid = false;
	spin_unlock_irqrestore(&d->clock_lock, flags);
}

/*
 * The PCR of the service when a frame arrived. It follows the arrival:
 * between two PCRs the clock runs on the local time; a PCR far off starts a
 * new time base. The PCR advances at the rate of the encoder, the local
 * clock nearly so; the next PCR corrects the small drift.
 */
static void dec_clock_input(struct mosaic_dec *d, struct video_frame *f,
			    s64 now)
{
	u32 stc, pcr;
	s32 off;

	if (f->reserved[0] & VIDEO_FRAME_PCR) {
		pcr = (u32)f->reserved[0];
		if (!dec_stc(d, now, &stc) || !d->stc_pcr) {
			dec_stc_set(d, pcr, now, true);
			return;
		}
		off = (s32)(pcr - stc);
		if (off > PCR_JUMP || off < -PCR_JUMP) {
			dec_stc_set(d, pcr, now, true);
			return;
		}
		/* the PCR arrives with the data, in bursts as the player reads
		 * it: the clock follows the latest of a window */
		d->pcr_late = min(d->pcr_late, off);
		if (now - d->pcr_window >= PCR_WINDOW) {
			unsigned long flags;
			s32 late = d->pcr_late;
			s32 step = late;

			if (step >= PCR_SETTLE)
				step = d->pcr_late_prev >= PCR_SETTLE ?
				       min(step, d->pcr_late_prev) : PCR_STEP_MAX;
			else if (step > -PCR_SETTLE)
				step = clamp_t(s32, step, -PCR_STEP_MAX,
					       PCR_STEP_MAX);
			d->pcr_late_prev = late - step;
			spin_lock_irqsave(&d->clock_lock, flags);
			d->stc_base += step;
			spin_unlock_irqrestore(&d->clock_lock, flags);
			if (step > 0) {
				d->fwd_max = max(d->fwd_max, step);
				d->fwd_n++;
			} else if (step < 0) {
				d->back_max = max(d->back_max, -step);
				d->back_n++;
			}
			d->pcr_late = S32_MAX;
			d->pcr_window = now;
		}
		return;
	}
	/* no PCR: the input itself keeps the time */
	if (f->pts < 0 || (d->stc_valid && d->stc_pcr))
		return;
	if (!dec_stc(d, now, &stc)) {
		dec_stc_set(d, (u32)f->pts - NO_PCR_LEAD, now, false);
		return;
	}
	off = (s32)((u32)f->pts - NO_PCR_LEAD - stc);
	if (off > INPUT_JUMP || off < -INPUT_JUMP * 4)
		dec_stc_set(d, (u32)f->pts - NO_PCR_LEAD, now, false);
}

/* ---- decoders ---- */

static int stream_vformat(int streamtype)
{
	switch (streamtype) {
	case STREAMTYPE_MPEG2:
	case STREAMTYPE_MPEG1:
		return VFORMAT_MPEG12;
	case STREAMTYPE_H264:
		return VFORMAT_H264;
	case STREAMTYPE_H265:
	case STREAMTYPE_H265_ALT:
		return VFORMAT_HEVC;
	default:
		return -1;
	}
}

/* d->lock held */
static void dec_queue_flush(struct mosaic_dec *d)
{
	struct chunk *c, *n;

	list_for_each_entry_safe(c, n, &d->queue, list) {
		list_del(&c->list);
		vfree(c);
	}
	d->queue_bytes = 0;
	d->last_in_pts = -1;
}

/* d->lock held */
static void dec_stop(struct mosaic_dec *d)
{
	dec_queue_flush(d);
	dec_stc_reset(d);
	d->synced = false;
	d->lead = 0;
	d->lead_hold = 0;
	d->delay = 0;
	/* a new stream shows its temporal layers again */
	d->hevc_max_tid = 0;
	d->hevc_seen = d->hevc_unref = 0;
	d->key_only = d->key_resync = d->skip_rasl = false;
	if (!d->vdec)
		return;
	/* the compositor holds the pictures of the decoder under mode_lock */
	mutex_lock(&mode_lock);
	vdec_release(d->vdec);
	mutex_unlock(&mode_lock);
	d->vdec = NULL;
	d->port.flag = 0;
	amports_switch_gate("vdec", 0);
	if (debug)
		pr_info("dreammosaic: %s stopped\n", d->name);
}

/* d->lock held */
static int dec_start(struct mosaic_dec *d)
{
	struct vdec_s *vdec;
	bool hevc = d->vformat == VFORMAT_HEVC;
	int ret;

	if (d->vdec)
		return 0;
	if (d->vformat < 0)
		return -EINVAL;

	memset(&d->port, 0, sizeof(d->port));
	d->port.name = d->name;
	d->port.type = PORT_TYPE_ES | PORT_TYPE_VIDEO | PORT_TYPE_FRAME |
		       PORT_TYPE_DECODER_SCHED | (hevc ? PORT_TYPE_HEVC : 0);
	d->port.vformat = d->vformat;
	d->port.flag = PORT_FLAG_IN_USE;

	vdec = vdec_create(&d->port, NULL);
	if (!vdec)
		return -ENOMEM;
	vdec_set_format(vdec, d->vformat);
	vdec_sys_info(vdec)->width = 1920;
	vdec_sys_info(vdec)->height = 1088;
	/*
	 * ge2d reads no compressed frames: HEVC writes a half size double.
	 * The buffer margin stays at the default: H.264 holds its pictures
	 * until the margin is used up, a larger one gives them later.
	 */
	if (hevc)
		vdec_set_config(vdec,
			"hevc_double_write_mode:4;parm_v4l_buffer_margin:8;");
	/* frames go to the receiver of this device, not to a video layer */
	vdec_set_receiver_name(vdec, d->recv_name);
	vdec_set_video_path(vdec, FRAME_BASE_PATH_V4L_OSD);
	/* the decoder clock is counted; it is off while no player runs */
	amports_switch_gate("vdec", 1);
	vdec_poweron(hevc ? VDEC_HEVC : VDEC_1);
	ret = vdec_init(vdec, 0);
	if (ret < 0) {
		pr_err("dreammosaic: %s vdec_init failed %d\n", d->name, ret);
		vdec_release(vdec);
		amports_switch_gate("vdec", 0);
		return ret;
	}
	/* the scheduler runs connected decoders only */
	vdec_connect(vdec);
	d->vdec = vdec;
	if (debug)
		pr_info("dreammosaic: %s started, format %d\n", d->name,
			d->vformat);
	return 0;
}

/* d->lock held: write a chunk to the decoder, the bytes taken */
static ssize_t dec_write_chunk(struct mosaic_dec *d, struct chunk *c)
{
	mm_segment_t fs;
	ssize_t ret;

	ret = dec_start(d);
	if (ret)
		return ret;
	if (c->pts >= 0 && !c->done)
		vdec_set_pts(d->vdec, (u32)c->pts);
	/* the decoder copies from user memory */
	fs = get_fs();
	set_fs(KERNEL_DS);
	ret = vdec_write_vframe(d->vdec, (const char *)(c->data + c->done),
				c->size - c->done);
	set_fs(fs);
	return ret;
}

/* d->lock held: the input that is due goes to the decoder; true while some
 * waits for its time */
static bool dec_feed(struct mosaic_dec *d, s64 now)
{
	struct chunk *c;
	ssize_t ret;
	u32 stc;
	s32 lead;

	if (!d->playing) {
		dec_queue_flush(d);
		return false;
	}
	while (!list_empty(&d->queue)) {
		c = list_first_entry(&d->queue, struct chunk, list);
		if (!c->done && c->pts >= 0 && dec_stc(d, now, &stc)) {
			lead = (s32)((u32)c->pts - stc);
			/* the queue goes ahead when too full, as the decoder
			 * would when its time stood still */
			if (lead > max_t(s32, d->lead, READ_ONCE(feed_lead)) &&
			    d->queue_bytes < QUEUE_MAX_BYTES)
				return true;
			d->lead_min = min(d->lead_min, lead);
			d->lead_max = max(d->lead_max, lead);
			if (lead < 0)
				d->fed_late++;
		}
		ret = dec_write_chunk(d, c);
		if (ret <= 0)
			/* the decoder is full, the next tick tries again */
			return true;
		c->done += ret;
		if (c->done < c->size)
			return true;
		list_del(&c->list);
		d->queue_bytes -= c->size;
		vfree(c);
	}
	return false;
}

/* parameter sets or a picture to start the decoding with */
static bool es_entry(struct mosaic_dec *d, const u8 *p, size_t n)
{
	size_t i;
	u8 type;

	for (i = 0; i + 4 < n; i++) {
		if (p[i] || p[i + 1] || p[i + 2] != 1)
			continue;
		switch (d->vformat) {
		case VFORMAT_MPEG12:
			/* sequence header */
			if (p[i + 3] == 0xb3)
				return true;
			break;
		case VFORMAT_H264:
			/* SPS or IDR picture */
			type = p[i + 3] & 0x1f;
			if (!(p[i + 3] & 0x80) && (type == 7 || type == 5))
				return true;
			break;
		case VFORMAT_HEVC:
			/* IRAP picture, VPS or SPS */
			type = (p[i + 3] >> 1) & 0x3f;
			if (!(p[i + 3] & 0x80) &&
			    ((type >= 16 && type <= 21) || type == 32 || type == 33))
				return true;
			break;
		default:
			return true;
		}
		i += 2;
	}
	return false;
}

/* ---- decoder load ---- */

/*
 * Four HD services can exceed the decoder. The windows without audio then
 * leave out pictures no other picture refers to, before decoding them: a
 * share of DROP_UNIT, raised while a picture comes from the decoder after it
 * is due, eased while every decoder keeps some pictures ready ahead.
 */
#define DROP_SCAN	4096
#define DROP_TICKS	10
#define DROP_UNIT	64
/* a picture over DROP_LATE late raises the share; DROP_EASE evaluations
 * without lets it go down again. DROP_READY pictures ready let a window take
 * back its lead. */
#define DROP_LATE	1800
#define DROP_EASE	10
#define DROP_READY	3
/* an overload after easing keeps the share DROP_MARGIN above where it
 * began; the floor goes down by one every DROP_FLOOR_EASE evaluations */
#define DROP_MARGIN	8
#define DROP_FLOOR_EASE	300
/* keyframes only from the full share on, down to DROP_UNIT - DROP_KEY_HOLD */
#define DROP_KEY_HOLD	16

static unsigned int drop_share, drop_floor;
/* the highest share: 0 leaves out nothing */
static unsigned int drop_max = DROP_UNIT;
module_param(drop_max, uint, 0644);
MODULE_PARM_DESC(drop_max, "highest share of pictures left out, of 64");
/* since the last evaluation: the latest picture against its time, the
 * pictures left out far behind */
static s32 worst_ahead = S32_MAX;
static unsigned int late_pictures;

/*
 * A window whose pictures come late gets its input earlier; pictures still
 * late at LEAD_MAX, or a window catching up, mean the decoder cannot keep
 * up: the windows without audio leave out more. All decoders keeping
 * pictures ready lets them leave out less, and the windows take back their
 * lead.
 */
static void drop_evaluate(void)
{
	static unsigned int in_time, floor_time;
	unsigned int floor = drop_floor;
	static bool eased;
	s32 worst = xchg(&worst_ahead, S32_MAX);
	unsigned int share = drop_share, skips = xchg(&late_pictures, 0);
	bool overload = skips > 0;
	struct mosaic_dec *d;
	s32 base = READ_ONCE(feed_lead), lead;
	int i;

	for (i = 0; i < DECODERS; i++) {
		d = &decs[i];
		lead = max_t(s32, d->lead, base);
		if (d->lead_hold)
			d->lead_hold--;
		if (d->win_worst != S32_MAX && d->win_worst < -DROP_LATE) {
			if (d->lead_hold) {
				/* the last step takes effect yet */
			} else if (lead < LEAD_MAX) {
				d->lead = min_t(s32, lead - d->win_worst + 900,
						LEAD_MAX);
				d->lead_hold = LEAD_HOLD;
			} else if (d->delay < DELAY_MAX && !d->win_ready &&
				   !READ_ONCE(d->clock)) {
				d->delay = min_t(s32, d->delay - d->win_worst + 900,
						 DELAY_MAX);
				d->lead_hold = LEAD_HOLD;
			} else {
				overload = true;
			}
		} else if (d->win_ready != UINT_MAX &&
			   d->win_ready > DROP_READY && !d->lead_hold) {
			/* pictures wait ready all along: take back the delay,
			 * then the lead */
			if (d->delay)
				d->delay = max_t(s32, d->delay - 450, 0);
			else if (lead > base)
				d->lead = max_t(s32, lead - 450, base);
		}
		d->win_worst = S32_MAX;
		d->win_early = S32_MIN;
		d->win_ready = UINT_MAX;
	}
	if (worst == S32_MAX) {
		WRITE_ONCE(drop_floor, 0);
		eased = false;
		return;
	}
	if (floor && ++floor_time >= DROP_FLOOR_EASE) {
		floor_time = 0;
		floor--;
	}
	if (overload) {
		in_time = 0;
		if (eased) {
			floor_time = 0;
			floor = max(floor, min_t(unsigned int,
						 share + DROP_MARGIN, DROP_UNIT));
		}
		eased = false;
		share = min_t(unsigned int, share + 2 + 2 * (worst < -9000),
			      min_t(unsigned int, READ_ONCE(drop_max), DROP_UNIT));
	} else if (worst < -DROP_LATE) {
		in_time = 0;
	} else if (++in_time >= DROP_EASE && share > floor) {
		in_time = 0;
		share--;
		eased = true;
	}
	WRITE_ONCE(drop_floor, floor);
	WRITE_ONCE(drop_share, share);
}

/* the pictures a decoder has ready */
static void dec_count_ready(struct mosaic_dec *d)
{
	struct vframe_states states;
	unsigned int ready;

	if (!d->vdec || d->frozen ||
	    vf_get_states_by_name(d->recv_name, &states))
		return;
	ready = d->fifo_count + states.buf_avail_num;
	d->win_ready = min_t(unsigned int, d->win_ready, ready);
}

/*
 * A picture no other picture refers to; the first picture of the PES
 * decides. MPEG-2: a B picture. H.264: nal_ref_idc 0. HEVC: a sub-layer
 * non-reference picture of the highest temporal layer seen, once the
 * stream showed its layers.
 */
#define HEVC_LEARN	64

static bool es_unreferenced(struct mosaic_dec *d, const u8 *p, size_t n)
{
	size_t i;
	u8 type, tid;

	for (i = 0; i + 5 < n; i++) {
		if (p[i] || p[i + 1] || p[i + 2] != 1)
			continue;
		switch (d->vformat) {
		case VFORMAT_MPEG12:
			/* picture header: picture_coding_type */
			if (p[i + 3] == 0x00)
				return ((p[i + 5] >> 3) & 7) == 3;
			break;
		case VFORMAT_H264:
			type = p[i + 3] & 0x1f;
			if (type == 1 || type == 5)
				return !(p[i + 3] & 0x60) && type == 1;
			break;
		case VFORMAT_HEVC:
			type = (p[i + 3] >> 1) & 0x3f;
			if (type > 31)
				break;
			tid = (p[i + 4] & 7) - 1;
			if (tid > d->hevc_max_tid)
				d->hevc_max_tid = tid;
			if (type <= 14 && !(type & 1) && !d->hevc_unref)
				d->hevc_unref = 1;
			if (d->hevc_seen < HEVC_LEARN) {
				d->hevc_seen++;
				return false;
			}
			return type <= 14 && !(type & 1) &&
			       tid == d->hevc_max_tid;
		default:
			return false;
		}
		i += 2;
	}
	return false;
}

/* a picture of a smaller stream costs the decoder less: in 1/256 */
static unsigned int dec_drop_weight(struct mosaic_dec *d)
{
	unsigned int w = d->width ? d->width : 1920;
	unsigned int h = d->height ? d->height : 1080;

	return clamp_t(unsigned int, w * h / (1920 * 1080 / 256), 32, 256);
}

/* the type of the first picture NAL unit, -1 none */
static int hevc_picture_type(const u8 *p, size_t n)
{
	size_t i;
	u8 type;

	for (i = 0; i + 4 < n; i++) {
		if (p[i] || p[i + 1] || p[i + 2] != 1)
			continue;
		type = (p[i + 3] >> 1) & 0x3f;
		if (type < 32)
			return type;
		i += 2;
	}
	return -1;
}

/*
 * d->lock held: an HEVC stream whose pictures all serve as reference (no
 * temporal layers) has nothing to leave out; under full load a window
 * without audio decodes its keyframes only. The way back starts at a
 * keyframe; its leading pictures refer to pictures left out.
 */
static bool dec_drop_keyframes(struct mosaic_dec *d, const u8 *p, size_t n,
			       bool want)
{
	int type = hevc_picture_type(p, min_t(size_t, n, DROP_SCAN));

	if (type < 0)
		return false;
	if (want) {
		d->key_only = true;
	} else if (d->key_only) {
		d->key_only = false;
		d->key_resync = true;
	}
	/* IRAP */
	if (type >= 16 && type <= 23) {
		d->skip_rasl = true;
		if (!d->key_only)
			d->key_resync = false;
		return false;
	}
	/* RASL */
	if (type == 8 || type == 9) {
		if (d->skip_rasl)
			goto drop;
	} else {
		d->skip_rasl = false;
	}
	if (!d->key_only && !d->key_resync)
		return false;
drop:
	d->dropped++;
	return true;
}

/* d->lock held: leave out this PES */
static bool dec_drop_pes(struct mosaic_dec *d, const u8 *p, size_t n)
{
	bool key = !READ_ONCE(d->clock) &&
		   READ_ONCE(drop_share) >= DROP_UNIT -
		   (d->key_only ? DROP_KEY_HOLD : 0) &&
		   d->hevc_seen >= HEVC_LEARN && !d->hevc_max_tid &&
		   !d->hevc_unref;

	if (d->vformat == VFORMAT_HEVC &&
	    (key || d->key_only || d->key_resync || d->skip_rasl))
		return dec_drop_keyframes(d, p, n, key);
	if (READ_ONCE(d->clock) || !READ_ONCE(drop_share))
		return false;
	if (!es_unreferenced(d, p, min_t(size_t, n, DROP_SCAN)))
		return false;
	d->drop_acc += READ_ONCE(drop_share) * dec_drop_weight(d);
	if (d->drop_acc < DROP_UNIT * 256)
		return false;
	d->drop_acc -= DROP_UNIT * 256;
	d->dropped++;
	return true;
}

/* d->lock held: a frame of the player goes into the queue of the window */
static ssize_t dec_write_frame(struct mosaic_dec *d, struct video_frame *f)
{
	size_t n = f->size[0];
	struct chunk *c;
	s64 now = now_90k();

	if (!d->playing)
		return -EPERM;
	if (n <= 0)
		return 0;
	if (d->queue_bytes + n > QUEUE_MAX_BYTES * 2)
		return -EAGAIN;
	dec_clock_input(d, f, now);
	c = vmalloc(sizeof(*c) + n);
	if (!c)
		return -ENOMEM;
	if (copy_from_user(c->data, f->data[0], n)) {
		vfree(c);
		return -EFAULT;
	}
	/* a decoder starts at an entry point of the stream */
	if (!d->synced && f->pts >= 0 &&
	    es_entry(d, c->data, min_t(size_t, n, DROP_SCAN)))
		d->synced = true;
	if (!d->synced) {
		vfree(c);
		return n;
	}
	/* the rest of a PES packet goes with its start */
	if (f->pts >= 0)
		d->drop_pes = dec_drop_pes(d, c->data, n);
	if (d->drop_pes) {
		vfree(c);
		return n;
	}
	/* a chunk without PTS goes with the one before */
	c->pts = f->pts >= 0 ? f->pts : -1;
	if (f->pts >= 0)
		d->last_in_pts = f->pts;
	c->size = n;
	c->done = 0;
	list_add_tail(&c->list, &d->queue);
	d->queue_bytes += n;
	wake_up_interruptible(&feed_wait);
	return n;
}

/* ---- pictures of a decoder ---- */

static int ge2d_scale(int src_canvas, int src_format, int sw, int sh,
		      int dst_canvas, int dw, int dh,
		      int x, int y, int cw, int ch);
static int vf_canvas(struct vframe_s *vf);
static int vf_format(struct vframe_s *vf);
static unsigned int vf_lines(struct vframe_s *vf);
static int slot_canvas(struct mosaic_slot *slot, int *canvas);
static int canvas_slot[2] = { -1, -1 };

/* the window is empty from the next picture of the compositor on */
static void dec_clear(struct mosaic_dec *d)
{
	WRITE_ONCE(d->clear, true);
}

/* compose thread: the cache empties for a stopped window */
static void dec_clear_now(struct mosaic_dec *d)
{
	unsigned long flags;

	if (!READ_ONCE(d->clear))
		return;
	spin_lock_irqsave(&d->vf_lock, flags);
	d->fifo_count = 0;
	d->show = -1;
	d->last_valid = false;
	d->last_reusable = false;
	d->clear = false;
	spin_unlock_irqrestore(&d->vf_lock, flags);
	layout_gen++;
}

/* the PTS of a frame of the decoder; fields and frames without one follow
 * the previous frame */
static u32 dec_frame_pts(struct mosaic_dec *d, struct vframe_s *vf)
{
	if (!vf->pts && d->last_valid)
		return d->last_pts + d->last_dur;
	return vf->pts;
}

/* the slot is in the cache or on screen */
static bool dec_slot_used(struct mosaic_dec *d, int index)
{
	int i;

	if (index < 0)
		return false;
	if (d->show == index)
		return true;
	for (i = 0; i < d->fifo_count; i++)
		if (d->fifo[(d->fifo_head + i) % FIFO_MAX].slot == index)
			return true;
	return false;
}

/* the buffer of a frame of the decoder */
static unsigned long vf_buffer(struct vframe_s *vf)
{
	if (vf->canvas0Addr != (u32)-1)
		return vf->canvas0Addr | (1UL << 63);
	return vf->canvas0_config[0].phy_addr;
}

/* a slot neither in the cache nor on screen, -1 none */
static int dec_free_slot(struct mosaic_dec *d)
{
	unsigned int used = 0;
	int i;

	if (d->show >= 0)
		used |= 1 << d->show;
	for (i = 0; i < d->fifo_count; i++)
		used |= 1 << d->fifo[(d->fifo_head + i) % FIFO_MAX].slot;
	for (i = 0; i < RING_SLOTS; i++)
		if (!(used & (1 << i)))
			return i;
	return -1;
}

/*
 * compose thread, mode_lock held: the pictures of the decoder go into the
 * cache, scaled to the window, and the decoder gets each back at once. A
 * full cache leaves the next pictures in the decoder.
 */
static void dec_drain(struct mosaic_dec *d)
{
	struct mosaic_entry *e;
	struct vframe_s *vf;
	unsigned long flags;
	int index, src;
	bool keep = mosaic_on && !d->frozen;
	u32 pts;

	while ((vf = vf_peek(d->recv_name))) {
		index = keep && d->fifo_count < FIFO_MAX ? dec_free_slot(d) : -1;
		if (keep && index < 0)
			return;
		vf = vf_get(d->recv_name);
		if (!vf)
			return;
		pts = dec_frame_pts(d, vf);
		d->last_valid = true;
		d->last_pts = pts;
		/* vframe duration is in 1/96000 s */
		d->last_dur = vf->duration ? vf->duration * 15 / 16 : 1800;
		d->width = vf->width;
		d->height = vf->height;
		d->duration = vf->duration;
		if (!vf->pts)
			d->pts_zero++;
		/* the other field of the picture scaled last */
		if (index >= 0 && d->last_reusable &&
		    vf_buffer(vf) == d->last_buf &&
		    dec_slot_used(d, d->last_slot)) {
			d->last_reusable = false;
			d->reused++;
			spin_lock_irqsave(&d->vf_lock, flags);
			e = &d->fifo[(d->fifo_head + d->fifo_count) % FIFO_MAX];
			e->slot = d->last_slot;
			e->pts = pts;
			d->fifo_count++;
			spin_unlock_irqrestore(&d->vf_lock, flags);
			vf_put(vf, d->recv_name);
			continue;
		}
		/* a frozen window and one without the mosaic keep their picture */
		src = index >= 0 ? vf_canvas(vf) : -1;
		if (src >= 0 && !slot_canvas(&d->slot[index], canvas_slot) &&
		    !ge2d_scale(src, vf_format(vf), vf->width, vf_lines(vf),
				canvas_slot[0] | (canvas_slot[1] << 8),
				SLOT_WIDTH, SLOT_HEIGHT,
				0, 0, SLOT_WIDTH, SLOT_HEIGHT)) {
			d->last_buf = vf_buffer(vf);
			d->last_slot = index;
			d->last_reusable = true;
			if (d->cache_pts) {
				s32 step = (s32)(pts - d->cache_pts);

				d->step_min = min(d->step_min, step);
				d->step_max = max(d->step_max, step);
			}
			d->cache_pts = pts;
			spin_lock_irqsave(&d->vf_lock, flags);
			e = &d->fifo[(d->fifo_head + d->fifo_count) % FIFO_MAX];
			e->slot = index;
			e->pts = pts;
			d->fifo_count++;
			spin_unlock_irqrestore(&d->vf_lock, flags);
		}
		vf_put(vf, d->recv_name);
	}
}

/* picture n of the cache against its time, false if none */
static bool dec_head(struct mosaic_dec *d, int n, u32 stc, bool have_stc,
		     s32 *ahead)
{
	u32 pts;

	if (n >= d->fifo_count)
		return false;
	pts = d->fifo[(d->fifo_head + n) % FIFO_MAX].pts;
	*ahead = have_stc && pts ?
		 (s32)(pts + READ_ONCE(show_delay) + d->delay - stc) : 0;
	return true;
}

/*
 * One picture per output period, the oldest due on the clock of the
 * service. A picture skip_late behind with the next due as well is left out:
 * the window catches up. A window without a clock shows its pictures as
 * they come. The picture on screen stays until the next.
 */
static bool dec_advance(struct mosaic_dec *d, s64 now)
{
	unsigned long flags;
	bool have_stc;
	u32 stc = 0;
	s32 ahead = 0, next;
	int index;

	have_stc = dec_stc(d, now, &stc);
	for (;;) {
		if (!dec_head(d, 0, stc, have_stc, &ahead))
			return false;
		/* due within half a 50 Hz period */
		if (ahead > 900) {
			d->early_max = max(d->early_max, ahead);
			d->win_early = max(d->win_early, ahead);
			return false;
		}
		/* far behind with the next due as well: leave this one out */
		if (ahead < -(s32)READ_ONCE(skip_late) &&
		    dec_head(d, 1, stc, have_stc, &next) && next <= 900) {
			spin_lock_irqsave(&d->vf_lock, flags);
			d->fifo_head = (d->fifo_head + 1) % FIFO_MAX;
			d->fifo_count--;
			spin_unlock_irqrestore(&d->vf_lock, flags);
			d->skipped++;
			/* far behind: the decoder did not keep up */
			if (ahead < -4500)
				late_pictures++;
			continue;
		}
		break;
	}
	d->ahead_min = min(d->ahead_min, ahead);
	d->ahead_max = max(d->ahead_max, ahead);
	if (have_stc) {
		worst_ahead = min(worst_ahead, ahead);
		d->win_worst = min(d->win_worst, ahead);
	}
	if (ahead < -LATE)
		d->late++;
	spin_lock_irqsave(&d->vf_lock, flags);
	index = d->fifo[d->fifo_head].slot;
	d->pts = d->fifo[d->fifo_head].pts;
	d->fifo_head = (d->fifo_head + 1) % FIFO_MAX;
	d->fifo_count--;
	d->show = index;
	spin_unlock_irqrestore(&d->vf_lock, flags);
	d->frames++;
	d->shown++;
	if (debug && d->clock) {
		u64 apts;

		if (!aml_spdif_get_pts(&apts)) {
			s32 av = (s32)(d->pts - (u32)apts);

			d->av_min = min(d->av_min, av);
			d->av_max = max(d->av_max, av);
		}
	}
	return true;
}

static void dec_report(struct mosaic_dec *d, s64 now)
{
	u32 stc = 0;
	bool have = dec_stc(d, now, &stc);

	if (now - d->rep_time < 90000 * 5)
		return;
	if (d->frames || d->queue_bytes)
		pr_info("dreammosaic: %s %ux%u shown %u late %u skipped %u dropped %u share %u (%u), early %d ms, no pts %u, delay %d ms, lead %d (%d..%d) ms, ahead %d..%d ms, queue %zu KB, fed late %u, clock %s, jumps %u, fwd %u max %d ms, back %u max %d ms, step %d..%d ms, reused %u, video-audio %d..%d ms%s\n",
			d->name, d->width, d->height, d->shown, d->late,
			d->skipped, d->dropped, READ_ONCE(drop_share),
			READ_ONCE(drop_floor),
			d->early_max / 90, d->pts_zero, d->delay / 90,
			max_t(s32, d->lead, READ_ONCE(feed_lead)) / 90,
			d->lead_min / 90, d->lead_max / 90,
			d->ahead_min / 90, d->ahead_max / 90,
			d->queue_bytes >> 10, d->fed_late,
			have ? (d->stc_pcr ? "pcr" : "input") : "none",
			d->clock_jumps, d->fwd_n, d->fwd_max / 90, d->back_n, d->back_max / 90,
			d->step_min / 90, d->step_max / 90, d->reused,
			d->av_min == S32_MAX ? 0 : d->av_min / 90,
			d->av_max == S32_MIN ? 0 : d->av_max / 90,
			d->clock ? " (audio)" : "");
	d->rep_time = now;
	d->frames = d->shown = d->late = d->skipped = d->dropped = 0;
	d->fed_late = d->clock_jumps = 0;
	d->early_max = S32_MIN;
	d->pts_zero = 0;
	d->av_min = S32_MAX;
	d->av_max = S32_MIN;
	d->fwd_max = d->back_max = 0;
	d->fwd_n = d->back_n = 0;
	d->reused = 0;
	d->step_min = S32_MAX;
	d->step_max = S32_MIN;
	d->lead_min = d->ahead_min = S32_MAX;
	d->lead_max = d->ahead_max = S32_MIN;
}

static int dec_receiver_event(int type, void *data, void *private_data)
{
	switch (type) {
	case VFRAME_EVENT_PROVIDER_QUREY_STATE:
		return RECEIVER_ACTIVE;
	default:
		break;
	}
	return 0;
}

static const struct vframe_receiver_op_s dec_receiver_ops = {
	.event_cb = dec_receiver_event,
};

/* ---- output to the video layer ---- */

static struct vframe_s *out_peek(void *op_arg)
{
	struct vframe_s *vf = NULL;
	unsigned long flags;

	spin_lock_irqsave(&out_lock, flags);
	if (ready_count)
		vf = out_ready[ready_head];
	spin_unlock_irqrestore(&out_lock, flags);
	return vf;
}

static struct vframe_s *out_get(void *op_arg)
{
	struct vframe_s *vf = NULL;
	unsigned long flags;

	/* amvideo takes a picture at its vsync only */
	WRITE_ONCE(vsync_ns, ktime_get_ns());
	spin_lock_irqsave(&out_lock, flags);
	if (ready_count) {
		vf = out_ready[ready_head];
		ready_head = (ready_head + 1) % OUT_BUFFERS;
		ready_count--;
	}
	spin_unlock_irqrestore(&out_lock, flags);
	return vf;
}

static void out_put(struct vframe_s *vf, void *op_arg)
{
	unsigned long flags;
	int i;

	spin_lock_irqsave(&out_lock, flags);
	for (i = 0; i < OUT_BUFFERS; i++)
		if (vf == &out[i].vf)
			out_free_mask |= 1 << i;
	spin_unlock_irqrestore(&out_lock, flags);
}

static int out_event(int type, void *data, void *private_data)
{
	return 0;
}

static int out_states(struct vframe_states *states, void *op_arg)
{
	unsigned long flags;

	spin_lock_irqsave(&out_lock, flags);
	states->vf_pool_size = OUT_BUFFERS;
	states->buf_avail_num = ready_count;
	states->buf_free_num = hweight32(out_free_mask);
	states->buf_recycle_num = 0;
	spin_unlock_irqrestore(&out_lock, flags);
	return 0;
}

static const struct vframe_operations_s out_ops = {
	.peek = out_peek,
	.get = out_get,
	.put = out_put,
	.event_cb = out_event,
	.vf_states = out_states,
};

static int out_alloc(void)
{
	int pages = PAGE_ALIGN(OUT_WIDTH * OUT_HEIGHT * 3 / 2) >> PAGE_SHIFT;
	int i;

	for (i = 0; i < OUT_BUFFERS; i++) {
		out[i].phys = codec_mm_alloc_for_dma(PROVIDER_NAME, pages, 0,
						     CODEC_MM_FLAGS_CMA_CLEAR |
						     CODEC_MM_FLAGS_DMA);
		if (!out[i].phys)
			return -ENOMEM;
	}
	return 0;
}

/* the window caches; the windows start empty */
static int slot_alloc(void)
{
	int pages = PAGE_ALIGN(SLOT_WIDTH * SLOT_HEIGHT * 3 / 2) >> PAGE_SHIFT;
	struct mosaic_dec *d;
	int i, j;

	for (i = 0; i < DECODERS; i++) {
		d = &decs[i];
		for (j = 0; j < RING_SLOTS; j++) {
			d->slot[j].phys = codec_mm_alloc_for_dma(PROVIDER_NAME,
					pages, 0, CODEC_MM_FLAGS_CMA_CLEAR |
					CODEC_MM_FLAGS_DMA);
			if (!d->slot[j].phys)
				return -ENOMEM;
		}
		d->fifo_count = 0;
		d->show = -1;
	}
	return 0;
}

static void slot_release(void)
{
	struct mosaic_dec *d;
	int i, j;

	for (i = 0; i < DECODERS; i++) {
		d = &decs[i];
		d->fifo_count = 0;
		d->show = -1;
		for (j = 0; j < RING_SLOTS; j++) {
			if (d->slot[j].phys)
				codec_mm_free_for_dma(PROVIDER_NAME, d->slot[j].phys);
			d->slot[j].phys = 0;
		}
	}
}

static void out_release(void)
{
	int i;

	for (i = 0; i < OUT_BUFFERS; i++) {
		if (out[i].phys)
			codec_mm_free_for_dma(PROVIDER_NAME, out[i].phys);
		out[i].phys = 0;
	}
}

static void out_setup_vframe(struct mosaic_out *o)
{
	struct vframe_s *vf = &o->vf;

	memset(vf, 0, sizeof(*vf));
	vf->type = VIDTYPE_PROGRESSIVE | VIDTYPE_VIU_FIELD | VIDTYPE_VIU_NV21;
	/* due at once: the windows are timed here */
	vf->flag = VFRAME_FLAG_VIDEO_LINEAR | VFRAME_FLAG_GAME_MODE;
	vf->bitdepth = BITDEPTH_Y8 | BITDEPTH_U8 | BITDEPTH_V8;
	vf->canvas0Addr = (u32)-1;
	vf->canvas1Addr = (u32)-1;
	vf->width = OUT_WIDTH;
	vf->height = OUT_HEIGHT;
	vf->plane_num = 2;
	vf->canvas0_config[0].phy_addr = o->phys;
	vf->canvas0_config[0].width = OUT_WIDTH;
	vf->canvas0_config[0].height = OUT_HEIGHT;
	vf->canvas0_config[1].phy_addr = o->phys + OUT_WIDTH * OUT_HEIGHT;
	vf->canvas0_config[1].width = OUT_WIDTH;
	vf->canvas0_config[1].height = OUT_HEIGHT / 2;
	vf->canvas1_config[0] = vf->canvas0_config[0];
	vf->canvas1_config[1] = vf->canvas0_config[1];
	/* shown at the next vsync, one vsync long */
	vf->duration = div_u64(output_period_ns() * 96, 1000000);
}

static int out_take_free(void)
{
	unsigned long flags;
	int i, index = -1;

	spin_lock_irqsave(&out_lock, flags);
	for (i = 0; i < OUT_BUFFERS; i++) {
		if (out_free_mask & (1 << i)) {
			out_free_mask &= ~(1 << i);
			index = i;
			break;
		}
	}
	spin_unlock_irqrestore(&out_lock, flags);
	return index;
}

static void out_queue(int index)
{
	struct vframe_s *old;
	unsigned long flags;
	int i;

	spin_lock_irqsave(&out_lock, flags);
	/* amvideo takes all pictures due at a vsync and shows the last: one
	 * not taken yet makes room for the newer */
	while (ready_count) {
		old = out_ready[ready_head];
		ready_head = (ready_head + 1) % OUT_BUFFERS;
		ready_count--;
		for (i = 0; i < OUT_BUFFERS; i++)
			if (old == &out[i].vf)
				out_free_mask |= 1 << i;
		out_replaced++;
	}
	out_ready[(ready_head + ready_count) % OUT_BUFFERS] = &out[index].vf;
	ready_count++;
	spin_unlock_irqrestore(&out_lock, flags);
	vf_notify_receiver(PROVIDER_NAME, VFRAME_EVENT_PROVIDER_VFRAME_READY,
			   NULL);
}

/* ---- compositor ---- */

static int vf_format(struct vframe_s *vf)
{
	/* an interlaced picture is scaled as a whole frame: a field scaled on
	 * its own moves by half a line against the other, which shows at the
	 * edges of a window */
	if (vf->type & VIDTYPE_VIU_NV21)
		return GE2D_FORMAT_M24_NV21;
	if (vf->type & VIDTYPE_VIU_422)
		return GE2D_FORMAT_S16_YUV422;
	return GE2D_FORMAT_M24_YUV420;
}

/* the lines to scale: a field coded picture (HEVC) has a buffer of one
 * field, half the height of the picture */
static unsigned int vf_lines(struct vframe_s *vf)
{
	if ((vf->type & VIDTYPE_VIU_FIELD) && vf->canvas0Addr == (u32)-1 &&
	    vf->canvas0_config[0].height &&
	    vf->canvas0_config[0].height < vf->height)
		return vf->height / 2;
	return vf->height;
}

/* the canvas of a frame of the decoder, -1 if ge2d cannot read it */
static int vf_canvas(struct vframe_s *vf)
{
	/* ge2d reads no AFBC; HEVC needs a double write */
	if ((vf->type & VIDTYPE_COMPRESS) && !(vf->type & VIDTYPE_VIU_NV21))
		return -1;
	if (vf->canvas0Addr != (u32)-1)
		return vf->canvas0Addr;
	canvas_config_config(canvas_src[0], &vf->canvas0_config[0]);
	if (vf->plane_num >= 2)
		canvas_config_config(canvas_src[1], &vf->canvas0_config[1]);
	if (vf->plane_num >= 3)
		canvas_config_config(canvas_src[2], &vf->canvas0_config[2]);
	return canvas_src[0] | (canvas_src[1] << 8) | (canvas_src[2] << 16);
}

static void nv21_canvas(unsigned long phys, int width, int height, int *canvas)
{
	struct canvas_config_s cfg[2];

	memset(cfg, 0, sizeof(cfg));
	cfg[0].phy_addr = phys;
	cfg[0].width = width;
	cfg[0].height = height;
	cfg[1].phy_addr = phys + width * height;
	cfg[1].width = width;
	cfg[1].height = height / 2;
	canvas_config_config(canvas[0], &cfg[0]);
	canvas_config_config(canvas[1], &cfg[1]);
}

static int slot_canvas(struct mosaic_slot *slot, int *canvas)
{
	if (!slot->phys)
		return -1;
	nv21_canvas(slot->phys, SLOT_WIDTH, SLOT_HEIGHT, canvas);
	return 0;
}

/* scale a source canvas into a rectangle of an NV21 canvas */
static int ge2d_scale(int src_canvas, int src_format, int sw, int sh,
		      int dst_canvas, int dw, int dh,
		      int x, int y, int cw, int ch)
{
	memset(&ge2d_config, 0, sizeof(ge2d_config));
	ge2d_config.src_para.mem_type = CANVAS_TYPE_INVALID;
	ge2d_config.src_para.canvas_index = src_canvas;
	ge2d_config.src_para.format = src_format;
	ge2d_config.src_para.color = 0xffffffff;
	ge2d_config.src_para.width = sw;
	ge2d_config.src_para.height = sh;
	ge2d_config.src2_para.mem_type = CANVAS_TYPE_INVALID;
	ge2d_config.dst_para.mem_type = CANVAS_TYPE_INVALID;
	ge2d_config.dst_para.canvas_index = dst_canvas;
	ge2d_config.dst_para.format = GE2D_FORMAT_M24_NV21 | GE2D_LITTLE_ENDIAN;
	ge2d_config.dst_para.width = dw;
	ge2d_config.dst_para.height = dh;
	if (ge2d_context_config_ex(ge2d, &ge2d_config) < 0)
		return -EIO;
	ge2d_wq_set_scale_coef(ge2d, FILTER_TYPE_BICUBIC, FILTER_TYPE_BICUBIC);
	stretchblt_noalpha(ge2d, 0, 0, sw, sh, x, y, cw, ch);
	return 0;
}

static int compose_clear(void)
{
	int dst = canvas_dst[0] | (canvas_dst[1] << 8);

	memset(&ge2d_config, 0, sizeof(ge2d_config));
	ge2d_config.src_para.mem_type = CANVAS_TYPE_INVALID;
	ge2d_config.src_para.canvas_index = dst;
	ge2d_config.src_para.format = GE2D_FORMAT_M24_NV21 | GE2D_LITTLE_ENDIAN;
	ge2d_config.src_para.width = OUT_WIDTH;
	ge2d_config.src_para.height = OUT_HEIGHT;
	ge2d_config.src2_para.mem_type = CANVAS_TYPE_INVALID;
	ge2d_config.dst_para.mem_type = CANVAS_TYPE_INVALID;
	ge2d_config.dst_para.canvas_index = dst;
	ge2d_config.dst_para.format = GE2D_FORMAT_M24_NV21 | GE2D_LITTLE_ENDIAN;
	ge2d_config.dst_para.width = OUT_WIDTH;
	ge2d_config.dst_para.height = OUT_HEIGHT;
	if (ge2d_context_config_ex(ge2d, &ge2d_config) < 0)
		return -EIO;
	/* black in YUV */
	fillrect(ge2d, 0, 0, OUT_WIDTH, OUT_HEIGHT, 0x008080ff);
	return 0;
}

/* the picture of a window from its cache into the output */
static int compose_window(struct mosaic_slot *slot, struct mosaic_window *w)
{
	int x, y, cw, ch;

	x = w->left * OUT_WIDTH / DST_WIDTH;
	y = w->top * OUT_HEIGHT / DST_HEIGHT;
	cw = w->width * OUT_WIDTH / DST_WIDTH;
	ch = w->height * OUT_HEIGHT / DST_HEIGHT;
	if (x < 0 || y < 0 || cw <= 0 || ch <= 0 ||
	    x + cw > OUT_WIDTH || y + ch > OUT_HEIGHT)
		return -EINVAL;
	if (slot_canvas(slot, canvas_slot))
		return -EINVAL;
	return ge2d_scale(canvas_slot[0] | (canvas_slot[1] << 8),
			  GE2D_FORMAT_M24_NV21 | GE2D_LITTLE_ENDIAN,
			  SLOT_WIDTH, SLOT_HEIGHT,
			  canvas_dst[0] | (canvas_dst[1] << 8),
			  OUT_WIDTH, OUT_HEIGHT, x, y, cw, ch);
}

static void compose_frame(void)
{
	struct mosaic_dec *d;
	struct mosaic_out *o;
	unsigned long flags;
	int index, show, i;

	index = out_take_free();
	if (index < 0) {
		out_busy++;
		return;
	}
	o = &out[index];
	nv21_canvas(o->phys, OUT_WIDTH, OUT_HEIGHT, canvas_dst);
	/* the windows cover their area each time; the rest needs a new layout */
	if (out_gen[index] != READ_ONCE(layout_gen)) {
		out_gen[index] = READ_ONCE(layout_gen);
		compose_clear();
	}
	for (i = 0; i < DECODERS; i++) {
		d = &decs[i];
		spin_lock_irqsave(&d->vf_lock, flags);
		d->dst = d->dst_pending;
		show = d->show;
		spin_unlock_irqrestore(&d->vf_lock, flags);
		if (show >= 0 && d->dst.width > 0 && d->dst.height > 0)
			compose_window(&d->slot[show], &d->dst);
	}
	out_setup_vframe(o);
	pending_epoch = READ_ONCE(out_epoch);
	out_pending = index;
}

/* one picture per vsync of the output */
static u64 output_period_ns(void)
{
	const struct vinfo_s *vinfo = get_current_vinfo();

	if (vinfo && vinfo->sync_duration_num && vinfo->sync_duration_den)
		return div_u64(NSEC_PER_SEC * (u64)vinfo->sync_duration_den,
			       vinfo->sync_duration_num);
	return NSEC_PER_SEC / 50;
}

/*
 * The compose thread wakes halfway between two vsyncs and hands out the
 * picture composed before: composing takes most of a period, and a picture
 * done close to a vsync now and then misses it, two pictures meet at the
 * next one and amvideo shows only the newer. Small steps, the rate stays
 * even.
 */
#define VSYNC_PHASE_STEP	(NSEC_PER_MSEC / 2)

static ktime_t vsync_phase(ktime_t next, u64 period)
{
	u64 vs = READ_ONCE(vsync_ns);
	s64 err;
	u32 rem;

	if (!vs || ktime_get_ns() - vs > 4 * period || ktime_to_ns(next) < vs)
		return next;
	div_u64_rem(ktime_to_ns(next) - vs, (u32)period, &rem);
	err = (s64)rem - (s64)(period / 2);
	if (err > (s64)period / 2)
		err -= period;
	err = clamp_t(s64, err, -VSYNC_PHASE_STEP, VSYNC_PHASE_STEP);
	return ktime_sub_ns(next, err);
}

/*
 * The system clock as a line: scr_base at scr_time (now_90k). The audio
 * reads it at any time, so it advances every millisecond and not in steps
 * of an output period.
 */
static DEFINE_SPINLOCK(scr_lock);
static struct hrtimer scr_timer;
static bool scr_on;
static u32 scr_base;
static s64 scr_time;

#define SCR_TICK_NS 1000000

static void scr_update(void)
{
	unsigned long flags;

	spin_lock_irqsave(&scr_lock, flags);
	if (scr_on)
		timestamp_pcrscr_set(scr_base + (u32)(now_90k() - scr_time));
	spin_unlock_irqrestore(&scr_lock, flags);
}

static enum hrtimer_restart scr_timer_fn(struct hrtimer *t)
{
	scr_update();
	hrtimer_forward_now(t, ns_to_ktime(SCR_TICK_NS));
	return HRTIMER_RESTART;
}

/* the window of the audio runs the system clock; the vsync must not
 * advance it meanwhile. Without such a window the vsync runs it. */
static void system_clock(s64 now, bool *owner)
{
	struct mosaic_dec *d;
	unsigned long flags;
	bool own = false;
	u32 stc;
	int i;

	for (i = 0; i < DECODERS; i++) {
		d = &decs[i];
		if (!READ_ONCE(d->clock) || !dec_stc(d, now, &stc))
			continue;
		spin_lock_irqsave(&scr_lock, flags);
		scr_base = stc - READ_ONCE(show_delay) - d->delay -
			   READ_ONCE(scr_latency);
		scr_time = now;
		scr_on = true;
		spin_unlock_irqrestore(&scr_lock, flags);
		scr_update();
		timestamp_pcrscr_enable(1);
		own = true;
		break;
	}
	if (!own) {
		spin_lock_irqsave(&scr_lock, flags);
		scr_on = false;
		spin_unlock_irqrestore(&scr_lock, flags);
	}
	if (own != *owner) {
		if (own)
			hrtimer_start(&scr_timer, ns_to_ktime(SCR_TICK_NS),
				      HRTIMER_MODE_REL);
		else
			hrtimer_cancel(&scr_timer);
		timestamp_pcrscr_external(own);
		*owner = own;
	}
}

static int compose_thread(void *data)
{
	unsigned int shown_gen = 0;
	ktime_t next = ktime_get();
	bool changed, owner = false;
	u64 period;
	s64 now;
	int i;

	while (!kthread_should_stop()) {
		period = output_period_ns();
		next = ktime_add_ns(next, period);
		/* a late wakeup keeps the rate; far behind, start over */
		if (ktime_after(ktime_get(), ktime_add_ns(next, 4 * period)))
			next = ktime_get();
		next = vsync_phase(next, period);
		set_current_state(TASK_INTERRUPTIBLE);
		schedule_hrtimeout(&next, HRTIMER_MODE_ABS);
		if (kthread_should_stop())
			break;
		/* the planned time: a late wakeup must not pick a later picture */
		now = div_s64(ktime_to_us(next) * 9, 100);
		ticks++;
		if (out_pending >= 0) {
			if (READ_ONCE(mosaic_on) &&
			    pending_epoch == READ_ONCE(out_epoch))
				out_queue(out_pending);
			out_pending = -1;
		}
		system_clock(now, &owner);
		changed = false;
		/* the mosaic and its buffers stay while it composes */
		mutex_lock(&mode_lock);
		for (i = 0; i < DECODERS; i++) {
			dec_clear_now(&decs[i]);
			dec_drain(&decs[i]);
			changed |= dec_advance(&decs[i], now);
			dec_count_ready(&decs[i]);
		}
		if (!(ticks % DROP_TICKS))
			drop_evaluate();
		if (debug)
			for (i = 0; i < DECODERS; i++)
				dec_report(&decs[i], now);
		if (shown_gen != READ_ONCE(layout_gen)) {
			shown_gen = READ_ONCE(layout_gen);
			changed = true;
		}
		if (changed && READ_ONCE(mosaic_on))
			compose_frame();
		mutex_unlock(&mode_lock);
	}
	__set_current_state(TASK_RUNNING);
	hrtimer_cancel(&scr_timer);
	if (owner)
		timestamp_pcrscr_external(false);
	return 0;
}

/* writes the input as it becomes due, every 2 ms while some waits */
static int feed_thread(void *data)
{
	struct mosaic_dec *d;
	bool waiting;
	s64 now;
	int i;

	while (!kthread_should_stop()) {
		waiting = false;
		now = now_90k();
		for (i = 0; i < DECODERS; i++) {
			d = &decs[i];
			if (list_empty(&d->queue))
				continue;
			mutex_lock(&d->lock);
			waiting |= dec_feed(d, now);
			mutex_unlock(&d->lock);
		}
		if (waiting)
			usleep_range(2000, 2500);
		else
			wait_event_interruptible_timeout(feed_wait,
				kthread_should_stop() ||
				!list_empty(&decs[0].queue) ||
				!list_empty(&decs[1].queue) ||
				!list_empty(&decs[2].queue) ||
				!list_empty(&decs[3].queue), HZ / 10);
	}
	return 0;
}

static int mosaic_enable(void)
{
	int i, ret;

	ge2d = create_ge2d_work_queue();
	if (IS_ERR_OR_NULL(ge2d)) {
		ge2d = NULL;
		return -ENOMEM;
	}
	for (i = 0; i < 3; i++)
		canvas_src[i] = canvas_pool_map_alloc_canvas(PROVIDER_NAME);
	for (i = 0; i < 2; i++) {
		canvas_dst[i] = canvas_pool_map_alloc_canvas(PROVIDER_NAME);
		canvas_slot[i] = canvas_pool_map_alloc_canvas(PROVIDER_NAME);
	}
	ret = out_alloc();
	if (ret)
		return ret;
	ret = slot_alloc();
	if (ret)
		return ret;

	ready_head = 0;
	ready_count = 0;
	out_free_mask = (1 << OUT_BUFFERS) - 1;
	out_epoch++;
	vfm_map_remove(MAP_NAME);
	vfm_map_add(MAP_NAME, PROVIDER_NAME " amvideo");
	vf_provider_init(&provider, PROVIDER_NAME, &out_ops, NULL);
	vf_reg_provider(&provider);
	vf_notify_receiver(PROVIDER_NAME, VFRAME_EVENT_PROVIDER_START, NULL);
	/* the player stopped its service and may have switched the layer off */
	_video_set_disable(0);
	layout_gen++;
	WRITE_ONCE(mosaic_on, true);
	return 0;
}

static void mosaic_disable(void)
{
	int i;

	WRITE_ONCE(mosaic_on, false);
	vf_unreg_provider(&provider);
	vfm_map_remove(MAP_NAME);
	out_release();
	slot_release();
	for (i = 0; i < 3; i++) {
		if (canvas_src[i] >= 0)
			canvas_pool_map_free_canvas(canvas_src[i]);
		canvas_src[i] = -1;
	}
	for (i = 0; i < 2; i++) {
		if (canvas_dst[i] >= 0)
			canvas_pool_map_free_canvas(canvas_dst[i]);
		canvas_dst[i] = -1;
		if (canvas_slot[i] >= 0)
			canvas_pool_map_free_canvas(canvas_slot[i]);
		canvas_slot[i] = -1;
	}
	if (ge2d)
		destroy_ge2d_work_queue(ge2d);
	ge2d = NULL;
}

static int decodermode_show(struct seq_file *m, void *v)
{
	seq_puts(m, mosaic_on ? "mosaic\n" : "normal\n");
	return 0;
}

static int decodermode_open(struct inode *inode, struct file *file)
{
	return single_open(file, decodermode_show, NULL);
}

static ssize_t decodermode_write(struct file *file, const char __user *buf,
				 size_t count, loff_t *ppos)
{
	char mode[16];
	size_t len = min(count, sizeof(mode) - 1);
	int ret = 0;

	if (copy_from_user(mode, buf, len))
		return -EFAULT;
	mode[len] = 0;
	strim(mode);

	mutex_lock(&mode_lock);
	if (!strcmp(mode, "mosaic")) {
		if (!mosaic_on) {
			ret = mosaic_enable();
			if (ret)
				mosaic_disable();
		}
	} else if (!strcmp(mode, "normal")) {
		if (mosaic_on)
			mosaic_disable();
	} else {
		ret = -EINVAL;
	}
	mutex_unlock(&mode_lock);
	return ret ? ret : count;
}

static const struct file_operations decodermode_fops = {
	.owner = THIS_MODULE,
	.open = decodermode_open,
	.read = seq_read,
	.write = decodermode_write,
	.llseek = seq_lseek,
	.release = single_release,
};

/* ---- window of a decoder: /proc/stb/vmpeg/N/dst_* ---- */

enum { DST_LEFT, DST_TOP, DST_WIDTH_ENTRY, DST_HEIGHT_ENTRY, DST_APPLY, DEC_CLOCK };

static const char * const dst_names[] = {
	"dst_left", "dst_top", "dst_width", "dst_height", "dst_apply",
	/* 1: the window plays the audio and runs the system clock */
	"clock",
};

struct dst_entry {
	struct mosaic_dec *dec;
	int field;
};

static struct dst_entry dst_entries[DECODERS][ARRAY_SIZE(dst_names)];

static int dst_show(struct seq_file *m, void *v)
{
	struct dst_entry *e = m->private;
	struct mosaic_window *w = &e->dec->dst_pending;
	int value = 0;

	switch (e->field) {
	case DST_LEFT:
		value = w->left;
		break;
	case DST_TOP:
		value = w->top;
		break;
	case DST_WIDTH_ENTRY:
		value = w->width;
		break;
	case DST_HEIGHT_ENTRY:
		value = w->height;
		break;
	case DEC_CLOCK:
		value = e->dec->clock;
		break;
	}
	seq_printf(m, "%08x\n", value);
	return 0;
}

static int dst_open(struct inode *inode, struct file *file)
{
	return single_open(file, dst_show, PDE_DATA(inode));
}

static ssize_t dst_write(struct file *file, const char __user *buf,
			 size_t count, loff_t *ppos)
{
	struct dst_entry *e = ((struct seq_file *)file->private_data)->private;
	struct mosaic_dec *d = e->dec;
	unsigned long flags;
	unsigned int value;
	int ret;

	ret = kstrtouint_from_user(buf, count, 16, &value);
	if (ret)
		return ret;
	spin_lock_irqsave(&d->vf_lock, flags);
	switch (e->field) {
	case DST_LEFT:
		d->dst_pending.left = value;
		break;
	case DST_TOP:
		d->dst_pending.top = value;
		break;
	case DST_WIDTH_ENTRY:
		d->dst_pending.width = value;
		break;
	case DST_HEIGHT_ENTRY:
		d->dst_pending.height = value;
		break;
	case DEC_CLOCK:
		/* the window runs the system clock from the next tick on; its
		 * pictures go on as before */
		if (d->clock != !!value && debug > 1)
			pr_info("dreammosaic: %s %s the audio\n", d->name,
				value ? "gets" : "loses");
		WRITE_ONCE(d->clock, !!value);
		break;
	}
	spin_unlock_irqrestore(&d->vf_lock, flags);
	if (e->field == DST_APPLY)
		layout_gen++;
	return count;
}

static const struct file_operations dst_fops = {
	.owner = THIS_MODULE,
	.open = dst_open,
	.read = seq_read,
	.write = dst_write,
	.llseek = seq_lseek,
	.release = single_release,
};

/* ---- video devices ---- */

static int video_open(struct inode *inode, struct file *file)
{
	struct miscdevice *misc = file->private_data;

	file->private_data = container_of(misc, struct mosaic_dec, misc);
	return 0;
}

static int video_release(struct inode *inode, struct file *file)
{
	struct mosaic_dec *d = file->private_data;

	mutex_lock(&d->lock);
	d->playing = false;
	dec_stop(d);
	mutex_unlock(&d->lock);
	dec_clear(d);
	return 0;
}

static long video_ioctl(struct file *file, unsigned int cmd,
			unsigned long arg)
{
	struct mosaic_dec *d = file->private_data;
	void __user *argp = (void __user *)arg;
	struct video_frame frame;
	long ret = 0;

	mutex_lock(&d->lock);
	switch (cmd) {
	case VIDEO_SELECT_SOURCE:
		/* the stream comes from the player in memory */
		break;
	case VIDEO_SET_STREAMTYPE:
		if (stream_vformat(arg) < 0) {
			ret = -EINVAL;
			break;
		}
		if (d->vformat != stream_vformat(arg))
			dec_stop(d);
		d->vformat = stream_vformat(arg);
		break;
	case VIDEO_PLAY:
		d->playing = true;
		d->frozen = false;
		/* the decoder takes the parameters the player set for its
		 * start, as the double write of HEVC */
		if (d->vformat >= 0)
			dec_start(d);
		break;
	case VIDEO_STOP:
		d->playing = false;
		dec_stop(d);
		dec_clear(d);
		break;
	case VIDEO_FREEZE:
		d->frozen = true;
		break;
	case VIDEO_CONTINUE:
		d->frozen = false;
		break;
	case VIDEO_CLEAR_BUFFER:
		/* the next frame starts a new decoder and a new clock */
		dec_stop(d);
		break;
	case VIDEO_SET_FRAME:
		if (copy_from_user(&frame, argp, sizeof(frame))) {
			ret = -EFAULT;
			break;
		}
		ret = dec_write_frame(d, &frame);
		break;
	case VIDEO_GET_PTS: {
		u64 pts = d->pts;

		if (copy_to_user(argp, &pts, sizeof(pts)))
			ret = -EFAULT;
		break;
	}
	case VIDEO_GET_SIZE: {
		video_size_t size = {
			.w = d->width,
			.h = d->height,
			.aspect_ratio = VIDEO_FORMAT_16_9,
		};

		if (!d->width || !d->height)
			ret = -EAGAIN;
		else if (copy_to_user(argp, &size, sizeof(size)))
			ret = -EFAULT;
		break;
	}
	case VIDEO_GET_FRAME_RATE: {
		unsigned int rate = d->duration ?
			96000 * 1000 / d->duration : 0;

		if (copy_to_user(argp, &rate, sizeof(rate)))
			ret = -EFAULT;
		break;
	}
	case VIDEO_GET_EVENT:
		ret = -EWOULDBLOCK;
		break;
	case VIDEO_FAST_FORWARD:
	case VIDEO_SLOWMOTION:
		break;
	default:
		ret = -ENOIOCTLCMD;
		break;
	}
	mutex_unlock(&d->lock);
	return ret;
}

static unsigned int video_poll(struct file *file, poll_table *wait)
{
	/* no events: size changes are read with VIDEO_GET_SIZE */
	return 0;
}

static const struct file_operations video_fops = {
	.owner = THIS_MODULE,
	.open = video_open,
	.release = video_release,
	.poll = video_poll,
	.unlocked_ioctl = video_ioctl,
	.compat_ioctl = video_ioctl,
	.llseek = no_llseek,
};

/* ---- module ---- */

static void dec_proc_remove(struct mosaic_dec *d)
{
	char path[32];

	if (!d->proc_dir)
		return;
	snprintf(path, sizeof(path), "stb/vmpeg/%d", d->index);
	remove_proc_subtree(path, NULL);
	d->proc_dir = NULL;
}

static int dec_proc_add(struct mosaic_dec *d, int i)
{
	char path[32];
	int f;

	snprintf(path, sizeof(path), "stb/vmpeg/%d", d->index);
	d->proc_dir = proc_mkdir(path, NULL);
	if (!d->proc_dir)
		return -ENOMEM;
	for (f = 0; f < ARRAY_SIZE(dst_names); f++) {
		dst_entries[i][f].dec = d;
		dst_entries[i][f].field = f;
		if (!proc_create_data(dst_names[f], 0664, d->proc_dir,
				      &dst_fops, &dst_entries[i][f]))
			return -ENOMEM;
	}
	return 0;
}

static void dreammosaic_cleanup(void)
{
	struct mosaic_dec *d;
	int i;

	if (feed_task)
		kthread_stop(feed_task);
	feed_task = NULL;
	if (compose_task)
		kthread_stop(compose_task);
	compose_task = NULL;
	mutex_lock(&mode_lock);
	if (mosaic_on)
		mosaic_disable();
	mutex_unlock(&mode_lock);
	for (i = 0; i < DECODERS; i++) {
		d = &decs[i];
		if (d->registered)
			misc_deregister(&d->misc);
		d->registered = false;
		mutex_lock(&d->lock);
		dec_stop(d);
		mutex_unlock(&d->lock);
		vf_unreg_receiver(&d->recv);
		dec_proc_remove(d);
	}
	remove_proc_entry("stb/video/decodermode", NULL);
}

static int __init dreammosaic_init(void)
{
	struct mosaic_dec *d;
	int i, ret;

	for (i = 0; i < DECODERS; i++) {
		d = &decs[i];
		d->index = FIRST_DEVICE + i;
		d->vformat = -1;
		mutex_init(&d->lock);
		INIT_LIST_HEAD(&d->queue);
		d->win_worst = S32_MAX;
		d->win_early = S32_MIN;
		d->win_ready = UINT_MAX;
		d->last_in_pts = -1;
		spin_lock_init(&d->clock_lock);
		spin_lock_init(&d->vf_lock);
		d->show = -1;
		d->lead_min = d->ahead_min = S32_MAX;
		d->lead_max = d->ahead_max = S32_MIN;
		snprintf(d->name, sizeof(d->name), "video%d", d->index);
		snprintf(d->nodename, sizeof(d->nodename),
			 "dvb/adapter0/video%d", d->index);
		snprintf(d->recv_name, sizeof(d->recv_name),
			 PROVIDER_NAME ".%d", i);
		vf_receiver_init(&d->recv, d->recv_name, &dec_receiver_ops, d);
		vf_reg_receiver(&d->recv);
		ret = dec_proc_add(d, i);
		if (ret)
			goto err;
		d->misc.minor = MISC_DYNAMIC_MINOR;
		d->misc.name = d->name;
		d->misc.nodename = d->nodename;
		d->misc.mode = 0660;
		d->misc.fops = &video_fops;
		ret = misc_register(&d->misc);
		if (ret)
			goto err;
		d->registered = true;
	}
	if (!proc_create("stb/video/decodermode", 0664, NULL,
			 &decodermode_fops)) {
		ret = -ENOMEM;
		goto err;
	}
	hrtimer_init(&scr_timer, CLOCK_MONOTONIC, HRTIMER_MODE_REL);
	scr_timer.function = scr_timer_fn;
	compose_task = kthread_run(compose_thread, NULL, "dreammosaic");
	if (IS_ERR(compose_task)) {
		ret = PTR_ERR(compose_task);
		compose_task = NULL;
		goto err;
	}
	feed_task = kthread_run(feed_thread, NULL, "dreammosaic-feed");
	if (IS_ERR(feed_task)) {
		ret = PTR_ERR(feed_task);
		feed_task = NULL;
		goto err;
	}
	pr_info("dreammosaic: video%d..video%d\n", FIRST_DEVICE,
		FIRST_DEVICE + DECODERS - 1);
	return 0;

err:
	dreammosaic_cleanup();
	return ret;
}

static void __exit dreammosaic_exit(void)
{
	dreammosaic_cleanup();
}

module_init(dreammosaic_init);
module_exit(dreammosaic_exit);

MODULE_DESCRIPTION("Mosaic decoders for the Dreambox One/Two");
MODULE_LICENSE("GPL");
