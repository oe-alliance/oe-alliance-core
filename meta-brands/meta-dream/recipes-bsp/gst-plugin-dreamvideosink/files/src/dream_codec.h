/* SPDX-License-Identifier: LGPL-2.1-or-later */
#ifndef DREAM_CODEC_H
#define DREAM_CODEC_H

#include <gst/gst.h>

G_BEGIN_DECLS

#define DREAM_CODEC_MAX_FRAME_SIZE ((gsize) 32 * 1024 * 1024)
#define DREAM_CODEC_MAX_HEADER_SIZE ((gsize) 1024 * 1024)
#define DREAM_VIDEO_CAPS \
	"video/x-h264, parsed=(boolean)true, alignment=(string)au, " \
	"stream-format=(string){ byte-stream, avc, avc3 }, width=(int)[1,4096], height=(int)[1,4096]; " \
	"video/x-h265, parsed=(boolean)true, alignment=(string)au, " \
	"stream-format=(string){ byte-stream, hvc1, hev1 }, width=(int)[1,4096], height=(int)[1,4096]; " \
	"video/mpeg, mpegversion=(int){ 1, 2, 4 }, systemstream=(boolean)false, " \
	"parsed=(boolean)true, width=(int)[1,4096], height=(int)[1,4096]; " \
	"video/x-divx, divxversion=(int)[3,6]; video/x-xvid; " \
	"video/x-msmpeg, msmpegversion=(int)43; video/x-vp9; video/x-cavs; " \
	"image/jpeg; video/x-jpeg; video/x-pn-realvideo, rmversion=(int){3,4}; " \
	"video/x-h263, variant=(string)itu, parsed=(boolean)true; video/x-flash-video, flvversion=(int)1; " \
	"video/x-wmv, wmvversion=(int)3, format=(string){WMV3,WVC3,WVC1}"

typedef enum {
	DREAM_CODEC_NONE,
	DREAM_CODEC_H264,
	DREAM_CODEC_H265,
	DREAM_CODEC_MPEG,
	DREAM_CODEC_DIVX3,
	DREAM_CODEC_VP9,
	DREAM_CODEC_CAVS,
	DREAM_CODEC_JPEG,
	DREAM_CODEC_REAL,
	DREAM_CODEC_WMV3,
	DREAM_CODEC_VC1,
	DREAM_CODEC_H263,
	DREAM_CODEC_SORENSON
} DreamCodecKind;

typedef enum {
	DREAM_CODEC_ERROR_CAPS,
	DREAM_CODEC_ERROR_DATA,
	DREAM_CODEC_ERROR_LIMIT,
	DREAM_CODEC_ERROR_MEMORY
} DreamCodecError;

#define DREAM_CODEC_ERROR (dream_codec_error_quark())

typedef struct {
	DreamCodecKind kind;
	guint32 format;
	guint32 vformat;
	guint32 streamType;
	guint32 width;
	guint32 height;
	/* Decoder frame duration in 96 kHz units, NOT the 90 kHz PTS clock. */
	guint32 rate;
	guint nalLengthSize;
	GstBuffer *headers;
	struct DreamH263 *h263;
	struct DreamVc1 *vc1;
} DreamCodec;

GQuark dream_codec_error_quark(void);
void dream_codec_init(DreamCodec *codec);
void dream_codec_clear(DreamCodec *codec);
void dream_codec_flush(DreamCodec *codec);

/* Initialize before use. Failed configuration leaves the previous state intact. */
gboolean dream_codec_configure(DreamCodec *codec, GstCaps *caps, GError **error);

/* Returns an owned reference; never consumes input. Empty buffers stay empty.
 * Unchanged elementary buffers are referenced without copying. Caller must
 * serialize transform/flush/configure/clear: legacy codecs retain picture state.
 * Header retry after flush belongs to sink.
 */
GstBuffer *dream_codec_transform(const DreamCodec *codec, GstBuffer *input,
	gboolean prependHeaders, GError **error);

/* Streaming wrapper. Successful pushes consume the header retry even when
 * output is NULL (VC-1 lookahead). Input is never consumed. Outputs are owned
 * and already framed; drain once at EOS before draining the decoder. Flush
 * discards any delayed frame. All calls require the same serialization above.
 */
gboolean dream_codec_push(DreamCodec *codec, GstBuffer *input,
	gboolean prependHeaders, GstBuffer **output, GError **error);
gboolean dream_codec_drain(DreamCodec *codec, GstBuffer **output, GError **error);

G_END_DECLS
#endif
