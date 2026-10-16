/* SPDX-License-Identifier: LGPL-2.1-or-later */
#include "dream_codec.h"
#include "dream_h263.h"

#ifndef GST_USE_UNSTABLE_API
#define GST_USE_UNSTABLE_API
#endif
#include <gst/codecparsers/gstvc1parser.h>
#include <string.h>

typedef struct DreamVc1 {
	GstVC1SeqHdr sequence;
	gboolean haveSequence;
	guint fpsNumerator;
	guint fpsDenominator;
	GstBuffer *pending;
	gint pendingType;
	guint64 pendingIndex;
	guint64 nextIndex;
	GstClockTime anchor;
	guint64 anchorIndex;
} DreamVc1;

static void vc1_reset(DreamVc1 *state)
{
	if (state->pending)
		gst_buffer_unref(state->pending);
	state->pending = NULL;
	state->pendingType = -1;
	state->pendingIndex = state->nextIndex = state->anchorIndex = 0;
	state->anchor = GST_CLOCK_TIME_NONE;
}

/* Compressed headers have arbitrary byte alignment. Some GStreamer builds
 * implement their fast endian macros using aligned integer dereferences. */
static guint16 read_be16(const guint8 *data)
{
	return ((guint16) data[0] << 8) | data[1];
}

static void write_be16(guint8 *data, guint16 value)
{
	data[0] = value >> 8;
	data[1] = value;
}

static void write_be32(guint8 *data, guint32 value)
{
	data[0] = value >> 24;
	data[1] = value >> 16;
	data[2] = value >> 8;
	data[3] = value;
}

typedef struct {
	const guint8 *data;
	gsize size;
	gsize position;
	gsize outputSize;
	guint8 *output;
} HeaderReader;

GQuark dream_codec_error_quark(void)
{
	return g_quark_from_static_string("dream-codec-error");
}

static gboolean fail(GError **error, DreamCodecError code, const gchar *message)
{
	g_set_error_literal(error, DREAM_CODEC_ERROR, code, message);
	return FALSE;
}

void dream_codec_init(DreamCodec *codec)
{
	memset(codec, 0, sizeof(*codec));
}

void dream_codec_clear(DreamCodec *codec)
{
	if (codec->headers)
		gst_buffer_unref(codec->headers);
	dream_h263_free(codec->h263);
	if (codec->vc1) {
		vc1_reset(codec->vc1);
		g_free(codec->vc1);
	}
	dream_codec_init(codec);
}

void dream_codec_flush(DreamCodec *codec)
{
	if (codec->h263)
		dream_h263_reset(codec->h263);
	if (codec->vc1)
		vc1_reset(codec->vc1);
}

static GstBuffer *allocate_buffer(gsize size, guint8 **data, GError **error)
{
	GstBuffer *buffer;
	guint8 *memory = g_try_malloc(size);

	if (!memory) {
		fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to allocate normalized video buffer");
		return NULL;
	}
	buffer = gst_buffer_new_wrapped_full(0, memory, size, 0, size, memory, g_free);
	if (!buffer) {
		g_free(memory);
		fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to wrap normalized video buffer");
		return NULL;
	}
	*data = memory;
	return buffer;
}

static gboolean valid_nal(const guint8 *data, gsize size, DreamCodecKind kind)
{
	if (!size || (data[0] & 0x80))
		return FALSE;
	if (kind == DREAM_CODEC_H265)
		return size >= 2 && (data[1] & 7) != 0;
	return (data[0] & 31) != 0;
}

/* Configuration records are walked twice: validate/count, then emit. The cap
 * applies to the resulting Annex-B data, not only to its packed input form.
 */
static gboolean read_header_nal(HeaderReader *reader, DreamCodecKind kind,
	guint expectedType, GError **error)
{
	gsize length;
	guint type;
	const guint8 *data;

	if (reader->size - reader->position < 2)
		return fail(error, DREAM_CODEC_ERROR_DATA, "Truncated codec header NAL length");
	length = read_be16(reader->data + reader->position);
	reader->position += 2;
	if (length > reader->size - reader->position)
		return fail(error, DREAM_CODEC_ERROR_DATA, "Truncated codec header NAL payload");
	data = reader->data + reader->position;
	if (!valid_nal(data, length, kind))
		return fail(error, DREAM_CODEC_ERROR_DATA, "Invalid codec header NAL unit");
	type = kind == DREAM_CODEC_H264 ? (data[0] & 31) : ((data[0] >> 1) & 63);
	if (type != expectedType)
		return fail(error, DREAM_CODEC_ERROR_DATA, "Codec header array and NAL types disagree");
	if (length + 4 > DREAM_CODEC_MAX_HEADER_SIZE - reader->outputSize)
		return fail(error, DREAM_CODEC_ERROR_LIMIT, "Normalized codec headers exceed 1 MiB");
	if (reader->output) {
		write_be32(reader->output + reader->outputSize, 1);
		memcpy(reader->output + reader->outputSize + 4, data, length);
	}
	reader->outputSize += length + 4;
	reader->position += length;
	return TRUE;
}

static gboolean avc_extended_profile(guint profile)
{
	switch (profile) {
	case 100: case 110: case 122: case 144: case 44: case 83: case 86:
	case 118: case 128: case 138: case 139: case 134: case 135:
		return TRUE;
	default:
		return FALSE;
	}
}

