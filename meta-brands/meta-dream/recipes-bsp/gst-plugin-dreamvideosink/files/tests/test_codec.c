/* SPDX-License-Identifier: LGPL-2.1-or-later */
#include "../src/dream_codec.h"

#include <string.h>

static guint16 read_be16(const guint8 *data)
{
	return ((guint16) data[0] << 8) | data[1];
}

static guint32 read_be32(const guint8 *data)
{
	return ((guint32) data[0] << 24) | ((guint32) data[1] << 16) |
		((guint32) data[2] << 8) | data[3];
}

static const guint8 avcc[] = {
	1, 66, 0, 30, 0xff, 0xe1, 0, 3, 0x67, 0x42, 0,
	1, 0, 2, 0x68, 0xce
};
static const guint8 avcHeaders[] = {
	0, 0, 0, 1, 0x67, 0x42, 0, 0, 0, 0, 1, 0x68, 0xce
};

static GstBuffer *buffer_from_data(const guint8 *data, gsize size)
{
	GstBuffer *buffer = gst_buffer_new_allocate(NULL, size, NULL);
	if (size)
		g_assert_cmpuint(gst_buffer_fill(buffer, 0, data, size), ==, size);
	return buffer;
}

static GstCaps *nal_caps(const gchar *name, const gchar *format,
	const guint8 *header, gsize size)
{
	GstCaps *caps = gst_caps_new_simple(name, "width", G_TYPE_INT, 1920,
		"height", G_TYPE_INT, 1080, "parsed", G_TYPE_BOOLEAN, TRUE,
		"alignment", G_TYPE_STRING, "au", "stream-format", G_TYPE_STRING, format,
		"framerate", GST_TYPE_FRACTION, 30000, 1001, NULL);
	if (header) {
		GstBuffer *buffer = buffer_from_data(header, size);
		gst_caps_set_simple(caps, "codec_data", GST_TYPE_BUFFER, buffer, NULL);
		gst_buffer_unref(buffer);
	}
	return caps;
}

static void assert_buffer(GstBuffer *buffer, const guint8 *expected, gsize size)
{
	GstMapInfo map = GST_MAP_INFO_INIT;
	g_assert_nonnull(buffer);
	g_assert_cmpuint(gst_buffer_get_size(buffer), ==, size);
	g_assert_true(gst_buffer_map(buffer, &map, GST_MAP_READ));
	g_assert_cmpmem(map.data, map.size, expected, size);
	gst_buffer_unmap(buffer, &map);
}

static void test_avc(void)
{
	DreamCodec codec;
	GError *error = NULL;
	GstCaps *caps = nal_caps("video/x-h264", "avc", avcc, sizeof(avcc));
	const guint8 packed[] = { 0, 0, 0, 2, 0x65, 0xaa, 0, 0, 0, 2, 0x61, 0xbb };
	const guint8 expected[] = {
		0, 0, 0, 1, 0x67, 0x42, 0, 0, 0, 0, 1, 0x68, 0xce,
		0, 0, 0, 1, 0x65, 0xaa, 0, 0, 0, 1, 0x61, 0xbb
	};
	GstBuffer *input = buffer_from_data(packed, sizeof(packed)), *output;

	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	g_assert_no_error(error);
	g_assert_cmpuint(codec.format, ==, 4);
	g_assert_cmpuint(codec.vformat, ==, 2);
	g_assert_cmpuint(codec.streamType, ==, 1);
	g_assert_cmpuint(codec.nalLengthSize, ==, 4);
	g_assert_cmpuint(codec.rate, ==, 3203);
	assert_buffer(codec.headers, avcHeaders, sizeof(avcHeaders));
	GST_BUFFER_PTS(input) = 42 * GST_SECOND;
	GST_BUFFER_DTS(input) = 41 * GST_SECOND;
	GST_BUFFER_DURATION(input) = GST_SECOND / 30;
	GST_BUFFER_FLAG_SET(input, GST_BUFFER_FLAG_DISCONT);
	output = dream_codec_transform(&codec, input, TRUE, &error);
	g_assert_no_error(error);
	assert_buffer(output, expected, sizeof(expected));
	g_assert_cmpuint(GST_BUFFER_PTS(output), ==, GST_BUFFER_PTS(input));
	g_assert_cmpuint(GST_BUFFER_DTS(output), ==, GST_BUFFER_DTS(input));
	g_assert_cmpuint(GST_BUFFER_DURATION(output), ==, GST_BUFFER_DURATION(input));
	g_assert_true(GST_BUFFER_FLAG_IS_SET(output, GST_BUFFER_FLAG_DISCONT));
	gst_buffer_unref(output);
	output = dream_codec_transform(&codec, input, FALSE, &error);
	g_assert_no_error(error);
	assert_buffer(output, expected + sizeof(avcHeaders), sizeof(expected) - sizeof(avcHeaders));
	gst_buffer_unref(output);
	gst_buffer_unref(input);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
	dream_codec_clear(&codec);
}

static void test_nal_length_sizes(void)
{
	guint lengthSize;
	const guint8 expected[] = { 0, 0, 0, 1, 0x65, 0xaa };
	for (lengthSize = 1; lengthSize <= 4; lengthSize++) {
		DreamCodec codec;
		GError *error = NULL;
		guint8 header[sizeof(avcc)], packed[6] = { 0 };
		GstCaps *caps;
		GstBuffer *input, *output;

		if (lengthSize == 3)
			continue;
		memcpy(header, avcc, sizeof(header));
		header[4] = 0xfc | (lengthSize - 1);
		caps = nal_caps("video/x-h264", "avc", header, sizeof(header));
		dream_codec_init(&codec);
		g_assert_true(dream_codec_configure(&codec, caps, &error));
		packed[lengthSize - 1] = 2;
		packed[lengthSize] = 0x65;
		packed[lengthSize + 1] = 0xaa;
		input = buffer_from_data(packed, lengthSize + 2);
		output = dream_codec_transform(&codec, input, FALSE, &error);
		g_assert_no_error(error);
		assert_buffer(output, expected, sizeof(expected));
		gst_buffer_unref(output);
		gst_buffer_unref(input);
		gst_caps_unref(caps);
		dream_codec_clear(&codec);
	}
}

static GByteArray *make_hvcc(void)
{
	guint8 header[23] = { 1 };
	GByteArray *data = g_byte_array_new();
	guint type;
	header[13] = 0xf0;
	header[15] = header[16] = 0xfc;
	header[17] = header[18] = 0xf8;
	header[21] = 3;
	header[22] = 3;
	g_byte_array_append(data, header, sizeof(header));
	for (type = 32; type <= 34; type++) {
		guint8 nal[] = { 0x80 | type, 0, 1, 0, 3, type << 1, 1, 0xaa };
		g_byte_array_append(data, nal, sizeof(nal));
	}
	return data;
}

static void test_hevc(void)
{
	DreamCodec codec;
	GError *error = NULL;
	GByteArray *header = make_hvcc();
	GstCaps *caps = nal_caps("video/x-h265", "hvc1", header->data, header->len);
	const guint8 packed[] = { 0, 0, 0, 3, 0x26, 1, 0xbb };
	const guint8 expected[] = {
		0, 0, 0, 1, 0x40, 1, 0xaa, 0, 0, 0, 1, 0x42, 1, 0xaa,
		0, 0, 0, 1, 0x44, 1, 0xaa, 0, 0, 0, 1, 0x26, 1, 0xbb
	};
	GstBuffer *input = buffer_from_data(packed, sizeof(packed)), *output;

	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	g_assert_cmpuint(codec.format, ==, 15);
	g_assert_cmpuint(codec.vformat, ==, 11);
	g_assert_cmpuint(codec.streamType, ==, 22);
	output = dream_codec_transform(&codec, input, TRUE, &error);
	g_assert_no_error(error);
	assert_buffer(output, expected, sizeof(expected));
	gst_buffer_unref(output);
	gst_buffer_unref(input);
	gst_caps_unref(caps);
	g_byte_array_unref(header);
	dream_codec_clear(&codec);
}

