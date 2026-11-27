/* SPDX-License-Identifier: GPL-2.0-only */
#include <dlfcn.h>
#include <errno.h>
#include <fcntl.h>
#include <glib-unix.h>
#include <gst/gst.h>
#include <gst/video/gstvideoencoder.h>
#include <stdio.h>
#include <string.h>
#include <sys/file.h>
#include <unistd.h>

/* Each client runs in its own process. Both vendor libraries export the
 * same symbols with different signatures and keep per-process VPU state. */
typedef struct {
	GstVideoEncoder parent;
	GstVideoInfo info;
	gboolean hevc;
	int bitrate;
	void *library;
	long handle;
	int (*destroy)(long);
	int (*avcEncode)(long, int, unsigned char *, int, unsigned char *, int, int, void *);
	int (*hevcEncode)(long, int, unsigned char *, unsigned, unsigned char *, int);
	guint8 *pixels;
} GtxEncoder;

typedef GstVideoEncoderClass GtxEncoderClass;
G_DEFINE_TYPE(GtxEncoder, gtx_encoder, GST_TYPE_VIDEO_ENCODER)

enum { PROP_HEVC = 1, PROP_BITRATE };

static void setProperty(GObject *object, guint prop, const GValue *value, GParamSpec *spec)
{
	GtxEncoder *enc = (GtxEncoder *)object;
	switch (prop) {
	case PROP_HEVC: enc->hevc = g_value_get_boolean(value); break;
	case PROP_BITRATE: enc->bitrate = g_value_get_int(value); break;
	default: G_OBJECT_WARN_INVALID_PROPERTY_ID(object, prop, spec);
	}
}

static void getProperty(GObject *object, guint prop, GValue *value, GParamSpec *spec)
{
	GtxEncoder *enc = (GtxEncoder *)object;
	switch (prop) {
	case PROP_HEVC: g_value_set_boolean(value, enc->hevc); break;
	case PROP_BITRATE: g_value_set_int(value, enc->bitrate); break;
	default: G_OBJECT_WARN_INVALID_PROPERTY_ID(object, prop, spec);
	}
}

static gboolean stopEncoder(GstVideoEncoder *encoder)
{
	GtxEncoder *enc = (GtxEncoder *)encoder;
	if (enc->handle > 0)
		enc->destroy(enc->handle);
	enc->handle = 0;
	if (enc->library)
		dlclose(enc->library);
	enc->library = NULL;
	g_clear_pointer(&enc->pixels, g_free);
	return TRUE;
}

static gboolean setFormat(GstVideoEncoder *encoder, GstVideoCodecState *input)
{
	GtxEncoder *enc = (GtxEncoder *)encoder;
	int width = GST_VIDEO_INFO_WIDTH(&input->info), height = GST_VIDEO_INFO_HEIGHT(&input->info);
	int fps = gst_util_uint64_scale_round(GST_VIDEO_INFO_FPS_N(&input->info), 1, MAX(1, GST_VIDEO_INFO_FPS_D(&input->info)));
	stopEncoder(encoder);
	if (width % 16 || height % 2 || fps < 1 || fps > 30)
		return FALSE;
	enc->library = dlopen(enc->hevc ? GTX_VENC_DIR "/libgtxhevc.so.1" : GTX_VENC_DIR "/libvpcodec.so", RTLD_NOW | RTLD_LOCAL);
	if (!enc->library)
		goto failed;
	void *init = dlsym(enc->library, "vl_video_encoder_init");
	void *encode = dlsym(enc->library, "vl_video_encoder_encode");
	enc->destroy = dlsym(enc->library, "vl_video_encoder_destroy");
	if (!init || !encode || !enc->destroy)
		goto failed;
	if (enc->hevc) {
		long (*hevcInit)(int, int, int, int, int, int) = init;
		enc->hevcEncode = encode;
		enc->handle = hevcInit(5, width, height, fps, enc->bitrate, fps);
	} else {
		long (*avcInit)(int, int, int, int, int, int, int, int, int, int, int) = init;
		enc->avcEncode = encode;
		enc->handle = avcInit(4, width, height, fps, enc->bitrate, fps, 1, 12, 48, 12, 48);
	}
	if (enc->handle <= 0)
		goto failed;
	enc->info = input->info;
	enc->pixels = g_malloc((gsize)width * height * 3 / 2);
	GstCaps *caps = gst_caps_new_simple(enc->hevc ? "video/x-h265" : "video/x-h264",
		"stream-format", G_TYPE_STRING, "byte-stream", "alignment", G_TYPE_STRING, "au", NULL);
	gst_video_codec_state_unref(gst_video_encoder_set_output_state(encoder, caps, input));
	return gst_video_encoder_negotiate(encoder);
failed:
	GST_ELEMENT_ERROR(enc, RESOURCE, OPEN_READ_WRITE, ("Cannot start GTX hardware encoder"), (NULL));
	stopEncoder(encoder);
	return FALSE;
}