static gboolean parse_avcc(HeaderReader *reader, gboolean inband,
	guint *nalLengthSize, GError **error)
{
	guint count, index, spsCount, ppsCount;

	if (reader->size < 7 || reader->data[0] != 1)
		return fail(error, DREAM_CODEC_ERROR_DATA, "Invalid AVC configuration record");
	if ((reader->data[4] & 0xfc) != 0xfc || (reader->data[5] & 0xe0) != 0xe0)
		return fail(error, DREAM_CODEC_ERROR_DATA, "Invalid AVC configuration reserved bits");
	*nalLengthSize = (reader->data[4] & 3) + 1;
	if (*nalLengthSize == 3)
		return fail(error, DREAM_CODEC_ERROR_DATA, "Reserved AVC NAL length size");
	spsCount = reader->data[5] & 31;
	reader->position = 6;
	for (index = 0; index < spsCount; index++)
		if (!read_header_nal(reader, DREAM_CODEC_H264, 7, error))
			return FALSE;
	if (reader->position == reader->size)
		return fail(error, DREAM_CODEC_ERROR_DATA, "Missing AVC PPS array count");
	ppsCount = reader->data[reader->position++];
	for (index = 0; index < ppsCount; index++)
		if (!read_header_nal(reader, DREAM_CODEC_H264, 8, error))
			return FALSE;
	if (!inband && (!spsCount || !ppsCount))
		return fail(error, DREAM_CODEC_ERROR_DATA, "AVC requires SPS and PPS in codec_data");
	if (reader->position < reader->size) {
		if (!avc_extended_profile(reader->data[1]) || reader->size - reader->position < 4)
			return fail(error, DREAM_CODEC_ERROR_DATA, "Unexpected AVC configuration tail");
		if ((reader->data[reader->position] & 0xfc) != 0xfc ||
			(reader->data[reader->position + 1] & 0xf8) != 0xf8 ||
			(reader->data[reader->position + 2] & 0xf8) != 0xf8)
			return fail(error, DREAM_CODEC_ERROR_DATA, "Invalid AVC extension reserved bits");
		count = reader->data[reader->position + 3];
		reader->position += 4;
		for (index = 0; index < count; index++)
			if (!read_header_nal(reader, DREAM_CODEC_H264, 13, error))
				return FALSE;
	}
	if (reader->position != reader->size)
		return fail(error, DREAM_CODEC_ERROR_DATA, "Trailing bytes in AVC configuration");
	return TRUE;
}

static gboolean parse_hvcc(HeaderReader *reader, gboolean inband,
	guint *nalLengthSize, GError **error)
{
	guint arrays, array, index, count, type, parameterSets = 0;

	if (reader->size < 23 || reader->data[0] != 1)
		return fail(error, DREAM_CODEC_ERROR_DATA, "Invalid HEVC configuration record");
	if ((reader->data[13] & 0xf0) != 0xf0 || (reader->data[15] & 0xfc) != 0xfc ||
		(reader->data[16] & 0xfc) != 0xfc || (reader->data[17] & 0xf8) != 0xf8 ||
		(reader->data[18] & 0xf8) != 0xf8)
		return fail(error, DREAM_CODEC_ERROR_DATA, "Invalid HEVC configuration reserved bits");
	*nalLengthSize = (reader->data[21] & 3) + 1;
	arrays = reader->data[22];
	reader->position = 23;
	for (array = 0; array < arrays; array++) {
		if (reader->size - reader->position < 3)
			return fail(error, DREAM_CODEC_ERROR_DATA, "Truncated HEVC configuration array");
		if (reader->data[reader->position] & 0x40)
			return fail(error, DREAM_CODEC_ERROR_DATA, "Invalid HEVC array reserved bit");
		type = reader->data[reader->position] & 63;
		count = read_be16(reader->data + reader->position + 1);
		reader->position += 3;
		for (index = 0; index < count; index++)
			if (!read_header_nal(reader, DREAM_CODEC_H265, type, error))
				return FALSE;
		if (count && type >= 32 && type <= 34)
			parameterSets |= 1U << (type - 32);
	}
	if (!inband && parameterSets != 7)
		return fail(error, DREAM_CODEC_ERROR_DATA, "HVC1 requires VPS, SPS and PPS in codec_data");
	if (reader->position != reader->size)
		return fail(error, DREAM_CODEC_ERROR_DATA, "Trailing bytes in HEVC configuration");
	return TRUE;
}

static gboolean starts_with_startcode(const guint8 *data, gsize size,
	DreamCodecKind kind)
{
	gsize prefix = 0;

	while (prefix < size && data[prefix] == 0)
		prefix++;
	if (prefix < 2 || prefix >= size || data[prefix] != 1 || ++prefix == size)
		return FALSE;
	return kind == DREAM_CODEC_MPEG || kind == DREAM_CODEC_CAVS ||
		valid_nal(data + prefix, size - prefix, kind);
}

static gboolean configure_headers(DreamCodec *codec, GstBuffer *source,
	gboolean packed, gboolean inband, GError **error)
{
	GstMapInfo map = GST_MAP_INFO_INIT;
	HeaderReader reader = { 0 };
	gboolean success;
	guint8 *output;

	if (!source)
		return fail(error, DREAM_CODEC_ERROR_DATA, "codec_data contains no buffer");
	if (!gst_buffer_get_size(source) || gst_buffer_get_size(source) > DREAM_CODEC_MAX_HEADER_SIZE)
		return fail(error, DREAM_CODEC_ERROR_LIMIT, "codec_data is empty or exceeds 1 MiB");
	if (!gst_buffer_map(source, &map, GST_MAP_READ))
		return fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to map codec_data");
	if (!packed) {
		if (codec->kind == DREAM_CODEC_VC1 && map.size < 5) {
			gst_buffer_unmap(source, &map);
			return fail(error, DREAM_CODEC_ERROR_DATA, "Truncated VC-1 configuration record");
		}
		success = codec->kind != DREAM_CODEC_H264 && codec->kind != DREAM_CODEC_H265 &&
			codec->kind != DREAM_CODEC_MPEG;
		if (!success)
			success = starts_with_startcode(map.data, map.size, codec->kind);
		if (success)
			codec->headers = codec->kind == DREAM_CODEC_VC1 ?
				gst_buffer_copy_region(source, GST_BUFFER_COPY_MEMORY, 1, map.size - 1) :
				gst_buffer_ref(source);
		else
			fail(error, DREAM_CODEC_ERROR_DATA, "Elementary codec_data needs a start code");
		gst_buffer_unmap(source, &map);
		if (success && !codec->headers)
			return fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to retain codec_data");
		return success;
	}
	reader.data = map.data;
	reader.size = map.size;
	if (codec->kind == DREAM_CODEC_H264)
		success = parse_avcc(&reader, inband, &codec->nalLengthSize, error);
	else
		success = parse_hvcc(&reader, inband, &codec->nalLengthSize, error);
	if (success && reader.outputSize) {
		codec->headers = allocate_buffer(reader.outputSize, &output, error);
		success = codec->headers != NULL;
		if (success) {
			reader.position = 0;
			reader.outputSize = 0;
			reader.output = output;
			if (codec->kind == DREAM_CODEC_H264)
				success = parse_avcc(&reader, inband, &codec->nalLengthSize, error);
			else
				success = parse_hvcc(&reader, inband, &codec->nalLengthSize, error);
		}
	}
	gst_buffer_unmap(source, &map);
	return success;
}