static void test_inband_headers(void)
{
	DreamCodec codec;
	GError *error = NULL;
	const guint8 header[] = { 1, 66, 0, 30, 0xff, 0xe0, 0 };
	GstCaps *caps = nal_caps("video/x-h264", "avc3", header, sizeof(header));
	GByteArray *hevc = make_hvcc();

	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	g_assert_no_error(error);
	g_assert_null(codec.headers);
	g_assert_cmpuint(codec.nalLengthSize, ==, 4);
	gst_caps_unref(caps);
	hevc->data[22] = 0;
	caps = nal_caps("video/x-h265", "hev1", hevc->data, 23);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	g_assert_no_error(error);
	g_assert_null(codec.headers);
	gst_caps_unref(caps);
	g_byte_array_unref(hevc);
	dream_codec_clear(&codec);
}

static void test_avc_extension(void)
{
	DreamCodec codec;
	GError *error = NULL;
	guint8 header[sizeof(avcc) + 8];
	const guint8 extension[] = { 0xfc, 0xf8, 0xf8, 1, 0, 2, 0x6d, 0xaa };
	GstCaps *caps;
	memcpy(header, avcc, sizeof(avcc));
	header[1] = 100;
	memcpy(header + sizeof(avcc), extension, sizeof(extension));
	caps = nal_caps("video/x-h264", "avc", header, sizeof(header));
	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	g_assert_no_error(error);
	g_assert_cmpuint(gst_buffer_get_size(codec.headers), ==, sizeof(avcHeaders) + 6);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void test_elementary_zero_copy(void)
{
	DreamCodec codec;
	GError *error = NULL;
	const guint8 data[] = { 0, 0, 1, 0x65, 0xaa };
	GstCaps *caps = nal_caps("video/x-h264", "byte-stream", NULL, 0);
	GstBuffer *input = buffer_from_data(data, sizeof(data)), *output;

	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	output = dream_codec_transform(&codec, input, TRUE, &error);
	g_assert_no_error(error);
	g_assert_true(output == input);
	gst_buffer_unref(output);
	gst_buffer_unref(input);
	input = gst_buffer_new();
	output = dream_codec_transform(&codec, input, TRUE, &error);
	g_assert_true(output == input);
	g_assert_cmpuint(gst_buffer_get_size(output), ==, 0);
	gst_buffer_unref(output);
	gst_buffer_unref(input);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void test_mpeg(void)
{
	guint version;
	for (version = 1; version <= 4; version++) {
		DreamCodec codec;
		GError *error = NULL;
		const guint8 data[] = { 0, 0, 1, 0xb3, 0xaa };
		GstCaps *caps;
		GstBuffer *input, *output;
		if (version == 3)
			continue;
		caps = gst_caps_new_simple("video/mpeg", "width", G_TYPE_INT, 720,
			"height", G_TYPE_INT, 576, "mpegversion", G_TYPE_INT, version,
			"systemstream", G_TYPE_BOOLEAN, FALSE, "parsed", G_TYPE_BOOLEAN, TRUE, NULL);
		dream_codec_init(&codec);
		g_assert_true(dream_codec_configure(&codec, caps, &error));
		g_assert_cmpuint(codec.format, ==, version == 4 ? 3 : 0);
		g_assert_cmpuint(codec.vformat, ==, version == 4 ? 1 : 0);
		g_assert_cmpuint(codec.streamType, ==, version == 4 ? 4 : 0);
		g_assert_cmpuint(codec.rate, ==, 0);
		input = buffer_from_data(data, sizeof(data));
		output = dream_codec_transform(&codec, input, TRUE, &error);
		g_assert_no_error(error);
		g_assert_true(output == input);
		gst_buffer_unref(output);
		gst_buffer_unref(input);
		gst_caps_unref(caps);
		dream_codec_clear(&codec);
	}
}

static void assert_bad_caps(GstCaps *caps)
{
	DreamCodec codec;
	GError *error = NULL;
	dream_codec_init(&codec);
	g_assert_false(dream_codec_configure(&codec, caps, &error));
	g_assert_nonnull(error);
	g_assert_cmpuint(error->domain, ==, DREAM_CODEC_ERROR);
	g_clear_error(&error);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void test_header_truncation(void)
{
	gsize size;
	GByteArray *hevc = make_hvcc();
	for (size = 0; size < sizeof(avcc); size++)
		assert_bad_caps(nal_caps("video/x-h264", "avc", avcc, size));
	for (size = 0; size < hevc->len; size++)
		assert_bad_caps(nal_caps("video/x-h265", "hvc1", hevc->data, size));
	g_byte_array_unref(hevc);
}

static void test_bad_caps_and_atomicity(void)
{
	DreamCodec codec;
	GError *error = NULL;
	GstCaps *good = nal_caps("video/x-h264", "avc", avcc, sizeof(avcc));
	GstCaps *bad = nal_caps("video/x-h264", "avc", NULL, 0);
	GstBuffer *headers;
	guint8 header[sizeof(avcc)];

	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, good, &error));
	headers = codec.headers;
	g_assert_false(dream_codec_configure(&codec, bad, &error));
	g_assert_nonnull(error);
	g_clear_error(&error);
	g_assert_true(codec.headers == headers);
	g_assert_cmpuint(codec.kind, ==, DREAM_CODEC_H264);
	gst_caps_unref(bad);
	gst_caps_unref(good);
	dream_codec_clear(&codec);
	assert_bad_caps(gst_caps_new_any());
	assert_bad_caps(gst_caps_new_empty());
	assert_bad_caps(nal_caps("video/x-vp8", "byte-stream", NULL, 0));
	assert_bad_caps(nal_caps("video/x-h265", "avc", NULL, 0));
	bad = nal_caps("video/x-h264", "byte-stream", NULL, 0);
	gst_caps_set_simple(bad, "alignment", G_TYPE_STRING, "nal", NULL);
	assert_bad_caps(bad);
	bad = nal_caps("video/x-h264", "byte-stream", NULL, 0);
	gst_caps_set_simple(bad, "width", G_TYPE_INT, 0, NULL);
	assert_bad_caps(bad);
	bad = nal_caps("video/x-h264", "byte-stream", NULL, 0);
	gst_caps_set_simple(bad, "height", G_TYPE_INT, 4097, NULL);
	assert_bad_caps(bad);
	bad = nal_caps("video/x-h264", "byte-stream", NULL, 0);
	gst_caps_set_simple(bad, "codec_data", G_TYPE_STRING, "invalid", NULL);
	assert_bad_caps(bad);
	memcpy(header, avcc, sizeof(header));
	header[4] = 0xfe;
	assert_bad_caps(nal_caps("video/x-h264", "avc", header, sizeof(header)));
	header[4] = 0xff;
	header[8] = 0x68;
	assert_bad_caps(nal_caps("video/x-h264", "avc", header, sizeof(header)));
}

static void test_bad_nals(void)
{
	DreamCodec codec;
	GError *error = NULL;
	GstCaps *caps = nal_caps("video/x-h264", "avc", avcc, sizeof(avcc));
	const guint8 invalid[][7] = {
		{ 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 3, 0x65 },
		{ 0xff, 0xff, 0xff, 0xff }, { 0, 0, 0, 1, 0x80 },
		{ 0, 0, 0, 1, 0 }, { 0, 0, 0, 1, 0x65, 0 }
	};
	const gsize sizes[] = { 3, 4, 5, 4, 5, 5, 6 };
	guint index;

	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	for (index = 0; index < G_N_ELEMENTS(sizes); index++) {
		GstBuffer *input = buffer_from_data(invalid[index], sizes[index]);
		g_assert_null(dream_codec_transform(&codec, input, FALSE, &error));
		g_assert_error(error, DREAM_CODEC_ERROR, DREAM_CODEC_ERROR_DATA);
		g_clear_error(&error);
		gst_buffer_unref(input);
	}
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void test_limits(void)
{
	DreamCodec codec;
	GError *error = NULL;
	GstCaps *caps = nal_caps("video/x-h264", "byte-stream", NULL, 0);
	GstBuffer *large = gst_buffer_new_allocate(NULL, DREAM_CODEC_MAX_FRAME_SIZE + 1, NULL);
	GstBuffer *header = gst_buffer_new_allocate(NULL, DREAM_CODEC_MAX_HEADER_SIZE + 1, NULL);

	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	g_assert_null(dream_codec_transform(&codec, large, FALSE, &error));
	g_assert_error(error, DREAM_CODEC_ERROR, DREAM_CODEC_ERROR_LIMIT);
	g_clear_error(&error);
	gst_caps_set_simple(caps, "codec_data", GST_TYPE_BUFFER, header, NULL);
	g_assert_false(dream_codec_configure(&codec, caps, &error));
	g_assert_error(error, DREAM_CODEC_ERROR, DREAM_CODEC_ERROR_LIMIT);
	g_clear_error(&error);
	gst_buffer_unref(header);
	gst_buffer_unref(large);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void test_expansion_limits(void)
{
	DreamCodec codec;
	GError *error = NULL;
	GByteArray *header = make_hvcc();
	GstCaps *caps;
	guint type, index;
	guint8 avcHeader[sizeof(avcc)], *data;
	GstBuffer *input;
	gsize size = 14 * 1024 * 1024;

	/* Small packed NALs can exceed the output limit before the input limit. */
	g_byte_array_set_size(header, 23);
	for (type = 32; type <= 34; type++) {
		guint8 array[] = { 0x80 | type, 0xff, 0xff };
		guint8 nal[] = { 0, 2, type << 1, 1 };
		g_byte_array_append(header, array, sizeof(array));
		for (index = 0; index < 65535; index++)
			g_byte_array_append(header, nal, sizeof(nal));
	}
	g_assert_cmpuint(header->len, <, DREAM_CODEC_MAX_HEADER_SIZE);
	caps = nal_caps("video/x-h265", "hvc1", header->data, header->len);
	dream_codec_init(&codec);
	g_assert_false(dream_codec_configure(&codec, caps, &error));
	g_assert_error(error, DREAM_CODEC_ERROR, DREAM_CODEC_ERROR_LIMIT);
	g_clear_error(&error);
	gst_caps_unref(caps);
	g_byte_array_unref(header);
	memcpy(avcHeader, avcc, sizeof(avcHeader));
	avcHeader[4] = 0xfc;
	caps = nal_caps("video/x-h264", "avc", avcHeader, sizeof(avcHeader));
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	data = g_malloc(size);
	for (index = 0; index < size; index += 2) {
		data[index] = 1;
		data[index + 1] = 0x65;
	}
	input = gst_buffer_new_wrapped(data, size);
	g_assert_null(dream_codec_transform(&codec, input, FALSE, &error));
	g_assert_error(error, DREAM_CODEC_ERROR, DREAM_CODEC_ERROR_LIMIT);
	g_clear_error(&error);
	gst_buffer_unref(input);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void test_random_records(void)
{
	GRand *random = g_rand_new_with_seed(0x44726561);
	guint iteration;
	for (iteration = 0; iteration < 2048; iteration++) {
		guint8 data[256];
		guint index, size = g_rand_int_range(random, 1, sizeof(data));
		DreamCodec codec;
		GError *error = NULL;
		GstCaps *caps;
		for (index = 0; index < size; index++)
			data[index] = g_rand_int(random);
		caps = nal_caps(iteration & 1 ? "video/x-h264" : "video/x-h265",
			iteration & 1 ? "avc3" : "hev1", data, size);
		dream_codec_init(&codec);
		if (!dream_codec_configure(&codec, caps, &error)) {
			g_assert_nonnull(error);
			g_clear_error(&error);
		}
		gst_caps_unref(caps);
		dream_codec_clear(&codec);
	}
	g_rand_free(random);
}


static void test_extended_mappings(void)
{
	const struct { const gchar *caps; guint format, vformat, stream; } cases[] = {
		{ "video/x-divx,divxversion=3,width=640,height=480", 1, 1, 4 },
		{ "video/x-divx,divxversion=4", 2, 1, 4 },
		{ "video/x-divx,divxversion=5", 3, 1, 4 },
		{ "video/x-divx,divxversion=6", 3, 1, 4 },
		{ "video/x-xvid", 3, 1, 4 },
		{ "video/x-msmpeg,msmpegversion=43", 1, 1, 4 },
		{ "video/x-vp9", 16, 14, 23 },
		{ "video/x-cavs", 13, 7, 16 },
		{ "image/jpeg", 5, 3, 5 },
		{ "video/x-jpeg", 5, 3, 5 },
		{ "video/x-pn-realvideo,rmversion=3", 8, 4, 7 },
		{ "video/x-pn-realvideo,rmversion=4", 9, 4, 7 }
	};
	guint index;
	for (index = 0; index < G_N_ELEMENTS(cases); index++) {
		DreamCodec codec;
		GError *error = NULL;
		GstCaps *caps = gst_caps_from_string(cases[index].caps);
		dream_codec_init(&codec);
		g_assert_true(dream_codec_configure(&codec, caps, &error));
		g_assert_no_error(error);
		g_assert_cmpuint(codec.format, ==, cases[index].format);
		g_assert_cmpuint(codec.vformat, ==, cases[index].vformat);
		g_assert_cmpuint(codec.streamType, ==, cases[index].stream);
		gst_caps_unref(caps);
		dream_codec_clear(&codec);
	}
	assert_bad_caps(gst_caps_from_string("video/x-divx,divxversion=2"));
	assert_bad_caps(gst_caps_from_string("video/x-divx,divxversion=3,width=4096"));
	assert_bad_caps(gst_caps_from_string("video/x-msmpeg,msmpegversion=42"));
	assert_bad_caps(gst_caps_from_string("video/x-pn-realvideo,rmversion=2"));
	assert_bad_caps(gst_caps_from_string("video/x-wmv,wmvversion=1,format=WMV1"));
	assert_bad_caps(gst_caps_from_string("video/x-wmv,wmvversion=3,format=WMV3"));
}

static void test_vp9(void)
{
	DreamCodec codec;
	GError *error = NULL;
	GstCaps *caps = gst_caps_from_string("video/x-vp9");
	const guint8 frame[] = { 0x82, 0x55, 0x66 };
	const guint8 expected[] = {
		0, 0, 0, 7, 0xff, 0xff, 0xff, 0xf8, 0, 0, 0, 1, 'A', 'M', 'L', 'V', 0x82, 0x55, 0x66
	};
	const guint8 superframe[] = { 0x82, 0x55, 0x66, 0x83, 0x44, 0xc1, 3, 2, 0xc1 };
	const guint8 bad[][9] = {
		{ 0x82, 0x55, 0x66, 0x83, 0x44, 0xc0, 3, 2, 0xc1 },
		{ 0x82, 0x55, 0x66, 0x83, 0x44, 0xc1, 0, 5, 0xc1 },
		{ 0x82, 0x55, 0x66, 0x83, 0x44, 0xc1, 4, 2, 0xc1 },
		{ 0x82, 0x55, 0x66, 0x83, 0x44, 0xc1, 2, 2, 0xc1 }
	};
	GstBuffer *input, *output;
	guint index;
	guint8 data[37];

	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	input = buffer_from_data(frame, sizeof(frame));
	GST_BUFFER_PTS(input) = GST_SECOND;
	output = dream_codec_transform(&codec, input, FALSE, &error);
	g_assert_no_error(error);
	assert_buffer(output, expected, sizeof(expected));
	g_assert_cmpuint(GST_BUFFER_PTS(output), ==, GST_SECOND);
	gst_buffer_unref(output);
	gst_buffer_unref(input);
	input = buffer_from_data(superframe, sizeof(superframe));
	output = dream_codec_transform(&codec, input, FALSE, &error);
	g_assert_no_error(error);
	g_assert_cmpuint(gst_buffer_extract(output, 0, data, sizeof(data)), ==, sizeof(data));
	g_assert_cmpmem(data, sizeof(expected), expected, sizeof(expected));
	g_assert_cmpuint(read_be32(data + 19), ==, 6);
	g_assert_cmpmem(data + 35, 2, superframe + 3, 2);
	gst_buffer_unref(output);
	gst_buffer_unref(input);
	for (index = 0; index < G_N_ELEMENTS(bad); index++) {
		input = buffer_from_data(bad[index], sizeof(bad[index]));
		g_assert_null(dream_codec_transform(&codec, input, FALSE, &error));
		g_assert_error(error, DREAM_CODEC_ERROR, DREAM_CODEC_ERROR_DATA);
		g_clear_error(&error);
		gst_buffer_unref(input);
	}
	input = buffer_from_data((const guint8 *) "\xc1", 1);
	g_assert_null(dream_codec_transform(&codec, input, FALSE, &error));
	g_clear_error(&error);
	gst_buffer_unref(input);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void test_divx3(void)
{
	DreamCodec codec;
	GError *error = NULL;
	GstCaps *caps = gst_caps_from_string("video/x-divx,divxversion=3,width=640,height=480");
	const guint8 frame[] = { 0x11, 0x22 };
	const guint8 expected[] = {
		0, 0, 0, 1, 0x20, 0x28, 0x01, 0xe0, 0, 0,
		0, 0, 0, 1, 0xb6, 'D', 'I', 'V', 'X', '3', '.', '1', '1', 0, 0, 0, 2, 0x11, 0x22
	};
	GstBuffer *input = buffer_from_data(frame, sizeof(frame)), *output;
	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	output = dream_codec_transform(&codec, input, TRUE, &error);
	g_assert_no_error(error);
	assert_buffer(output, expected, sizeof(expected));
	gst_buffer_unref(output);
	output = dream_codec_transform(&codec, input, FALSE, &error);
	assert_buffer(output, expected + 10, sizeof(expected) - 10);
	gst_buffer_unref(output);
	gst_buffer_unref(input);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void test_wmv(void)
{
	DreamCodec codec;
	GError *error = NULL;
	const guint8 header[] = { 0x12, 0x34, 0x56, 0x78 };
	const guint8 frame[] = { 0x91, 0x23, 0x45 };
	GstBuffer *config = buffer_from_data(header, sizeof(header));
	GstBuffer *input = buffer_from_data(frame, sizeof(frame)), *output;
	GstCaps *caps = gst_caps_from_string("video/x-wmv,wmvversion=3,format=WMV3,width=640,height=480");
	guint8 result[55];
	guint checksum = 0, index;

	gst_caps_set_simple(caps, "codec_data", GST_TYPE_BUFFER, config, NULL);
	gst_buffer_unref(config);
	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	g_assert_cmpuint(codec.format, ==, 10);
	g_assert_cmpuint(codec.streamType, ==, 3);
	output = dream_codec_transform(&codec, input, TRUE, &error);
	g_assert_no_error(error);
	g_assert_cmpuint(gst_buffer_get_size(output), ==, sizeof(result));
	g_assert_cmpuint(gst_buffer_extract(output, 0, result, sizeof(result)), ==, sizeof(result));
	g_assert_cmpuint(read_be32(result), ==, 0x110);
	g_assert_cmpuint(result[8], ==, 8);
	g_assert_cmpuint(read_be16(result + 22), ==, 640);
	g_assert_cmpuint(read_be16(result + 24), ==, 480);
	g_assert_cmpmem(result + 26, 4, header, 4);
	g_assert_cmpuint(read_be32(result + 30), ==, 0x10d);
	g_assert_cmpuint(result[38], ==, 3);
	g_assert_cmpmem(result + 52, 3, frame, 3);
	for (index = 4; index < 16; index++)
		checksum += result[index];
	g_assert_cmpuint(read_be16(result + 16), ==, checksum);
	g_assert_cmpuint(read_be16(result + 19), ==, checksum);
	gst_buffer_unref(output);
	output = dream_codec_transform(&codec, input, FALSE, &error);
	g_assert_no_error(error);
	assert_buffer(output, result, sizeof(result));
	gst_buffer_unref(output);
	dream_codec_flush(&codec);
	output = dream_codec_transform(&codec, input, TRUE, &error);
	g_assert_no_error(error);
	assert_buffer(output, result, sizeof(result));
	gst_buffer_unref(output);
	/* WVC3 uses the same per-frame packet format, not WVC1 sequence insertion. */
	gst_caps_set_simple(caps, "format", G_TYPE_STRING, "WVC3", NULL);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	output = dream_codec_transform(&codec, input, TRUE, &error);
	g_assert_no_error(error);
	assert_buffer(output, result, sizeof(result));
	gst_buffer_unref(output);
	gst_buffer_unref(input);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void test_vc1(void)
{
	DreamCodec codec;
	GError *error = NULL;
	const guint8 header[] = { 4, 0, 0, 1, 0x0f, 0x45 };
	const guint8 frame[] = { 0x80, 0x11 };
	const guint8 expected[] = { 0, 0, 1, 0x0f, 0x45, 0, 0, 1, 0x0d, 0x80, 0x11 };
	const guint8 coded[] = { 0, 0, 1, 0x0d, 0x80, 0x11 };
	GstCaps *caps = gst_caps_from_string("video/x-wmv,wmvversion=3,format=WVC1");
	GstBuffer *config = buffer_from_data(header, sizeof(header));
	GstBuffer *input = buffer_from_data(frame, sizeof(frame)), *output;
	gst_caps_set_simple(caps, "codec_data", GST_TYPE_BUFFER, config, NULL);
	gst_buffer_unref(config);
	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	g_assert_cmpuint(codec.format, ==, 11);
	output = dream_codec_transform(&codec, input, TRUE, &error);
	g_assert_no_error(error);
	assert_buffer(output, expected, sizeof(expected));
	gst_buffer_unref(output);
	gst_buffer_unref(input);
	input = buffer_from_data(coded, sizeof(coded));
	output = dream_codec_transform(&codec, input, TRUE, &error);
	assert_buffer(output, expected, sizeof(expected));
	gst_buffer_unref(output);
	output = dream_codec_transform(&codec, input, FALSE, &error);
	g_assert_true(input == output);
	gst_buffer_unref(output);
	gst_buffer_unref(input);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void test_jpeg_and_real(void)
{
	DreamCodec codec;
	GError *error = NULL;
	const guint8 jpeg[] = { 0xff, 0xd8, 0xff, 0xd9 };
	const guint8 raw[] = { 0x12, 0x34 };
	GstCaps *caps = gst_caps_from_string("image/jpeg");
	GstBuffer *input = buffer_from_data(jpeg, sizeof(jpeg)), *output;
	guint8 prefix[6];
	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	output = dream_codec_transform(&codec, input, TRUE, &error);
	g_assert_no_error(error);
	g_assert_cmpuint(gst_buffer_get_size(output), ==, 422 + sizeof(jpeg));
	g_assert_cmpuint(gst_buffer_extract(output, 0, prefix, sizeof(prefix)), ==, sizeof(prefix));
	g_assert_cmpmem(prefix, sizeof(prefix), "\xff\xd8\xff\xc4\x01\xa2", 6);
	gst_buffer_unref(output);
	output = dream_codec_transform(&codec, input, FALSE, &error);
	g_assert_true(input == output);
	gst_buffer_unref(output);
	gst_buffer_unref(input);
	input = buffer_from_data(raw, sizeof(raw));
	g_assert_null(dream_codec_transform(&codec, input, FALSE, &error));
	g_clear_error(&error);
	gst_caps_unref(caps);
	caps = gst_caps_from_string("video/x-pn-realvideo,rmversion=4");
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	output = dream_codec_transform(&codec, input, FALSE, &error);
	g_assert_true(input == output);
	gst_buffer_unref(output);
	gst_buffer_unref(input);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void test_extended_random(void)
{
	GRand *random = g_rand_new_with_seed(0x56503931);
	DreamCodec codec;
	GError *error = NULL;
	GstCaps *caps = gst_caps_from_string("video/x-vp9");
	guint iteration, index;
	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	for (iteration = 0; iteration < 2048; iteration++) {
		guint8 data[256];
		guint size = g_rand_int_range(random, 1, sizeof(data));
		GstBuffer *input, *output;
		for (index = 0; index < size; index++)
			data[index] = g_rand_int(random);
		input = buffer_from_data(data, size);
		output = dream_codec_transform(&codec, input, FALSE, &error);
		if (output) {
			g_assert_no_error(error);
			gst_buffer_unref(output);
		} else {
			g_assert_nonnull(error);
			g_clear_error(&error);
		}
		gst_buffer_unref(input);
	}
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
	g_rand_free(random);
}



static void put_test_bits(guint8 *data, guint *position, guint value, guint count)
{
	while (count--) {
		if ((value >> count) & 1)
			data[*position / 8] |= 1 << (7 - *position % 8);
		(*position)++;
	}
}

static gsize make_h263_skip_frame(guint8 *data, gboolean sorenson, guint width, guint height)
{
	guint position = 0, index;
	memset(data, 0, 32);
	put_test_bits(data, &position, 1, 17);
	put_test_bits(data, &position, 0, 5);
	put_test_bits(data, &position, 7, 8);
	if (sorenson) {
		put_test_bits(data, &position, 1, 3);
		put_test_bits(data, &position, width, 16);
		put_test_bits(data, &position, height, 16);
		put_test_bits(data, &position, 1, 2);
		put_test_bits(data, &position, 0, 1);
		put_test_bits(data, &position, 10, 5);
	} else {
		put_test_bits(data, &position, 16, 5);
		put_test_bits(data, &position, 1, 3);
		put_test_bits(data, &position, 1, 1);
		put_test_bits(data, &position, 0, 4);
		put_test_bits(data, &position, 10, 5);
		put_test_bits(data, &position, 0, 1);
	}
	put_test_bits(data, &position, 0, 1);
	/* Fixed H.263 sub-QCIF or one-macroblock Sorenson skip frame. */
	for (index = 0; index < (sorenson ? 1U : 48U); index++)
		put_test_bits(data, &position, 1, 1);
	return (position + 7) / 8;
}

static void test_h263(void)
{
	guint variant;
	for (variant = 0; variant < 2; variant++) {
		DreamCodec codec;
		GError *error = NULL;
		GstCaps *caps = gst_caps_from_string(variant ? "video/x-flash-video,flvversion=1" : "video/x-h263,variant=itu,parsed=true");
		guint8 frame[32], expected[59] = { 0, 0, 1, 0xb6, 7, 0, 128, 0, 96, 1, 0 };
		gsize size = make_h263_skip_frame(frame, variant, 16, 16), cut;
		GstBuffer *input, *output;
		dream_codec_init(&codec);
		g_assert_true(dream_codec_configure(&codec, caps, &error));
		g_assert_cmpuint(codec.format, ==, 7);
		g_assert_cmpuint(codec.streamType, ==, 4);
		if (variant)
			expected[6] = expected[8] = 16;
		input = buffer_from_data(frame, size);
		GST_BUFFER_PTS(input) = 7 * GST_SECOND;
		g_assert_true(dream_codec_push(&codec, input, FALSE, &output, &error));
		g_assert_no_error(error);
		assert_buffer(output, expected, variant ? 12 : 59);
		if (variant) {
			g_assert_cmpuint(codec.width, ==, 16);
			g_assert_cmpuint(codec.height, ==, 16);
		}
		g_assert_cmpuint(GST_BUFFER_PTS(output), ==, GST_BUFFER_PTS(input));
		gst_buffer_unref(output);
		gst_buffer_unref(input);
		for (cut = 1; cut < size; cut++) {
			input = buffer_from_data(frame, cut);
			g_assert_null(dream_codec_transform(&codec, input, FALSE, &error));
			g_assert_nonnull(error);
			g_clear_error(&error);
			gst_buffer_unref(input);
		}
		dream_codec_flush(&codec);
		input = buffer_from_data(frame, size);
		output = dream_codec_transform(&codec, input, FALSE, &error);
		g_assert_no_error(error);
		assert_buffer(output, expected, variant ? 12 : 59);
		gst_buffer_unref(output);
		gst_buffer_unref(input);
		if (variant) {
			size = make_h263_skip_frame(frame, TRUE, 65535, 65535);
			input = buffer_from_data(frame, size);
			g_assert_null(dream_codec_transform(&codec, input, FALSE, &error));
			g_assert_nonnull(error);
			g_clear_error(&error);
			gst_buffer_unref(input);
		}
		gst_caps_unref(caps);
		dream_codec_clear(&codec);
	}
}

static void test_h263_mutations(void)
{
	GRand *random = g_rand_new_with_seed(0x48323633);
	guint variant, iteration;
	for (variant = 0; variant < 2; variant++) {
		DreamCodec codec;
		GError *error = NULL;
		GstCaps *caps = gst_caps_from_string(variant ? "video/x-flash-video,flvversion=1" : "video/x-h263,variant=itu,parsed=true");
		guint8 frame[32], original[32];
		gsize size = make_h263_skip_frame(original, variant, 16, 16);
		dream_codec_init(&codec);
		g_assert_true(dream_codec_configure(&codec, caps, &error));
		for (iteration = 0; iteration < 4096; iteration++) {
			GstBuffer *input, *output;
			memcpy(frame, original, sizeof(frame));
			frame[g_rand_int_range(random, 0, size)] ^= g_rand_int_range(random, 1, 256);
			input = buffer_from_data(frame, size);
			output = dream_codec_transform(&codec, input, FALSE, &error);
			if (output) {
				g_assert_no_error(error);
				gst_buffer_unref(output);
			} else {
				g_assert_nonnull(error);
				g_clear_error(&error);
			}
			gst_buffer_unref(input);
		}
		gst_caps_unref(caps);
		dream_codec_clear(&codec);
	}
	g_rand_free(random);
}

static void put_h263_test_motion(guint8 *data, guint *position, gint delta)
{
	/* H.263 Table 14 differential motion-vector VLC, including the sign. */
	switch (delta) {
	case 0: put_test_bits(data, position, 1, 1); break;
	case 1: put_test_bits(data, position, 2, 3); break;
	case -1: put_test_bits(data, position, 3, 3); break;
	case 2: put_test_bits(data, position, 2, 4); break;
	case 31: put_test_bits(data, position, 6, 13); break;
	case -31: put_test_bits(data, position, 7, 13); break;
	case -32: put_test_bits(data, position, 5, 13); break;
	default: g_assert_not_reached();
	}
}

static gsize make_h263_vector_frame(guint8 *data, gboolean annexD)
{
	static const gint deltas[8][2] = {
		{ 31, -31 }, { 0, 0 }, { 1, -1 }, { 31, -31 },
		{ 1, -1 }, { -32, 31 }, { -32, 31 }, { 0, 2 }
	};
	guint position = 0, index;
	memset(data, 0, 128);
	put_test_bits(data, &position, 1, 17);
	put_test_bits(data, &position, 0, 5);
	put_test_bits(data, &position, 7, 8);
	put_test_bits(data, &position, 16, 5);
	put_test_bits(data, &position, 1, 3); /* sub-QCIF: eight macroblocks per row */
	put_test_bits(data, &position, 1, 1); /* P picture */
	put_test_bits(data, &position, annexD ? 8 : 0, 4);
	put_test_bits(data, &position, 10, 5);
	put_test_bits(data, &position, 0, 2); /* CPM, PEI */
	for (index = 0; index < G_N_ELEMENTS(deltas); index++) {
		put_test_bits(data, &position, 0, 1); /* coded macroblock */
		put_test_bits(data, &position, 1, 1); /* INTER, CBPC=0 */
		put_test_bits(data, &position, 3, 2); /* CBPY=0 */
		put_h263_test_motion(data, &position, deltas[index][0]);
		put_h263_test_motion(data, &position, deltas[index][1]);
	}
	for (index = 8; index < 48; index++)
		put_test_bits(data, &position, 1, 1); /* remaining rows are skipped */
	return (position + 7) / 8;
}

static void test_h263_annex_d_vectors(void)
{
	static const gint expected[2][8][2] = {
		{ { 31, -31 }, { 31, -31 }, { -32, -32 }, { -1, 1 },
		  { 0, 0 }, { -32, 31 }, { 0, -2 }, { 0, 0 } },
		{ { 31, -31 }, { 31, -31 }, { 32, -32 }, { 63, -63 },
		  { 0, 0 }, { -32, 31 }, { 0, 62 }, { 0, 0 } }
	};
	DreamCodec codec;
	GstCaps *caps = gst_caps_from_string("video/x-h263,variant=itu,parsed=true,width=128,height=96");
	GError *error = NULL;
	guint pass, index, axis;
	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	/* Reuse the same codec to catch Annex D state leaking to later baseline AUs. */
	for (pass = 0; pass < 4; pass++) {
		gboolean annexD = !(pass & 1);
		guint8 frame[128], converted[187];
		gsize size = make_h263_vector_frame(frame, annexD);
		GstBuffer *input = buffer_from_data(frame, size);
		GstBuffer *output = dream_codec_transform(&codec, input, FALSE, &error);
		g_assert_no_error(error);
		g_assert_nonnull(output);
		g_assert_cmpuint(gst_buffer_get_size(output), ==, sizeof(converted));
		g_assert_cmpuint(gst_buffer_extract(output, 0, converted, sizeof(converted)), ==, sizeof(converted));
		for (index = 0; index < 8; index++) {
			g_assert_cmpuint(converted[11 + index * 17], ==, 2);
			for (axis = 0; axis < 2; axis++) {
				guint16 encoded = (guint16) (expected[annexD][index][axis] * 2) & 0x3fff;
				g_assert_cmpuint(read_be16(converted + 12 + index * 17 + axis * 2), ==, encoded);
			}
		}
		gst_buffer_unref(output);
		gst_buffer_unref(input);
		if (pass == 1)
			dream_codec_flush(&codec);
	}
	for (index = 0; index < 3; index++) {
		guint8 frame[128];
		gsize size = make_h263_vector_frame(frame, TRUE);
		GstBuffer *input;
		frame[5] |= 0x80 >> index; /* SAC, advanced prediction, PB remain rejected. */
		input = buffer_from_data(frame, size);
		g_assert_null(dream_codec_transform(&codec, input, FALSE, &error));
		g_assert_error(error, DREAM_CODEC_ERROR, DREAM_CODEC_ERROR_DATA);
		g_clear_error(&error);
		gst_buffer_unref(input);
	}
	for (index = 0; index < 3; index++) {
		GstCaps *invalid = gst_caps_from_string(index == 0 ? "video/x-h263,variant=itu" :
			(index == 1 ? "video/x-h263,variant=itu,parsed=false" : "video/x-h263,variant=other,parsed=true"));
		g_assert_false(dream_codec_configure(&codec, invalid, &error));
		g_assert_error(error, DREAM_CODEC_ERROR, DREAM_CODEC_ERROR_CAPS);
		g_clear_error(&error);
		gst_caps_unref(invalid);
	}
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}


/* Prefix fixtures exercise SMPTE 421M timing syntax, not decoded pictures. */
static GstCaps *vc1_timing_caps(gboolean capsRate)
{
	guint8 data[64] = { 0, 0, 0, 1, 0x0f };
	guint bit = 40;
	gsize size;
	GstBuffer *header;
	GstCaps *caps = gst_caps_from_string("video/x-wmv,wmvversion=3,format=WVC1,width=16,height=16");
	put_test_bits(data, &bit, 3, 2); /* Advanced profile */
	put_test_bits(data, &bit, 0, 3); /* Level */
	put_test_bits(data, &bit, 1, 2); /* 4:2:0 */
	put_test_bits(data, &bit, 0, 9); /* Frame/bit rate postprocessing hints */
	put_test_bits(data, &bit, 7, 12);
	put_test_bits(data, &bit, 7, 12);
	put_test_bits(data, &bit, 0, 4); /* Progressive, no pulldown/framecounter/interpolation */
	put_test_bits(data, &bit, 1, 1); /* Reserved */
	put_test_bits(data, &bit, 0, 1); /* PSF */
	put_test_bits(data, &bit, 1, 1); /* Display extension */
	put_test_bits(data, &bit, 15, 14);
	put_test_bits(data, &bit, 15, 14);
	put_test_bits(data, &bit, 0, 1); /* Aspect ratio absent */
	put_test_bits(data, &bit, 1, 1); /* Frame rate present */
	put_test_bits(data, &bit, 0, 1); /* Numerator/denominator form */
	put_test_bits(data, &bit, 2, 8); /* 25000 */
	put_test_bits(data, &bit, 1, 4); /* /1000 */
	put_test_bits(data, &bit, 0, 2); /* No color/HRD extension */
	size = (bit + 7) / 8;
	data[size++] = 0;
	data[size++] = 0;
	data[size++] = 1;
	data[size++] = 0x0e;
	data[size++] = 0;
	data[size++] = 0;
	header = buffer_from_data(data, size);
	gst_caps_set_simple(caps, "codec_data", GST_TYPE_BUFFER, header, NULL);
	if (capsRate)
		gst_caps_set_simple(caps, "framerate", GST_TYPE_FRACTION, 25, 1, NULL);
	gst_buffer_unref(header);
	return caps;
}

static GstBuffer *vc1_timing_frame(guint type, guint fraction, GstClockTime pts)
{
	guint8 data[8] = { 0, 0, 1, 0x0d };
	guint bit = 32, index;
	GstBuffer *frame;
	for (index = 0; index < type; index++)
		put_test_bits(data, &bit, 1, 1);
	if (type < 4)
		put_test_bits(data, &bit, 0, 1);
	if (type == 1) {
		put_test_bits(data, &bit, 0, 1); /* RNDCTRL */
		put_test_bits(data, &bit, fraction, fraction < 7 ? 3 : 7);
	}
	frame = buffer_from_data(data, (bit + 7) / 8);
	GST_BUFFER_PTS(frame) = pts;
	GST_BUFFER_DTS(frame) = 700 * GST_MSECOND;
	return frame;
}

static void test_vc1_timing(void)
{
	static const guint fractions[] = { 0, 1, 3, 5, 0x72, 0x74, 0x7a };
	guint group, index;
	for (group = 1; group <= G_N_ELEMENTS(fractions); group++) {
		DreamCodec codec;
		GstCaps *caps = vc1_timing_caps(group & 1);
		GError *error = NULL;
		GstBuffer *input, *output;
		dream_codec_init(&codec);
		g_assert_true(dream_codec_configure(&codec, caps, &error));
		input = vc1_timing_frame(2, 0, GST_SECOND);
		g_assert_true(dream_codec_push(&codec, input, TRUE, &output, &error));
		g_assert_null(output);
		gst_buffer_unref(input);
		input = vc1_timing_frame(0, 0, GST_CLOCK_TIME_NONE);
		g_assert_true(dream_codec_push(&codec, input, FALSE, &output, &error));
		g_assert_cmpuint(GST_BUFFER_PTS(output), ==, GST_SECOND);
		g_assert_cmpuint(gst_buffer_get_size(output), >, 5);
		gst_buffer_unref(input);
		gst_buffer_unref(output);
		for (index = 0; index < group; index++) {
			input = vc1_timing_frame(1, fractions[group - 1], GST_CLOCK_TIME_NONE);
			g_assert_true(dream_codec_push(&codec, input, FALSE, &output, &error));
			g_assert_cmpuint(GST_BUFFER_PTS(output), ==,
				GST_SECOND + (index ? index : group + 1) * 40 * GST_MSECOND);
			g_assert_cmpuint(GST_BUFFER_DTS(output), ==, 700 * GST_MSECOND);
			g_assert_cmpuint(GST_BUFFER_PTS(input), ==, GST_CLOCK_TIME_NONE);
			gst_buffer_unref(input);
			gst_buffer_unref(output);
		}
		input = vc1_timing_frame(0, 0, GST_CLOCK_TIME_NONE);
		g_assert_true(dream_codec_push(&codec, input, FALSE, &output, &error));
		g_assert_cmpuint(GST_BUFFER_PTS(output), ==, GST_SECOND + group * 40 * GST_MSECOND);
		gst_buffer_unref(input);
		gst_buffer_unref(output);
		g_assert_true(dream_codec_drain(&codec, &output, &error));
		g_assert_cmpuint(GST_BUFFER_PTS(output), ==, GST_SECOND + (group + 2) * 40 * GST_MSECOND);
		gst_buffer_unref(output);
		g_assert_true(dream_codec_drain(&codec, &output, &error));
		g_assert_null(output);
		g_assert_no_error(error);
		gst_caps_unref(caps);
		dream_codec_clear(&codec);
	}
}

static void test_vc1_timing_preserve_flush(void)
{
	DreamCodec codec;
	GstCaps *caps = vc1_timing_caps(TRUE);
	GError *error = NULL;
	GstBuffer *input, *output;
	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	input = vc1_timing_frame(2, 0, 3 * GST_SECOND);
	g_assert_true(dream_codec_push(&codec, input, TRUE, &output, &error));
	g_assert_null(output);
	gst_buffer_unref(input);
	input = vc1_timing_frame(0, 0, 17 * GST_SECOND);
	g_assert_true(dream_codec_push(&codec, input, FALSE, &output, &error));
	g_assert_cmpuint(GST_BUFFER_PTS(output), ==, 3 * GST_SECOND);
	gst_buffer_unref(input);
	gst_buffer_unref(output);
	input = vc1_timing_frame(1, 1, 8 * GST_SECOND);
	g_assert_true(dream_codec_push(&codec, input, FALSE, &output, &error));
	g_assert_cmpuint(GST_BUFFER_PTS(output), ==, 17 * GST_SECOND);
	gst_buffer_unref(input);
	gst_buffer_unref(output);
	dream_codec_flush(&codec);
	g_assert_true(dream_codec_drain(&codec, &output, &error));
	g_assert_null(output);
	/* No anchor survives a seek; do not invent a zero-based timestamp. */
	input = vc1_timing_frame(2, 0, GST_CLOCK_TIME_NONE);
	GST_BUFFER_DTS(input) = GST_CLOCK_TIME_NONE;
	g_assert_true(dream_codec_push(&codec, input, TRUE, &output, &error));
	g_assert_null(output);
	gst_buffer_unref(input);
	g_assert_true(dream_codec_drain(&codec, &output, &error));
	g_assert_cmpuint(GST_BUFFER_PTS(output), ==, GST_CLOCK_TIME_NONE);
	gst_buffer_unref(output);
	/* Header-only timestamps must not become a picture presentation anchor. */
	dream_codec_flush(&codec);
	{
		const GValue *value = gst_structure_get_value(gst_caps_get_structure(caps, 0), "codec_data");
		GstBuffer *headers = gst_value_get_buffer(value);
		input = gst_buffer_copy_region(headers, GST_BUFFER_COPY_ALL, 1, gst_buffer_get_size(headers) - 1);
	}
	GST_BUFFER_PTS(input) = 100 * GST_SECOND;
	g_assert_true(dream_codec_push(&codec, input, FALSE, &output, &error));
	g_assert_null(output);
	gst_buffer_unref(input);
	input = vc1_timing_frame(2, 0, GST_CLOCK_TIME_NONE);
	GST_BUFFER_DTS(input) = GST_CLOCK_TIME_NONE;
	g_assert_true(dream_codec_push(&codec, input, FALSE, &output, &error));
	g_assert_cmpuint(GST_BUFFER_PTS(output), ==, 100 * GST_SECOND);
	gst_buffer_unref(input);
	gst_buffer_unref(output);
	g_assert_true(dream_codec_drain(&codec, &output, &error));
	g_assert_cmpuint(GST_BUFFER_PTS(output), ==, GST_CLOCK_TIME_NONE);
	gst_buffer_unref(output);
	g_assert_no_error(error);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void test_vc1_timing_dts_anchor(void)
{
	DreamCodec codec;
	GstCaps *caps = vc1_timing_caps(TRUE);
	GError *error = NULL;
	GstBuffer *input, *output;
	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	input = vc1_timing_frame(2, 0, GST_CLOCK_TIME_NONE);
	g_assert_true(dream_codec_push(&codec, input, TRUE, &output, &error));
	g_assert_null(output);
	gst_buffer_unref(input);
	input = vc1_timing_frame(0, 0, GST_CLOCK_TIME_NONE);
	GST_BUFFER_DTS(input) = 740 * GST_MSECOND;
	g_assert_true(dream_codec_push(&codec, input, FALSE, &output, &error));
	g_assert_cmpuint(GST_BUFFER_PTS(output), ==, 700 * GST_MSECOND);
	gst_buffer_unref(input);
	gst_buffer_unref(output);
	input = vc1_timing_frame(1, 0, GST_CLOCK_TIME_NONE);
	GST_BUFFER_DTS(input) = 780 * GST_MSECOND;
	g_assert_true(dream_codec_push(&codec, input, FALSE, &output, &error));
	g_assert_cmpuint(GST_BUFFER_PTS(output), ==, 780 * GST_MSECOND);
	gst_buffer_unref(input);
	gst_buffer_unref(output);
	g_assert_true(dream_codec_drain(&codec, &output, &error));
	g_assert_cmpuint(GST_BUFFER_PTS(output), ==, 740 * GST_MSECOND);
	gst_buffer_unref(output);
	g_assert_no_error(error);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void test_vc1_timing_malformed(void)
{
	DreamCodec codec;
	GstCaps *caps = vc1_timing_caps(TRUE);
	GError *error = NULL;
	GstBuffer *input, *output;
	guint iteration;
	GRand *random = g_rand_new_with_seed(0x564331);
	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	for (iteration = 0; iteration < 2048; iteration++) {
		guint8 data[128] = { 0, 0, 1, 0x0d };
		guint index, size = g_rand_int_range(random, 4, sizeof(data));
		data[3] = 0x0d + iteration % 3;
		for (index = 4; index < size; index++)
			data[index] = g_rand_int(random);
		input = buffer_from_data(data, size);
		GST_BUFFER_PTS(input) = iteration * GST_SECOND;
		g_assert_true(dream_codec_push(&codec, input, FALSE, &output, &error));
		if (output) {
			g_assert_cmpuint(GST_BUFFER_PTS(output), ==, (iteration - 1) * GST_SECOND);
			gst_buffer_unref(output);
		}
		gst_buffer_unref(input);
	}
	g_assert_true(dream_codec_drain(&codec, &output, &error));
	g_assert_nonnull(output);
	gst_buffer_unref(output);
	g_assert_no_error(error);
	g_rand_free(random);
	gst_caps_unref(caps);
	dream_codec_clear(&codec);
}

static void check_legacy_fixture_frame(DreamCodec *codec, const guint8 *data,
	gsize size, guint index, guint width, guint height)
{
	GstBuffer *input = buffer_from_data(data, size), *output;
	guint8 header[11];
	GError *error = NULL;
	GST_BUFFER_PTS(input) = index * 40 * GST_MSECOND;
	output = dream_codec_transform(codec, input, FALSE, &error);
	g_assert_no_error(error);
	g_assert_nonnull(output);
	g_assert_cmpuint(gst_buffer_get_size(output), >, sizeof(header));
	g_assert_cmpuint(gst_buffer_extract(output, 0, header, sizeof(header)), ==, sizeof(header));
	g_assert_cmpuint(read_be32(header), ==, 0x1b6);
	g_assert_cmpuint(read_be16(header + 5), ==, width);
	g_assert_cmpuint(read_be16(header + 7), ==, height);
	g_assert_cmpuint(GST_BUFFER_PTS(output), ==, GST_BUFFER_PTS(input));
	gst_buffer_unref(output);
	gst_buffer_unref(input);
}

static gsize h263_picture_start(const guint8 *data, gsize size, gsize offset)
{
	while (offset + 3 <= size) {
		/* Byte-aligned 22-bit picture start code; GOB start codes differ. */
		if (!data[offset] && !data[offset + 1] && (data[offset + 2] & 0xfc) == 0x80)
			return offset;
		offset++;
	}
	return size;
}

static void test_legacy_compressed_fixtures(void)
{
	const gchar *directory = g_getenv("DREAM_CODEC_FIXTURE_DIR");
	guint variant;
	if (!directory || !*directory) {
		g_test_skip("Set DREAM_CODEC_FIXTURE_DIR to generated FFmpeg H263/Sorenson fixtures");
		return;
	}
	for (variant = 0; variant < 2; variant++) {
		DreamCodec codec;
		GstCaps *caps = gst_caps_from_string(variant ? "video/x-flash-video,flvversion=1" : "video/x-h263,variant=itu,parsed=true");
		GError *error = NULL;
		gchar *path = g_build_filename(directory, variant ? "test-sorenson.flv" : "test-h263.h263", NULL);
		gchar *contents = NULL;
		const guint8 *data;
		gsize size, offset;
		guint count = 0;
		g_assert_true(g_file_get_contents(path, &contents, &size, &error));
		g_assert_no_error(error);
		g_assert_cmpuint(size, <, 2 * 1024 * 1024);
		data = (const guint8 *) contents;
		dream_codec_init(&codec);
		g_assert_true(dream_codec_configure(&codec, caps, &error));
		if (!variant) {
			offset = h263_picture_start(data, size, 0);
			g_assert_cmpuint(offset, ==, 0);
			while (offset < size) {
				gsize next = h263_picture_start(data, size, offset + 3);
				check_legacy_fixture_frame(&codec, data + offset, next - offset, count++, 128, 96);
				offset = next;
			}
		} else {
			g_assert_cmpuint(size, >=, 13);
			g_assert_cmpmem(data, 3, "FLV", 3);
			offset = read_be32(data + 5);
			g_assert_cmpuint(offset, <=, size - 4);
			offset += 4; /* PreviousTagSize0 */
			while (offset < size) {
				gsize bodySize;
				g_assert_cmpuint(size - offset, >=, 15);
				bodySize = ((gsize) data[offset + 1] << 16) |
					((gsize) data[offset + 2] << 8) | data[offset + 3];
				g_assert_cmpuint(bodySize, <=, size - offset - 15);
				if (data[offset] == 9 && bodySize) {
					g_assert_cmpuint(data[offset + 11] & 15, ==, 2);
					check_legacy_fixture_frame(&codec, data + offset + 12, bodySize - 1, count++, 128, 96);
				}
				g_assert_cmpuint(read_be32(data + offset + 11 + bodySize), ==, bodySize + 11);
				offset += bodySize + 15;
			}
		}
		g_assert_cmpuint(count, ==, 5);
		g_test_message("Converted %u real %s fixture access units", count, variant ? "Sorenson" : "H263");
		dream_codec_clear(&codec);
		gst_caps_unref(caps);
		g_free(contents);
		g_free(path);
	}
}

static void test_h263_annex_d_fixture(void)
{
	const gchar *directory = g_getenv("DREAM_CODEC_FIXTURE_DIR");
	DreamCodec codec;
	GstCaps *caps;
	GError *error = NULL;
	gchar *path, *contents = NULL;
	const guint8 *data;
	gsize size, offset;
	guint count = 0;
	if (!directory || !*directory) {
		g_test_skip("Set DREAM_CODEC_FIXTURE_DIR to include test-annex-d.h263");
		return;
	}
	path = g_build_filename(directory, "test-annex-d.h263", NULL);
	if (!g_file_test(path, G_FILE_TEST_IS_REGULAR)) {
		g_test_skip("Optional real Annex D fixture test-annex-d.h263 is absent");
		g_free(path);
		return;
	}
	g_assert_true(g_file_get_contents(path, &contents, &size, &error));
	g_assert_no_error(error);
	g_assert_cmpuint(size, <, DREAM_CODEC_MAX_FRAME_SIZE);
	data = (const guint8 *) contents;
	g_assert_cmpuint(size, >, 6);
	g_assert_cmpuint(data[4] & 1, ==, 1); /* First picture signals Annex D. */
	caps = gst_caps_from_string("video/x-h263,variant=itu,parsed=true,width=352,height=288,annex-d=true");
	dream_codec_init(&codec);
	g_assert_true(dream_codec_configure(&codec, caps, &error));
	offset = h263_picture_start(data, size, 0);
	g_assert_cmpuint(offset, ==, 0);
	while (offset < size) {
		gsize next = h263_picture_start(data, size, offset + 3);
		check_legacy_fixture_frame(&codec, data + offset, next - offset, count++, 352, 288);
		offset = next;
	}
	g_assert_cmpuint(count, >, 1);
	g_test_message("Converted %u real Annex D fixture access units", count);
	dream_codec_clear(&codec);
	gst_caps_unref(caps);
	g_free(contents);
	g_free(path);
}

int main(int argc, char **argv)
{
	gst_init(&argc, &argv);
	g_test_init(&argc, &argv, NULL);
	g_test_add_func("/codec/avc", test_avc);
	g_test_add_func("/codec/nal-length-sizes", test_nal_length_sizes);
	g_test_add_func("/codec/hevc", test_hevc);
	g_test_add_func("/codec/inband-headers", test_inband_headers);
	g_test_add_func("/codec/avc-extension", test_avc_extension);
	g_test_add_func("/codec/elementary-zero-copy", test_elementary_zero_copy);
	g_test_add_func("/codec/mpeg", test_mpeg);
	g_test_add_func("/codec/header-truncation", test_header_truncation);
	g_test_add_func("/codec/bad-caps-atomicity", test_bad_caps_and_atomicity);
	g_test_add_func("/codec/bad-nals", test_bad_nals);
	g_test_add_func("/codec/limits", test_limits);
	g_test_add_func("/codec/expansion-limits", test_expansion_limits);
	g_test_add_func("/codec/random-records", test_random_records);
	g_test_add_func("/codec/extended-mappings", test_extended_mappings);
	g_test_add_func("/codec/vp9", test_vp9);
	g_test_add_func("/codec/divx3", test_divx3);
	g_test_add_func("/codec/wmv", test_wmv);
	g_test_add_func("/codec/vc1", test_vc1);
	g_test_add_func("/codec/jpeg-real", test_jpeg_and_real);
	g_test_add_func("/codec/extended-random", test_extended_random);
	g_test_add_func("/codec/h263", test_h263);
	g_test_add_func("/codec/h263-mutations", test_h263_mutations);
	g_test_add_func("/codec/h263-annex-d-vectors", test_h263_annex_d_vectors);
	g_test_add_func("/codec/h263-annex-d-fixture", test_h263_annex_d_fixture);
	g_test_add_func("/codec/vc1-timing", test_vc1_timing);
	g_test_add_func("/codec/vc1-timing-preserve-flush", test_vc1_timing_preserve_flush);
	g_test_add_func("/codec/vc1-timing-dts-anchor", test_vc1_timing_dts_anchor);
	g_test_add_func("/codec/vc1-timing-malformed", test_vc1_timing_malformed);
	g_test_add_func("/codec/legacy-compressed-fixtures", test_legacy_compressed_fixtures);
	return g_test_run();
}
