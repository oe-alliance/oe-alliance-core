// SPDX-License-Identifier: GPL-2.0-only
#include <errno.h>
#include <fcntl.h>
#include <linux/dma-buf.h>
#include <linux/dma-heap.h>
#include <linux/videodev2.h>
#include <stdint.h>
#include <sys/ioctl.h>
#include <sys/mman.h>
#include <unistd.h>
#include <amvdec_ioc.h>
#include <gst/video/gstvideodecoder.h>

enum { POOL = 5, SET_FORMAT = 0x105, SET_PTS_US = 0x10f, INIT_PORT = 0x111,
	SET_PATH = 0x174, SET_RECEIVER = 0x176, SET_CONFIG = 0x301 };
static guint outputWidth, outputHeight;

typedef struct {
	GstVideoDecoder parent;
	int stream, capture, heap, index, fd[POOL];
	guint8 *map[POOL];
	gsize size;
	guint stride, bufferHeight;
	GstVideoInfo info;
	GstVideoCodecState *input;
} Sm1Decoder;
typedef GstVideoDecoderClass Sm1DecoderClass;
G_DEFINE_TYPE(Sm1Decoder, sm1_decoder, GST_TYPE_VIDEO_DECODER)

static int setValue(int fd, guint cmd, guint64 value)
{
	struct am_ioctl_parm arg = { .cmd = cmd, .data_64 = value };
	return ioctl(fd, AMSTREAM_IOC_SET, &arg);
}

static gboolean closeDecoder(GstVideoDecoder *decoder)
{
	Sm1Decoder *s = (Sm1Decoder *)decoder;
	g_clear_pointer(&s->input, gst_video_codec_state_unref);
	if (s->capture >= 0) {
		enum v4l2_buf_type type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
		ioctl(s->capture, VIDIOC_STREAMOFF, &type);
	}
	if (s->stream >= 0) close(s->stream);
	if (s->capture >= 0) close(s->capture);
	if (s->heap >= 0) close(s->heap);
	s->heap = s->stream = s->capture = -1;
	for (guint i = 0; i < POOL; i++) {
		if (s->map[i] != MAP_FAILED) munmap(s->map[i], s->size);
		if (s->fd[i] >= 0) close(s->fd[i]);
		s->map[i] = MAP_FAILED;
		s->fd[i] = -1;
	}
	return TRUE;
}

static int enqueue(Sm1Decoder *s, guint i)
{
	struct v4l2_buffer b = { .type = V4L2_BUF_TYPE_VIDEO_CAPTURE, .index = i,
		.memory = V4L2_MEMORY_DMABUF };
	b.m.fd = s->fd[i];
	return ioctl(s->capture, VIDIOC_QBUF, &b);
}