gboolean dream_codec_configure(DreamCodec *codec, GstCaps *caps, GError **error)
{
	DreamCodec next;
	const GstStructure *structure;
	const GValue *value;
	const gchar *name, *streamFormat, *alignment;
	gint width = 1920, height = 1080, numerator = 0, denominator = 1, version;
	gboolean packed = FALSE, inband = FALSE, systemStream, parsed;
	guint64 rate;

	dream_codec_init(&next);
	if (!caps || !gst_caps_is_fixed(caps) || gst_caps_get_size(caps) != 1)
		return fail(error, DREAM_CODEC_ERROR_CAPS, "A single fixed video caps structure is required");
	structure = gst_caps_get_structure(caps, 0);
	name = gst_structure_get_name(structure);
	/* The original decoder uses 1920x1080 until dimensions become available.
	 * Missing fields are allowed, but an explicitly invalid value is not.
	 */
	if ((gst_structure_has_field(structure, "width") &&
		!gst_structure_get_int(structure, "width", &width)) || width < 1 || width > 4096 ||
		(gst_structure_has_field(structure, "height") &&
		!gst_structure_get_int(structure, "height", &height)) || height < 1 || height > 4096)
		return fail(error, DREAM_CODEC_ERROR_CAPS, "Video dimensions must be within 1..4096");
	next.width = width;
	next.height = height;
	if (gst_structure_has_field(structure, "framerate")) {
		if (!gst_structure_get_fraction(structure, "framerate", &numerator, &denominator) ||
			numerator < 0 || denominator <= 0)
			return fail(error, DREAM_CODEC_ERROR_CAPS, "Invalid video framerate");
		if (numerator) {
			rate = gst_util_uint64_scale(96000, denominator, numerator);
			if (!rate || rate > G_MAXUINT32)
				return fail(error, DREAM_CODEC_ERROR_CAPS, "Video framerate is outside decoder range");
			next.rate = rate;
		}
	}
	if (g_str_equal(name, "video/x-h264") || g_str_equal(name, "video/x-h265")) {
		next.kind = g_str_equal(name, "video/x-h264") ? DREAM_CODEC_H264 : DREAM_CODEC_H265;
		next.format = next.kind == DREAM_CODEC_H264 ? 4 : 15;
		next.vformat = next.kind == DREAM_CODEC_H264 ? 2 : 11;
		next.streamType = next.kind == DREAM_CODEC_H264 ? 1 : 22;
		alignment = gst_structure_get_string(structure, "alignment");
		streamFormat = gst_structure_get_string(structure, "stream-format");
		if (!alignment || !g_str_equal(alignment, "au") || !streamFormat)
			return fail(error, DREAM_CODEC_ERROR_CAPS, "NAL video requires AU alignment and stream-format");
		if (!g_str_equal(streamFormat, "byte-stream")) {
			if (next.kind == DREAM_CODEC_H264) {
				inband = g_str_equal(streamFormat, "avc3");
				packed = inband || g_str_equal(streamFormat, "avc");
			} else {
				inband = g_str_equal(streamFormat, "hev1");
				packed = inband || g_str_equal(streamFormat, "hvc1");
			}
			if (!packed)
				return fail(error, DREAM_CODEC_ERROR_CAPS, "Unsupported NAL stream-format");
		}
	} else if (g_str_equal(name, "video/mpeg")) {
		if (!gst_structure_get_int(structure, "mpegversion", &version) ||
			(version != 1 && version != 2 && version != 4) ||
			!gst_structure_get_boolean(structure, "systemstream", &systemStream) || systemStream ||
			!gst_structure_get_boolean(structure, "parsed", &parsed) || !parsed)
			return fail(error, DREAM_CODEC_ERROR_CAPS, "Only parsed MPEG-1/2/4 elementary video is supported");
		next.kind = DREAM_CODEC_MPEG;
		next.format = version == 4 ? 3 : 0;
		next.vformat = version == 4 ? 1 : 0;
		next.streamType = version == 4 ? 4 : 0;
	} else if (g_str_equal(name, "video/x-divx") || g_str_equal(name, "video/x-msmpeg") ||
		g_str_equal(name, "video/x-xvid")) {
		version = 5;
		if (g_str_equal(name, "video/x-divx") &&
			(!gst_structure_get_int(structure, "divxversion", &version) || version < 3 || version > 6))
			return fail(error, DREAM_CODEC_ERROR_CAPS, "DivX version must be within 3..6");
		if (g_str_equal(name, "video/x-msmpeg")) {
			if (!gst_structure_get_int(structure, "msmpegversion", &version) || version != 43)
				return fail(error, DREAM_CODEC_ERROR_CAPS, "Only Microsoft MPEG-4 version 43 is supported");
			version = 3;
		}
		next.kind = version == 3 ? DREAM_CODEC_DIVX3 : DREAM_CODEC_MPEG;
		next.format = version == 3 ? 1 : (version == 4 ? 2 : 3);
		next.vformat = 1;
		next.streamType = 4;
		if (version == 3 && (width > 4095 || height > 4095))
			return fail(error, DREAM_CODEC_ERROR_CAPS, "DivX 3 dimensions must fit the 12-bit decoder header");
	} else if (g_str_equal(name, "video/x-vp9")) {
		next.kind = DREAM_CODEC_VP9;
		next.format = 16;
		next.vformat = 14;
		next.streamType = 23;
	} else if (g_str_equal(name, "video/x-h263") || g_str_equal(name, "video/x-flash-video")) {
		next.kind = g_str_equal(name, "video/x-h263") ? DREAM_CODEC_H263 : DREAM_CODEC_SORENSON;
		/* Some containers report display dimensions instead of the coded size.
		 * h263parse supplies the coded dimensions before decoder setup.
		 */
		if (next.kind == DREAM_CODEC_H263 &&
			(!gst_structure_get_boolean(structure, "parsed", &parsed) || !parsed ||
			g_strcmp0(gst_structure_get_string(structure, "variant"), "itu")))
			return fail(error, DREAM_CODEC_ERROR_CAPS, "H.263 requires parsed ITU picture buffers");
		if (next.kind == DREAM_CODEC_SORENSON && gst_structure_has_field(structure, "flvversion") &&
			(!gst_structure_get_int(structure, "flvversion", &version) || version != 1))
			return fail(error, DREAM_CODEC_ERROR_CAPS, "Only Sorenson Spark Flash video is supported");
		next.format = 7;
		next.vformat = 1;
		next.streamType = 4;
	} else if (g_str_equal(name, "video/x-cavs")) {
		next.kind = DREAM_CODEC_CAVS;
		next.format = 13;
		next.vformat = 7;
		next.streamType = 16;
	} else if (g_str_equal(name, "image/jpeg") || g_str_equal(name, "video/x-jpeg")) {
		next.kind = DREAM_CODEC_JPEG;
		next.format = 5;
		next.vformat = 3;
		next.streamType = 5;
	} else if (g_str_equal(name, "video/x-pn-realvideo")) {
		if (!gst_structure_get_int(structure, "rmversion", &version) || (version != 3 && version != 4))
			return fail(error, DREAM_CODEC_ERROR_CAPS, "Only RealVideo 3 and 4 are supported");
		next.kind = DREAM_CODEC_REAL;
		next.format = version == 3 ? 8 : 9;
		next.vformat = 4;
		next.streamType = 7;
	} else if (g_str_equal(name, "video/x-wmv")) {
		streamFormat = gst_structure_get_string(structure, "format");
		if (!gst_structure_get_int(structure, "wmvversion", &version) || version != 3 || !streamFormat ||
			(!g_str_equal(streamFormat, "WMV3") && !g_str_equal(streamFormat, "WVC3") &&
			!g_str_equal(streamFormat, "WVC1")))
			return fail(error, DREAM_CODEC_ERROR_CAPS, "WMV requires WMV3, WVC3 or WVC1 format");
		next.kind = g_str_equal(streamFormat, "WVC1") ? DREAM_CODEC_VC1 : DREAM_CODEC_WMV3;
		next.format = next.kind == DREAM_CODEC_VC1 ? 11 : 10;
		next.vformat = 6;
		next.streamType = 3;
	} else {
		return fail(error, DREAM_CODEC_ERROR_CAPS, "Unsupported video codec");
	}
	value = gst_structure_get_value(structure, "codec_data");
	if (value) {
		if (!GST_VALUE_HOLDS_BUFFER(value))
			return fail(error, DREAM_CODEC_ERROR_CAPS, "codec_data must be a GstBuffer");
		if (!configure_headers(&next, gst_value_get_buffer(value), packed, inband, error)) {
			dream_codec_clear(&next);
			return FALSE;
		}
	} else if (packed) {
		return fail(error, DREAM_CODEC_ERROR_CAPS, "Length-prefixed video requires codec_data");
	}
	if (next.kind == DREAM_CODEC_WMV3 && (!next.headers || gst_buffer_get_size(next.headers) < 4)) {
		dream_codec_clear(&next);
		return fail(error, DREAM_CODEC_ERROR_CAPS, "WMV/VC-1 requires complete codec_data");
	}
	if (next.kind == DREAM_CODEC_H263 || next.kind == DREAM_CODEC_SORENSON) {
		next.h263 = dream_h263_new();
		if (!next.h263) {
			dream_codec_clear(&next);
			return fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to allocate H.263 converter state");
		}
	}
	if (next.kind == DREAM_CODEC_VC1) {
		next.vc1 = g_try_new0(DreamVc1, 1);
		if (!next.vc1) {
			dream_codec_clear(&next);
			return fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to allocate VC-1 timing state");
		}
		next.vc1->fpsNumerator = numerator;
		next.vc1->fpsDenominator = denominator;
		vc1_reset(next.vc1);
	}
	dream_codec_clear(codec);
	*codec = next;
	return TRUE;
}