static gboolean isKeyframe(const guint8 *data, gsize length, gboolean hevc)
{
	for (gsize i = 0; i + 3 < length; ++i) {
		if (!data[i] && !data[i + 1] && data[i + 2] == 1) {
			unsigned type = hevc ? (data[i + 3] >> 1) & 63 : data[i + 3] & 31;
			if (hevc ? type >= 16 && type <= 21 : type == 5)
				return TRUE;
		}
	}
	return FALSE;
}

static GstFlowReturn handleFrame(GstVideoEncoder *encoder, GstVideoCodecFrame *frame)
{
	GtxEncoder *enc = (GtxEncoder *)encoder;
	GstVideoFrame input;
	const int width = GST_VIDEO_INFO_WIDTH(&enc->info), height = GST_VIDEO_INFO_HEIGHT(&enc->info);
	if (!gst_video_frame_map(&input, &enc->info, frame->input_buffer, GST_MAP_READ))
		return GST_FLOW_ERROR;
	guint8 *dest = enc->pixels;
	for (int plane = 0; plane < 2; ++plane) {
		const guint8 *src = GST_VIDEO_FRAME_PLANE_DATA(&input, plane);
		int rows = plane ? height / 2 : height;
		for (int y = 0; y < rows; ++y) {
			memcpy(dest, src, width);
			dest += width;
			src += GST_VIDEO_FRAME_PLANE_STRIDE(&input, plane);
		}
	}
	gst_video_frame_unmap(&input);
	gsize capacity = MAX(1024 * 1024, width * height * 2);
	GstBuffer *buffer = gst_video_encoder_allocate_output_buffer(encoder, capacity);
	GstMapInfo out;
	if (!buffer || !gst_buffer_map(buffer, &out, GST_MAP_WRITE)) {
		if (buffer) gst_buffer_unref(buffer);
		return GST_FLOW_ERROR;
	}
	struct { int fd[3]; unsigned planes; } dma = {{-1, -1, -1}, 0};
	int type = GST_VIDEO_CODEC_FRAME_IS_FORCE_KEYFRAME(frame) ? 2 : 1;
	int bytes = enc->hevc ? enc->hevcEncode(enc->handle, type, enc->pixels, capacity, out.data, 0)
		: enc->avcEncode(enc->handle, type, enc->pixels, capacity, out.data, 1, 0, &dma);
	if (bytes > 0 && (gsize)bytes <= capacity && isKeyframe(out.data, bytes, enc->hevc))
		GST_VIDEO_CODEC_FRAME_SET_SYNC_POINT(frame);
	gst_buffer_unmap(buffer, &out);
	if (bytes < 0 || (gsize)bytes > capacity) {
		gst_buffer_unref(buffer);
		GST_ELEMENT_ERROR(enc, STREAM, ENCODE, ("GTX encoder rejected a frame"), ("size %d", bytes));
		return GST_FLOW_ERROR;
	}
	if (bytes) {
		gst_buffer_resize(buffer, 0, bytes);
		frame->output_buffer = buffer;
	} else {
		gst_buffer_unref(buffer);
	}
	return gst_video_encoder_finish_frame(encoder, frame);
}