static gboolean openCapture(Sm1Decoder *s)
{
	char *base = NULL;
	if (!g_file_get_contents("/sys/class/ionvideo/video_nr_base", &base, NULL, NULL))
		return FALSE;
	int first = atoi(base);
	g_free(base);
	for (int i = 0; i < 9; i++) {
		char device[48];
		g_snprintf(device, sizeof(device), "/dev/video%d", first + i);
		int fd = open(device, O_RDWR | O_NONBLOCK | O_CLOEXEC);
		if (fd < 0) continue;
		struct v4l2_capability cap = {};
		if (!ioctl(fd, VIDIOC_QUERYCAP, &cap) && g_str_has_prefix((char *)cap.driver, "ionvideo")) {
			s->capture = fd;
			s->index = i;
			break;
		}
		close(fd);
	}
	if (s->capture < 0) return FALSE;
	struct v4l2_format fmt = { .type = V4L2_BUF_TYPE_VIDEO_CAPTURE };
	fmt.fmt.pix.width = outputWidth;
	fmt.fmt.pix.height = outputHeight;
	fmt.fmt.pix.pixelformat = V4L2_PIX_FMT_NV21;
	fmt.fmt.pix.field = V4L2_FIELD_NONE;
	if (ioctl(s->capture, VIDIOC_S_FMT, &fmt) < 0) return FALSE;
	s->stride = GST_ROUND_UP_32(fmt.fmt.pix.width);
	s->bufferHeight = fmt.fmt.pix.height;
	s->size = s->stride * s->bufferHeight * 3 / 2;
	s->heap = open("/dev/dma_heap/heap-codecmm", O_RDWR | O_CLOEXEC);
	if (s->heap < 0) return FALSE;
	for (guint i = 0; i < POOL; i++) {
		struct dma_heap_allocation_data a = { .len = s->size, .fd_flags = O_RDWR | O_CLOEXEC };
		if (ioctl(s->heap, DMA_HEAP_IOCTL_ALLOC, &a) < 0) return FALSE;
		s->fd[i] = a.fd;
		s->map[i] = mmap(NULL, s->size, PROT_READ, MAP_SHARED, a.fd, 0);
		if (s->map[i] == MAP_FAILED || enqueue(s, i) < 0) return FALSE;
	}
	enum v4l2_buf_type type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	return ioctl(s->capture, VIDIOC_STREAMON, &type) == 0;
}

static gboolean setFormat(GstVideoDecoder *decoder, GstVideoCodecState *input)
{
	Sm1Decoder *s = (Sm1Decoder *)decoder;
	closeDecoder(decoder);
	const char *mime = gst_structure_get_name(gst_caps_get_structure(input->caps, 0));
	gboolean hevc = !strcmp(mime, "video/x-h265");
	guint format = hevc ? VFORMAT_HEVC : VFORMAT_H264;
	s->stream = open(hevc ? "/dev/amstream_hevc_frame" : "/dev/amstream_vframe", O_WRONLY | O_CLOEXEC | O_NONBLOCK);
	if (s->stream < 0 || !openCapture(s)) goto fail;
	struct dec_sysinfo info = { .format = hevc ? 15 : 4,
		.width = GST_VIDEO_INFO_WIDTH(&input->info), .height = GST_VIDEO_INFO_HEIGHT(&input->info) };
	if (hevc) {
		char config[] = "hevc_double_write_mode:1;";
		struct am_ioctl_parm_ptr p = { .pointer = config, .cmd = SET_CONFIG, .len = sizeof(config) };
		if (ioctl(s->stream, AMSTREAM_IOC_SET_PTR, &p) < 0) goto fail;
	}
	if (setValue(s->stream, SET_FORMAT, format) < 0 ||
	    ioctl(s->stream, AMSTREAM_IOC_SYSINFO, &info) < 0 ||
	    setValue(s->stream, SET_RECEIVER, s->index) < 0 ||
	    setValue(s->stream, SET_PATH, FRAME_BASE_PATH_IONVIDEO) < 0 ||
	    setValue(s->stream, INIT_PORT, 0) < 0) goto fail;
	s->input = gst_video_codec_state_ref(input);
	GstVideoCodecState *out = gst_video_decoder_set_output_state(decoder, GST_VIDEO_FORMAT_NV21, outputWidth, outputHeight, input);
	s->info = out->info;
	gst_video_codec_state_unref(out);
	return gst_video_decoder_negotiate(decoder);
fail:
	GST_ERROR_OBJECT(decoder, "SM1 decoder initialization failed: %s", g_strerror(errno));
	closeDecoder(decoder);
	return FALSE;
}

static GstVideoCodecFrame *findFrame(GstVideoDecoder *dec, GstClockTime pts)
{
	GList *frames = gst_video_decoder_get_frames(dec);
	GstVideoCodecFrame *best = NULL;
	guint64 distance = G_MAXUINT64;
	for (GList *l = frames; l; l = l->next) {
		GstVideoCodecFrame *f = l->data;
		guint64 delta = GST_CLOCK_TIME_IS_VALID(f->pts) ? (pts < f->pts ? f->pts - pts : pts - f->pts) : G_MAXUINT64;
		if (!best || delta < distance) { best = f; distance = delta; }
	}
	if (best) gst_video_codec_frame_ref(best);
	g_list_free_full(frames, (GDestroyNotify)gst_video_codec_frame_unref);
	return best;
}