static gboolean measure_nals(const DreamCodec *codec, const GstMapInfo *input,
	gsize *size, GError **error)
{
	gsize position = 0, length, total = 0;
	guint index;

	while (position < input->size) {
		if (input->size - position < codec->nalLengthSize)
			return fail(error, DREAM_CODEC_ERROR_DATA, "Truncated video NAL length");
		length = 0;
		for (index = 0; index < codec->nalLengthSize; index++)
			length = (length << 8) | input->data[position++];
		if (length > input->size - position)
			return fail(error, DREAM_CODEC_ERROR_DATA, "Truncated video NAL payload");
		if (!valid_nal(input->data + position, length, codec->kind))
			return fail(error, DREAM_CODEC_ERROR_DATA, "Invalid or empty video NAL unit");
		if (length + 4 > DREAM_CODEC_MAX_FRAME_SIZE - total)
			return fail(error, DREAM_CODEC_ERROR_LIMIT, "Normalized access unit exceeds 32 MiB");
		total += length + 4;
		position += length;
	}
	*size = total;
	return TRUE;
}


/* Default baseline JPEG Huffman tables (ITU-T T.81, Annex K), prefixed with
 * SOI/DHT as required by the Dream MJPEG decoder initialization sequence.
 */
static const guint8 jpeg_decoder_header[] = {
	0xff, 0xd8, 0xff, 0xc4, 0x01, 0xa2, 0x00, 0x00, 0x01, 0x05, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
	0x09, 0x0a, 0x0b, 0x01, 0x00, 0x03, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b,
	0x10, 0x00, 0x02, 0x01, 0x03, 0x03, 0x02, 0x04, 0x03, 0x05, 0x05, 0x04, 0x04, 0x00, 0x00, 0x01,
	0x7d, 0x01, 0x02, 0x03, 0x00, 0x04, 0x11, 0x05, 0x12, 0x21, 0x31, 0x41, 0x06, 0x13, 0x51, 0x61,
	0x07, 0x22, 0x71, 0x14, 0x32, 0x81, 0x91, 0xa1, 0x08, 0x23, 0x42, 0xb1, 0xc1, 0x15, 0x52, 0xd1,
	0xf0, 0x24, 0x33, 0x62, 0x72, 0x82, 0x09, 0x0a, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x25, 0x26, 0x27,
	0x28, 0x29, 0x2a, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3a, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48,
	0x49, 0x4a, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5a, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68,
	0x69, 0x6a, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7a, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88,
	0x89, 0x8a, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6,
	0xa7, 0xa8, 0xa9, 0xaa, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xc2, 0xc3, 0xc4,
	0xc5, 0xc6, 0xc7, 0xc8, 0xc9, 0xca, 0xd2, 0xd3, 0xd4, 0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xe1,
	0xe2, 0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9, 0xea, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7,
	0xf8, 0xf9, 0xfa, 0x11, 0x00, 0x02, 0x01, 0x02, 0x04, 0x04, 0x03, 0x04, 0x07, 0x05, 0x04, 0x04,
	0x00, 0x01, 0x02, 0x77, 0x00, 0x01, 0x02, 0x03, 0x11, 0x04, 0x05, 0x21, 0x31, 0x06, 0x12, 0x41,
	0x51, 0x07, 0x61, 0x71, 0x13, 0x22, 0x32, 0x81, 0x08, 0x14, 0x42, 0x91, 0xa1, 0xb1, 0xc1, 0x09,
	0x23, 0x33, 0x52, 0xf0, 0x15, 0x62, 0x72, 0xd1, 0x0a, 0x16, 0x24, 0x34, 0xe1, 0x25, 0xf1, 0x17,
	0x18, 0x19, 0x1a, 0x26, 0x27, 0x28, 0x29, 0x2a, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3a, 0x43, 0x44,
	0x45, 0x46, 0x47, 0x48, 0x49, 0x4a, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5a, 0x63, 0x64,
	0x65, 0x66, 0x67, 0x68, 0x69, 0x6a, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7a, 0x82, 0x83,
	0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a,
	0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xa9, 0xaa, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8,
	0xb9, 0xba, 0xc2, 0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8, 0xc9, 0xca, 0xd2, 0xd3, 0xd4, 0xd5, 0xd6,
	0xd7, 0xd8, 0xd9, 0xda, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9, 0xea, 0xf2, 0xf3, 0xf4,
	0xf5, 0xf6, 0xf7, 0xf8, 0xf9, 0xfa,
};