static void gtx_encoder_class_init(GtxEncoderClass *klass)
{
	GObjectClass *object = G_OBJECT_CLASS(klass);
	GstElementClass *element = GST_ELEMENT_CLASS(klass);
	object->set_property = setProperty;
	object->get_property = getProperty;
	g_object_class_install_property(object, PROP_HEVC, g_param_spec_boolean("hevc", "HEVC", "Use the Wave4 encoder", FALSE, G_PARAM_READWRITE));
	g_object_class_install_property(object, PROP_BITRATE, g_param_spec_int("bitrate", "Bitrate", "Target bits per second", 64000, 20000000, 1500000, G_PARAM_READWRITE));
	static GstStaticPadTemplate sink = GST_STATIC_PAD_TEMPLATE("sink", GST_PAD_SINK, GST_PAD_ALWAYS, GST_STATIC_CAPS("video/x-raw, format=(string)NV21, width=(int)[64,1920], height=(int)[64,1080]"));
	static GstStaticPadTemplate src = GST_STATIC_PAD_TEMPLATE("src", GST_PAD_SRC, GST_PAD_ALWAYS, GST_STATIC_CAPS("video/x-h264,stream-format=byte-stream,alignment=au;video/x-h265,stream-format=byte-stream,alignment=au"));
	gst_element_class_add_static_pad_template(element, &sink);
	gst_element_class_add_static_pad_template(element, &src);
	gst_element_class_set_static_metadata(element, "GTX hardware encoder", "Codec/Encoder/Video", "SM1 hardware AVC and HEVC", "OE-Alliance");
	klass->set_format = setFormat;
	klass->handle_frame = handleFrame;
	klass->stop = stopEncoder;
}

static void gtx_encoder_init(GtxEncoder *enc)
{
	enc->bitrate = 1500000;
}

typedef struct { GMainLoop *loop; gboolean failed; } Stream;

static gboolean onMessage(GstBus *bus, GstMessage *msg, gpointer user)
{
	Stream *stream = user;
	(void)bus;
	if (GST_MESSAGE_TYPE(msg) == GST_MESSAGE_ERROR) {
		GError *error = NULL;
		gst_message_parse_error(msg, &error, NULL);
		g_printerr("gtx-transcode: %s: %s\n", GST_OBJECT_NAME(msg->src), error->message);
		g_error_free(error);
		stream->failed = TRUE;
	} else if (GST_MESSAGE_TYPE(msg) != GST_MESSAGE_EOS) {
		return TRUE;
	}
	g_main_loop_quit(stream->loop);
	return TRUE;
}

static gboolean onDisconnect(gint fd, GIOCondition condition, gpointer user)
{
	(void)fd;
	(void)condition;
	g_main_loop_quit(user);
	return TRUE;
}

static int number(const char *text, int low, int high)
{
	char *end;
	errno = 0;
	gint64 value = g_ascii_strtoll(text, &end, 10);
	return errno || end == text || *end || value < low || value > high ? -1 : (int)value;
}

gboolean gtx_register_sm1_decoder(guint width, guint height);