static GstFlowReturn receiveFrames(Sm1Decoder *s)
{
	for (;;) {
		struct v4l2_buffer b = { .type = V4L2_BUF_TYPE_VIDEO_CAPTURE, .memory = V4L2_MEMORY_DMABUF };
		if (ioctl(s->capture, VIDIOC_DQBUF, &b) < 0)
			return errno == EAGAIN ? GST_FLOW_OK : GST_FLOW_ERROR;
		if (b.index >= POOL) return GST_FLOW_ERROR;
		guint64 us = ((guint64)(guint32)b.timestamp.tv_sec << 32) | (guint32)b.timestamp.tv_usec;
		GstVideoDecoder *dec = GST_VIDEO_DECODER(s);
		/* ION reports the visible GE2D rectangle separately from the DMA stride. */
		guint width = b.timecode.type & ~1U, height = b.timecode.flags & ~1U;
		if (!width || !height || width > s->stride || height > s->bufferHeight)
			return GST_FLOW_ERROR;
		if (width != GST_VIDEO_INFO_WIDTH(&s->info) || height != GST_VIDEO_INFO_HEIGHT(&s->info)) {
			GstVideoCodecState *out = gst_video_decoder_set_output_state(dec, GST_VIDEO_FORMAT_NV21, width, height, s->input);
			s->info = out->info;
			gst_video_codec_state_unref(out);
			if (!gst_video_decoder_negotiate(dec))
				return GST_FLOW_NOT_NEGOTIATED;
		}
		GstVideoCodecFrame *f = findFrame(dec, us * GST_USECOND);
		GstFlowReturn flow = GST_FLOW_OK;
		if (f) {
			flow = gst_video_decoder_allocate_output_frame(dec, f);
			GstVideoFrame out;
			if (flow == GST_FLOW_OK && gst_video_frame_map(&out, &s->info, f->output_buffer, GST_MAP_WRITE)) {
				struct dma_buf_sync sync = { .flags = DMA_BUF_SYNC_READ | DMA_BUF_SYNC_START };
				if (ioctl(s->fd[b.index], DMA_BUF_IOCTL_SYNC, &sync) < 0) flow = GST_FLOW_ERROR;
				if (flow == GST_FLOW_OK) {
					for (guint plane = 0; plane < 2; plane++) {
						const guint8 *src = s->map[b.index] + (plane ? s->stride * s->bufferHeight : 0);
						guint8 *dst = GST_VIDEO_FRAME_PLANE_DATA(&out, plane);
						for (guint row = 0; row < (plane ? height / 2 : height); row++) {
							memcpy(dst, src, width);
							dst += GST_VIDEO_FRAME_PLANE_STRIDE(&out, plane);
							src += s->stride;
						}
					}
					sync.flags = DMA_BUF_SYNC_READ | DMA_BUF_SYNC_END;
					if (ioctl(s->fd[b.index], DMA_BUF_IOCTL_SYNC, &sync) < 0) flow = GST_FLOW_ERROR;
				}
				gst_video_frame_unmap(&out);
			} else if (flow == GST_FLOW_OK) flow = GST_FLOW_ERROR;
			if (flow == GST_FLOW_OK) flow = gst_video_decoder_finish_frame(dec, f);
			else gst_video_decoder_drop_frame(dec, f);
		}
		if (enqueue(s, b.index) < 0) return GST_FLOW_ERROR;
		if (flow != GST_FLOW_OK) return flow;
	}
}