static gboolean raw_codec(DreamCodecKind kind)
{
	return kind == DREAM_CODEC_REAL || kind == DREAM_CODEC_WMV3 ||
		kind == DREAM_CODEC_VC1 || kind == DREAM_CODEC_JPEG ||
		kind == DREAM_CODEC_DIVX3 || kind == DREAM_CODEC_VP9;
}

static GstBuffer *wrap_payload(GstBuffer *input, gsize prefixSize,
	guint8 **data, GError **error)
{
	gsize size = gst_buffer_get_size(input);
	GstBuffer *output;

	if (prefixSize > DREAM_CODEC_MAX_FRAME_SIZE - size) {
		fail(error, DREAM_CODEC_ERROR_LIMIT, "Framed access unit exceeds 32 MiB");
		return NULL;
	}
	output = allocate_buffer(size + prefixSize, data, error);
	if (!output)
		return NULL;
	if (gst_buffer_extract(input, 0, *data + prefixSize, size) != size ||
		!gst_buffer_copy_into(output, input, GST_BUFFER_COPY_METADATA, 0, -1)) {
		gst_buffer_unref(output);
		fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to copy compressed payload and metadata");
		return NULL;
	}
	return output;
}

static GstBuffer *transform_vp9(GstBuffer *input, GError **error)
{
	GstMapInfo map = GST_MAP_INFO_INIT;
	GstBuffer *output = NULL;
	gsize sizes[8], count = 1, indexSize = 0, payloadSize, total, position, index, byte;
	guint marker, lengthBytes;
	guint8 *data;

	if (!gst_buffer_map(input, &map, GST_MAP_READ)) {
		fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to map VP9 access unit");
		return NULL;
	}
	marker = map.data[map.size - 1];
	sizes[0] = map.size;
	if ((marker & 0xe0) == 0xc0) {
		count = (marker & 7) + 1;
		lengthBytes = ((marker >> 3) & 3) + 1;
		indexSize = 2 + count * lengthBytes;
		if (indexSize > map.size || map.data[map.size - indexSize] != marker) {
			fail(error, DREAM_CODEC_ERROR_DATA, "Invalid VP9 superframe index");
			goto done;
		}
		position = map.size - indexSize + 1;
		total = 0;
		for (index = 0; index < count; index++) {
			sizes[index] = 0;
			for (byte = 0; byte < lengthBytes; byte++)
				sizes[index] |= ((gsize) map.data[position++]) << (8 * byte);
			if (!sizes[index] || sizes[index] > map.size - indexSize - total) {
				fail(error, DREAM_CODEC_ERROR_DATA, "Invalid VP9 superframe size");
				goto done;
			}
			total += sizes[index];
		}
		if (total != map.size - indexSize) {
			fail(error, DREAM_CODEC_ERROR_DATA, "VP9 superframe does not cover its payload");
			goto done;
		}
	}
	payloadSize = map.size - indexSize;
	if (count * 16 > DREAM_CODEC_MAX_FRAME_SIZE - payloadSize) {
		fail(error, DREAM_CODEC_ERROR_LIMIT, "VP9 AMLV access unit exceeds 32 MiB");
		goto done;
	}
	output = allocate_buffer(payloadSize + count * 16, &data, error);
	if (!output)
		goto done;
	position = 0;
	total = 0;
	for (index = 0; index < count; index++) {
		write_be32(data + total, sizes[index] + 4);
		write_be32(data + total + 4, ~((guint32) sizes[index] + 4));
		write_be32(data + total + 8, 1);
		memcpy(data + total + 12, "AMLV", 4);
		memcpy(data + total + 16, map.data + position, sizes[index]);
		total += sizes[index] + 16;
		position += sizes[index];
	}
	if (!gst_buffer_copy_into(output, input, GST_BUFFER_COPY_METADATA, 0, -1)) {
		gst_buffer_unref(output);
		output = NULL;
		fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to preserve VP9 metadata");
	}
done:
	gst_buffer_unmap(input, &map);
	return output;
}

/* Dream WMV3 packets contain 24-bit lengths separated by 0x88 escape bytes,
 * followed by two copies of the checksum over the escaped length block.
 */
static void wmv_packet_header(guint8 *data, guint8 type, gsize length)
{
	guint index, checksum = 0;
	static const guint8 fixed[] = {
		0, 0, 1, 0, 0, 0, 0x88, 0, 0, 0x88, 0xff, 0xff, 0x88, 0xff, 0xff, 0x88
	};

	memcpy(data, fixed, sizeof(fixed));
	data[3] = type;
	data[5] = length >> 16;
	data[7] = length >> 8;
	data[8] = length;
	for (index = 4; index < 16; index++)
		checksum += data[index];
	data[16] = data[19] = checksum >> 8;
	data[17] = data[20] = checksum;
	data[18] = data[21] = 0x88;
}