int main(int argc, char **argv)
{
	if (argc != 7 || strncmp(argv[1], "1:", 2))
		return 2;
	int width = number(argv[3], 64, 1920), height = number(argv[4], 64, 1080);
	int rate = number(argv[5], 1000, 30000), bitrate = number(argv[6], 64000, 20000000);
	gboolean hevc = !strcmp(argv[2], "h265");
	if (width < 0 || height < 0 || rate < 0 || bitrate < 0 || width % 16 || height % 2 || (!hevc && strcmp(argv[2], "h264")))
		return 2;
	/* The Wave4 library resets the shared encoder on initialization. Keep
	 * the lock until process exit, including GStreamer teardown. */
	if (hevc) {
		int lease = open("/run/gtx-wave4.lock", O_RDWR | O_CREAT | O_CLOEXEC | O_NOFOLLOW, 0600);
		if (lease < 0 || flock(lease, LOCK_EX | LOCK_NB) < 0) {
			g_printerr("gtx-transcode: HEVC encoder unavailable\n");
			return 1;
		}
	}
	int fd = fcntl(STDOUT_FILENO, F_DUPFD_CLOEXEC, 3);
	if (fd < 0 || dup2(STDERR_FILENO, STDOUT_FILENO) < 0)
		return 1;
	gst_init(NULL, NULL);
	gtx_register_sm1_decoder(width, height);
	gst_element_register(NULL, "gtxvenc", GST_RANK_NONE, gtx_encoder_get_type());
	char *desc = g_strdup_printf(
		"souphttpsrc name=source ! tsdemux name=d "
		"d. ! video/x-h264;video/x-h265;video/mpeg ! queue ! decodebin ! videorate ! "
		"video/x-raw,framerate=%d/1000 ! videoconvertscale n-threads=2 ! "
		"video/x-raw,format=NV21,width=%d,height=%d,framerate=%d/1000,pixel-aspect-ratio=1/1 ! "
		"gtxvenc bitrate=%d hevc=%s ! %sparse config-interval=-1 ! queue ! mux. "
		"d. ! audio/mpeg;audio/x-ac3;audio/x-eac3;audio/x-dts;audio/x-ac4 ! queue ! decodebin ! "
		"audioconvert ! audioresample ! audio/x-raw,channels=2,rate=48000 ! avenc_aac bitrate=96000 ! aacparse ! queue ! mux. "
		"mpegtsmux name=mux alignment=7 ! fdsink name=output sync=false", rate, width, height, rate, bitrate,
		hevc ? "true" : "false", hevc ? "h265" : "h264");
	GError *error = NULL;
	GstElement *pipeline = gst_parse_launch(desc, &error);
	g_free(desc);
	if (error || !pipeline) {
		g_printerr("gtx-transcode: %s\n", error ? error->message : "invalid pipeline");
		g_clear_error(&error);
		if (pipeline) gst_object_unref(pipeline);
		close(fd);
		return 1;
	}
	char *ref = g_uri_escape_string(argv[1], ":", FALSE);
	char *uri = g_strdup_printf("http://127.0.0.1:8001/%s", ref);
	GstElement *source = gst_bin_get_by_name(GST_BIN(pipeline), "source");
	GstElement *output = gst_bin_get_by_name(GST_BIN(pipeline), "output");
	g_object_set(source, "location", uri, NULL);
	g_object_set(output, "fd", fd, NULL);
	gst_object_unref(source);
	gst_object_unref(output);
	g_free(uri);
	g_free(ref);
	Stream stream = {g_main_loop_new(NULL, FALSE), FALSE};
	GstBus *bus = gst_element_get_bus(pipeline);
	guint busWatch = gst_bus_add_watch(bus, onMessage, &stream);
	guint inputWatch = g_unix_fd_add(STDIN_FILENO, G_IO_HUP | G_IO_ERR | G_IO_NVAL, onDisconnect, stream.loop);
	gst_object_unref(bus);
	if (gst_element_set_state(pipeline, GST_STATE_PLAYING) == GST_STATE_CHANGE_FAILURE)
		stream.failed = TRUE;
	else
		g_main_loop_run(stream.loop);
	gst_element_set_state(pipeline, GST_STATE_NULL);
	g_source_remove(inputWatch);
	g_source_remove(busWatch);
	g_main_loop_unref(stream.loop);
	gst_object_unref(pipeline);
	close(fd);
	return stream.failed ? 1 : 0;
}