static GstFlowReturn submitFrame(GstVideoDecoder *decoder, GstVideoCodecFrame *frame)
{
	Sm1Decoder *s = (Sm1Decoder *)decoder;
	if (s->stream < 0) { gst_video_decoder_drop_frame(decoder, frame); return GST_FLOW_ERROR; }
	GstFlowReturn flow = receiveFrames(s);
	if (flow != GST_FLOW_OK) { gst_video_decoder_drop_frame(decoder, frame); return flow; }
	/* A corrupt DVB access unit can be discarded by the hardware. Bound the
	 * references retained for reordering even when reception breaks up. */
	GstVideoCodecFrame *old = gst_video_decoder_get_oldest_frame(decoder);
	if (old) {
		if ((guint32)(frame->system_frame_number - old->system_frame_number) > 96)
			gst_video_decoder_drop_frame(decoder, old);
		else
			gst_video_codec_frame_unref(old);
	}
	GstMapInfo in;
	if (!gst_buffer_map(frame->input_buffer, &in, GST_MAP_READ)) { gst_video_decoder_drop_frame(decoder, frame); return GST_FLOW_ERROR; }
	if (GST_CLOCK_TIME_IS_VALID(frame->pts) && setValue(s->stream, SET_PTS_US, frame->pts / GST_USECOND) < 0) {
		gst_buffer_unmap(frame->input_buffer, &in);
		gst_video_decoder_drop_frame(decoder, frame);
		return GST_FLOW_ERROR;
	}
	gint64 deadline = g_get_monotonic_time() + G_TIME_SPAN_SECOND;
	for (;;) {
		ssize_t ret = write(s->stream, in.data, in.size);
		if (ret == (ssize_t)in.size) break;
		if (ret >= 0 || (errno != EAGAIN && errno != EINTR) || g_get_monotonic_time() >= deadline) {
			flow = GST_FLOW_ERROR;
			break;
		}
		flow = receiveFrames(s);
		if (flow != GST_FLOW_OK) break;
		g_usleep(2000);
	}
	gst_buffer_unmap(frame->input_buffer, &in);
	gst_video_codec_frame_unref(frame);
	return flow;
}

static void sm1_decoder_init(Sm1Decoder *s)
{
	s->stream = s->capture = s->heap = -1;
	for (guint i = 0; i < POOL; i++) { s->fd[i] = -1; s->map[i] = MAP_FAILED; }
	gst_video_decoder_set_packetized(GST_VIDEO_DECODER(s), TRUE);
}

static void sm1_decoder_class_init(Sm1DecoderClass *klass)
{
	GstElementClass *element = GST_ELEMENT_CLASS(klass);
	static GstStaticPadTemplate sink = GST_STATIC_PAD_TEMPLATE("sink", GST_PAD_SINK, GST_PAD_ALWAYS, GST_STATIC_CAPS("video/x-h264,stream-format=byte-stream,alignment=au,parsed=true;video/x-h265,stream-format=byte-stream,alignment=au,parsed=true"));
	static GstStaticPadTemplate src = GST_STATIC_PAD_TEMPLATE("src", GST_PAD_SRC, GST_PAD_ALWAYS, GST_STATIC_CAPS("video/x-raw,format=NV21"));
	gst_element_class_add_static_pad_template(element, &sink);
	gst_element_class_add_static_pad_template(element, &src);
	gst_element_class_set_static_metadata(element, "SM1 scaled video decoder", "Codec/Decoder/Video", "Decode and scale for a GTX mobile stream", "OE-Alliance");
	klass->set_format = setFormat;
	klass->handle_frame = submitFrame;
	klass->stop = closeDecoder;
}

gboolean gtx_register_sm1_decoder(guint width, guint height)
{
	outputWidth = width;
	outputHeight = height;
	return gst_element_register(NULL, "gtxsm1dec", GST_RANK_PRIMARY + 100, sm1_decoder_get_type());
}