static GstBuffer *transform_raw(const DreamCodec *codec, GstBuffer *input,
	gboolean prependHeaders, GError **error)
{
	static const guint8 divxFrame[] = { 0, 0, 0, 1, 0xb6, 'D', 'I', 'V', 'X', '3', '.', '1', '1' };
	gsize size = gst_buffer_get_size(input);
	gsize headerSize = prependHeaders && codec->headers ? gst_buffer_get_size(codec->headers) : 0;
	gsize prefixSize = headerSize;
	guint8 first[4], *data;
	GstBuffer *output;
	gboolean vc1FramePrefix = FALSE;

	if (codec->kind == DREAM_CODEC_VP9)
		return transform_vp9(input, error);
	if (codec->kind == DREAM_CODEC_DIVX3)
		prefixSize = (prependHeaders ? 10 : 0) + 17;
	else if (codec->kind == DREAM_CODEC_WMV3) {
		headerSize = gst_buffer_get_size(codec->headers);
		if (size > 0xffffff || headerSize + 4 > 0xffffff) {
			fail(error, DREAM_CODEC_ERROR_LIMIT, "WMV3 packet exceeds its 24-bit length field");
			return NULL;
		}
		prefixSize = headerSize + 48;
	} else if (codec->kind == DREAM_CODEC_JPEG) {
		if (size < 4 || gst_buffer_extract(input, 0, first, 2) != 2 ||
			first[0] != 0xff || first[1] != 0xd8) {
			fail(error, DREAM_CODEC_ERROR_DATA, "JPEG frame is missing its SOI marker");
			return NULL;
		}
		prefixSize = prependHeaders ? sizeof(jpeg_decoder_header) : 0;
	} else if (codec->kind == DREAM_CODEC_VC1) {
		vc1FramePrefix = !(size >= 4 && gst_buffer_extract(input, 0, first, 4) == 4 &&
			first[0] == 0 && first[1] == 0 && first[2] == 1);
		if (vc1FramePrefix && prependHeaders && !codec->headers) {
			fail(error, DREAM_CODEC_ERROR_DATA, "Raw VC-1 frame needs a sequence header");
			return NULL;
		}
		prefixSize += vc1FramePrefix ? 4 : 0;
	}
	if (!prefixSize)
		return gst_buffer_ref(input);
	output = wrap_payload(input, prefixSize, &data, error);
	if (!output)
		return NULL;
	if (codec->kind == DREAM_CODEC_DIVX3) {
		gsize position = 0;
		if (prependHeaders) {
			guint32 dimensions = (codec->width << 12) | codec->height;
			write_be32(data, 1);
			data[4] = 0x20;
			data[5] = dimensions >> 16;
			data[6] = dimensions >> 8;
			data[7] = dimensions;
			data[8] = data[9] = 0;
			position = 10;
		}
		memcpy(data + position, divxFrame, sizeof(divxFrame));
		write_be32(data + position + sizeof(divxFrame), size);
	} else if (codec->kind == DREAM_CODEC_WMV3) {
		/* Codec data is embedded in every sequence packet, including after flush. */
		wmv_packet_header(data, 0x10, headerSize + 4);
		write_be16(data + 22, codec->width);
		write_be16(data + 24, codec->height);
		if (gst_buffer_extract(codec->headers, 0, data + 26, headerSize) != headerSize)
			goto header_error;
		wmv_packet_header(data + 26 + headerSize, 0x0d, size);
	} else if (codec->kind == DREAM_CODEC_JPEG) {
		memcpy(data, jpeg_decoder_header, sizeof(jpeg_decoder_header));
	} else {
		if (headerSize && gst_buffer_extract(codec->headers, 0, data, headerSize) != headerSize)
			goto header_error;
		if (vc1FramePrefix)
			write_be32(data + headerSize, 0x10d);
	}
	return output;
header_error:
	gst_buffer_unref(output);
	fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to read decoder header");
	return NULL;
}


GstBuffer *dream_codec_transform(const DreamCodec *codec, GstBuffer *input,
	gboolean prependHeaders, GError **error)
{
	GstMapInfo source = GST_MAP_INFO_INIT;
	GstBuffer *output = NULL;
	guint8 prefix[8];
	guint8 *destination;
	gsize headerSize, size, position, length, outputPosition;
	guint index;

	if (codec->kind == DREAM_CODEC_NONE || !input) {
		fail(error, DREAM_CODEC_ERROR_CAPS, "Video codec is not configured");
		return NULL;
	}
	size = gst_buffer_get_size(input);
	if (size > DREAM_CODEC_MAX_FRAME_SIZE) {
		fail(error, DREAM_CODEC_ERROR_LIMIT, "Input access unit exceeds 32 MiB");
		return NULL;
	}
	if (!size)
		return gst_buffer_ref(input);
	if (codec->h263)
		return dream_h263_transform(codec->h263, input, codec->kind == DREAM_CODEC_SORENSON, error);
	if (raw_codec(codec->kind))
		return transform_raw(codec, input, prependHeaders, error);
	headerSize = prependHeaders && codec->headers ? gst_buffer_get_size(codec->headers) : 0;
	/* Avoid mapping/coalescing a multi-memory elementary access unit merely to
	 * inspect its start code. Unusually long leading-zero prefixes use the full
	 * validation below; the normal path never copies the compressed payload.
	 */
	if (!headerSize && !codec->nalLengthSize &&
		gst_buffer_extract(input, 0, prefix, MIN(size, sizeof(prefix))) == MIN(size, sizeof(prefix)) &&
		starts_with_startcode(prefix, MIN(size, sizeof(prefix)), codec->kind))
		return gst_buffer_ref(input);
	if (!gst_buffer_map(input, &source, GST_MAP_READ)) {
		fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to map compressed video buffer");
		return NULL;
	}
	if (codec->nalLengthSize) {
		if (!measure_nals(codec, &source, &size, error))
			goto done;
	} else if (!starts_with_startcode(source.data, source.size, codec->kind)) {
		fail(error, DREAM_CODEC_ERROR_DATA, "Elementary access unit needs a start code");
		goto done;
	}
	if (headerSize > DREAM_CODEC_MAX_FRAME_SIZE - size) {
		fail(error, DREAM_CODEC_ERROR_LIMIT, "Access unit plus codec headers exceeds 32 MiB");
		goto done;
	}
	if (!headerSize && !codec->nalLengthSize) {
		output = gst_buffer_ref(input);
		goto done;
	}
	output = allocate_buffer(size + headerSize, &destination, error);
	if (!output)
		goto done;
	if (headerSize && gst_buffer_extract(codec->headers, 0, destination, headerSize) != headerSize) {
		fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to read codec headers");
		goto failed_output;
	}
	if (!codec->nalLengthSize) {
		memcpy(destination + headerSize, source.data, source.size);
	} else {
		position = 0;
		outputPosition = headerSize;
		while (position < source.size) {
			length = 0;
			for (index = 0; index < codec->nalLengthSize; index++)
				length = (length << 8) | source.data[position++];
			write_be32(destination + outputPosition, 1);
			memcpy(destination + outputPosition + 4, source.data + position, length);
			position += length;
			outputPosition += length + 4;
		}
	}
	/* Memory-specific metadata is not valid after Annex-B reformatting. Timing,
	 * flags and transferable metadata (including overlays) retain their meaning.
	 */
	if (!gst_buffer_copy_into(output, input, GST_BUFFER_COPY_METADATA, 0, -1)) {
		fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to preserve video buffer metadata");
		goto failed_output;
	}
	goto done;

failed_output:
	gst_buffer_unref(output);
	output = NULL;
done:
	gst_buffer_unmap(input, &source);
	return output;
}
/* VC-1 timing repair needs only the picture prefix, not macroblock decoding.
 * SMPTE 421M sequence/entry syntax is handled by GStreamer's bounded parser.
 * A fixed scratch buffer covers all sequence HRD buckets and picture pan-scan
 * windows; compressed frame payloads are never duplicated for this inspection.
 */
typedef struct {
	const guint8 *data;
	gsize size;
	guint bit;
	gboolean valid;
} Vc1Bits;

static guint vc1_bits(Vc1Bits *bits, guint count)
{
	guint value = 0;
	if (count > 32 || bits->bit > bits->size * 8 ||
		count > bits->size * 8 - bits->bit) {
		bits->valid = FALSE;
		return 0;
	}
	while (count--) {
		value = (value << 1) | ((bits->data[bits->bit / 8] >> (7 - bits->bit % 8)) & 1);
		bits->bit++;
	}
	return value;
}

static gsize vc1_unescape(const guint8 *data, gsize size, guint8 *output, gsize capacity)
{
	gsize source, destination = 0;
	guint zeros = 0;
	for (source = 0; source < size && destination < capacity; source++) {
		guint8 byte = data[source];
		if (zeros >= 2 && byte == 3 && source + 1 < size && data[source + 1] < 4) {
			zeros = 0;
			continue;
		}
		output[destination++] = byte;
		zeros = byte == 0 ? zeros + 1 : 0;
	}
	return destination;
}

static gint vc1_picture(DreamVc1 *state, const guint8 *data, gsize size, guint *referenceShift)
{
	guint8 scratch[128];
	gsize length = vc1_unescape(data, size, scratch, sizeof(scratch));
	Vc1Bits bits = { scratch, length, 0, TRUE };
	GstVC1AdvancedSeqHdr *sequence = &state->sequence.advanced;
	guint type = 0, repeat = 0, windows, fraction, index;
	*referenceShift = 0;
	if (!state->haveSequence)
		return -1;
	if (sequence->interlace && vc1_bits(&bits, 1) && vc1_bits(&bits, 1))
		return -1; /* Field pairs need separate field-order timing. Keep supplied PTS. */
	while (type < 4 && vc1_bits(&bits, 1))
		type++;
	if (!bits.valid)
		return -1;
	if (type != GST_VC1_PICTURE_TYPE_B)
		return type;
	if (sequence->tfcntrflag)
		vc1_bits(&bits, 8);
	if (sequence->pulldown) {
		if (!sequence->interlace || sequence->psf)
			repeat = vc1_bits(&bits, 2);
		else {
			vc1_bits(&bits, 1);
			repeat = vc1_bits(&bits, 1);
		}
	}
	if (sequence->entrypoint.panscan_flag && vc1_bits(&bits, 1)) {
		windows = sequence->interlace && !sequence->psf ? 2 + repeat : 1 + repeat;
		for (index = 0; index < windows; index++) {
			vc1_bits(&bits, 32);
			vc1_bits(&bits, 32);
		}
	}
	vc1_bits(&bits, 1); /* RNDCTRL */
	if (sequence->interlace)
		vc1_bits(&bits, 1); /* UVSAMP */
	if (sequence->finterpflag)
		vc1_bits(&bits, 1);
	fraction = vc1_bits(&bits, 3);
	if (fraction == 7)
		fraction = 0x70 | vc1_bits(&bits, 4);
	if (!bits.valid)
		return -1;
	/* First B picture fractions 1/2 .. 1/8 identify how many pictures precede
	 * the reference in presentation order. This matches original 0x104dc0.
	 */
	switch (fraction) {
	case 0: *referenceShift = 1; break;
	case 1: *referenceShift = 2; break;
	case 3: *referenceShift = 3; break;
	case 5: *referenceShift = 4; break;
	case 0x72: *referenceShift = 5; break;
	case 0x74: *referenceShift = 6; break;
	case 0x7a: *referenceShift = 7; break;
	case 0x7e: return -1;
	case 0x7f: return GST_VC1_PICTURE_TYPE_BI;
	default: break;
	}
	return type;
}

static gsize vc1_next_startcode(const guint8 *data, gsize size, gsize offset)
{
	while (offset + 3 < size) {
		if (!data[offset] && !data[offset + 1] && data[offset + 2] == 1)
			return offset;
		offset++;
	}
	return size;
}

static void vc1_inspect(DreamVc1 *state, const GstMapInfo *map,
	gint *pictureType, guint *referenceShift, gboolean *hasPicture)
{
	gsize offset = vc1_next_startcode(map->data, map->size, 0);
	guint8 scratch[512];
	*pictureType = -1;
	*referenceShift = 0;
	*hasPicture = FALSE;
	while (offset < map->size) {
		guint type = map->data[offset + 3];
		gsize next = vc1_next_startcode(map->data, map->size, offset + 4);
		gsize size = next - offset - 4;
		const guint8 *data = map->data + offset + 4;
		if (type == GST_VC1_SEQUENCE) {
			GstVC1SeqHdr sequence = { 0 };
			gsize length = vc1_unescape(data, size, scratch, sizeof(scratch));
			state->haveSequence = size <= sizeof(scratch) &&
				gst_vc1_parse_sequence_header(scratch, length, &sequence) == GST_VC1_PARSER_OK &&
				sequence.profile == GST_VC1_PROFILE_ADVANCED;
			if (state->haveSequence) {
				state->sequence = sequence;
				if (!state->fpsNumerator && sequence.advanced.fps_n && sequence.advanced.fps_d) {
					state->fpsNumerator = sequence.advanced.fps_n;
					state->fpsDenominator = sequence.advanced.fps_d;
				}
			}
		} else if (type == GST_VC1_ENTRYPOINT && state->haveSequence) {
			GstVC1EntryPointHdr entry = { 0 };
			gsize length = vc1_unescape(data, size, scratch, sizeof(scratch));
			if (size > sizeof(scratch) || gst_vc1_parse_entry_point_header(scratch,
				length, &entry, &state->sequence) != GST_VC1_PARSER_OK)
				state->haveSequence = FALSE;
		} else if (type == GST_VC1_FRAME) {
			*hasPicture = TRUE;
			*pictureType = vc1_picture(state, data, size, referenceShift);
			return;
		}
		offset = next;
	}
}

static gboolean vc1_emit(DreamVc1 *state, guint64 displayIndex, GstBuffer **output, GError **error)
{
	GstBuffer *pending = state->pending;
	GstClockTime pts, delta;
	if (!pending) {
		*output = NULL;
		return TRUE;
	}
	pts = GST_BUFFER_PTS(pending);
	/* The original writer anchors DTS-only streams at their first decode
	 * timestamp. Unlike a supplied PTS this refers to the decode ordinal. */
	if (!GST_CLOCK_TIME_IS_VALID(pts) && state->pendingType >= 0 &&
		!GST_CLOCK_TIME_IS_VALID(state->anchor) && GST_BUFFER_DTS_IS_VALID(pending)) {
		state->anchor = GST_BUFFER_DTS(pending);
		state->anchorIndex = state->pendingIndex;
	}
	if (GST_CLOCK_TIME_IS_VALID(pts)) {
		if (state->pendingType >= 0 && !GST_CLOCK_TIME_IS_VALID(state->anchor)) {
			state->anchor = pts;
			state->anchorIndex = displayIndex;
		}
	} else if (state->pendingType >= 0 && GST_CLOCK_TIME_IS_VALID(state->anchor) &&
		state->fpsNumerator && state->fpsDenominator) {
		guint64 distance = displayIndex >= state->anchorIndex ?
			displayIndex - state->anchorIndex : state->anchorIndex - displayIndex;
		delta = gst_util_uint64_scale(distance, (guint64) GST_SECOND * state->fpsDenominator,
			state->fpsNumerator);
		if (displayIndex >= state->anchorIndex) {
			if (delta < GST_CLOCK_TIME_NONE - state->anchor)
				pts = state->anchor + delta;
		} else if (delta <= state->anchor) {
			pts = state->anchor - delta;
		}
		if (GST_CLOCK_TIME_IS_VALID(pts)) {
			GstBuffer *copy = gst_buffer_copy(pending);
			if (!copy)
				return fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to copy VC-1 timing metadata");
			GST_BUFFER_PTS(copy) = pts;
			gst_buffer_unref(pending);
			pending = copy;
		}
	}
	state->pending = NULL;
	*output = pending;
	return TRUE;
}

gboolean dream_codec_push(DreamCodec *codec, GstBuffer *input,
	gboolean prependHeaders, GstBuffer **output, GError **error)
{
	GstBuffer *framed;
	GstMapInfo map = GST_MAP_INFO_INIT;
	DreamVc1 next;
	gint pictureType;
	guint referenceShift;
	guint64 displayIndex;
	gboolean hasPicture;
	*output = NULL;
	framed = dream_codec_transform(codec, input, prependHeaders, error);
	if (!framed)
		return FALSE;
	/* FLV metadata may omit or misreport dimensions. The Spark picture
	 * header is authoritative, but only after the whole AU was validated. */
	if (codec->kind == DREAM_CODEC_SORENSON && gst_buffer_get_size(framed))
		dream_h263_get_size(codec->h263, &codec->width, &codec->height);
	if (!codec->vc1 || !gst_buffer_get_size(framed)) {
		*output = framed;
		return TRUE;
	}
	next = *codec->vc1;
	if (!gst_buffer_map(framed, &map, GST_MAP_READ)) {
		gst_buffer_unref(framed);
		return fail(error, DREAM_CODEC_ERROR_MEMORY, "Unable to inspect VC-1 picture timing");
	}
	vc1_inspect(&next, &map, &pictureType, &referenceShift, &hasPicture);
	gst_buffer_unmap(framed, &map);
	if (hasPicture && next.nextIndex == G_MAXUINT64) {
		gst_buffer_unref(framed);
		return fail(error, DREAM_CODEC_ERROR_LIMIT, "VC-1 picture counter overflow");
	}
	displayIndex = next.pendingIndex;
	if (next.pendingType == GST_VC1_PICTURE_TYPE_B && displayIndex)
		displayIndex--;
	else if (pictureType == GST_VC1_PICTURE_TYPE_B &&
		(next.pendingType == GST_VC1_PICTURE_TYPE_P || next.pendingType == GST_VC1_PICTURE_TYPE_I ||
		next.pendingType == GST_VC1_PICTURE_TYPE_SKIPPED) &&
		displayIndex <= G_MAXUINT64 - referenceShift)
		displayIndex += referenceShift;
	if (!vc1_emit(&next, displayIndex, output, error)) {
		gst_buffer_unref(framed);
		return FALSE;
	}
	next.pending = framed;
	next.pendingType = pictureType;
	next.pendingIndex = next.nextIndex;
	if (hasPicture)
		next.nextIndex++;
	*codec->vc1 = next;
	return TRUE;
}

gboolean dream_codec_drain(DreamCodec *codec, GstBuffer **output, GError **error)
{
	guint64 displayIndex;
	*output = NULL;
	if (!codec->vc1)
		return TRUE;
	displayIndex = codec->vc1->pendingIndex;
	if (codec->vc1->pendingType == GST_VC1_PICTURE_TYPE_B && displayIndex)
		displayIndex--;
	return vc1_emit(codec->vc1, displayIndex, output, error);
}
