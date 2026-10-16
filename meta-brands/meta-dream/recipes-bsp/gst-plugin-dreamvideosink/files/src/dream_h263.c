/*
 * Copyright (c) 2014 Amlogic, Inc. All rights reserved.
 *
 * This source code is subject to the terms and conditions defined in the
 * file 'LICENSE.amlogic' which is part of this source code package.
 *
 * Description:
 */



#include "dream_h263.h"
#include "dream_codec.h"

#include <string.h>

/* Adapted for an internal candidate from hardkernel libplayer commit
 * a242536ca027738c8df423e8398435174390005a, amplayer/player/h263vld.c.
 * Changes: per-instance state, checked bit consumption and output writes,
 * bounded dimensions/table indexes, no signed left-shift UB, atomic conversion.
 * This file is NOT covered by the project's LGPL grant.
 */
#define H263_I_PICTURE 0
#define H263_P_PICTURE 1
#define MODE_INTER 0
#define MODE_INTER_Q 1
#define MODE_INTER4V 2
#define MODE_INTRA 3
#define MODE_INTRA_Q 4
#define MODE_INTER4V_Q 5
#define NO_VEC 999
#define ESCAPE 7167
#define mmax(a, b) ((a) > (b) ? (a) : (b))
#define mmin(a, b) ((a) < (b) ? (a) : (b))
#define msign(a) ((a) < 0 ? -1 : 1)
#define mabs(a) ((a) < 0 ? -(a) : (a))

typedef struct { int val, len; } VLCtab;

struct DreamH263 {
	int width, height, source_format, optional_custom_PCF;
	int top_mv[514][2], left_mv[2][2], mv[4][2];
	gsize inputSize, outputSize, outputCapacity;
	guint8 *output;
	gboolean failed;
};

static guint showbits(DreamH263 *ctx, int count, int byte, int bit, const guint8 *input)
{
	gsize position;
	guint result = 0;
	int index;

	if (ctx->failed || count < 1 || count > 32 || byte < 0 || bit < 0 || bit > 7) {
		ctx->failed = TRUE;
		return 0;
	}
	position = (gsize) byte * 8 + 7 - bit;
	/* VLC lookahead may inspect padding but never dereference beyond input.
	 * Only consuming actual bits is permitted by flushbits().
	 */
	for (index = 0; index < count; index++, position++) {
		result <<= 1;
		if (position / 8 < ctx->inputSize)
			result |= (input[position / 8] >> (7 - position % 8)) & 1;
	}
	return result;
}

static void flushbits(DreamH263 *ctx, int count, int *byte, int *bit)
{
	gsize position = (gsize) *byte * 8 + 7 - *bit;
	if (ctx->failed || count < 1 || position > ctx->inputSize * 8 ||
		(gsize) count > ctx->inputSize * 8 - position) {
		ctx->failed = TRUE;
		return;
	}
	position += count;
	*byte = position / 8;
	*bit = 7 - position % 8;
}

static guint getbits(DreamH263 *ctx, int count, int *byte, int *bit, const guint8 *input)
{
	guint value = showbits(ctx, count, *byte, *bit, input);
	flushbits(ctx, count, byte, bit);
	return value;
}

static int startcode(DreamH263 *ctx, int *byte, int *bit, const guint8 *input, int length)
{
	(void) length;
	while (!ctx->failed && showbits(ctx, 17, *byte, *bit, input) != 1)
		flushbits(ctx, 1, byte, bit);
	return ctx->failed ? -1 : 0;
}

static gboolean put_byte(DreamH263 *ctx, guint value)
{
	if (ctx->failed || ctx->outputSize == ctx->outputCapacity) {
		ctx->failed = TRUE;
		return FALSE;
	}
	if (ctx->output)
		ctx->output[ctx->outputSize] = value;
	ctx->outputSize++;
	return TRUE;
}
static const VLCtab MCBPCtabintra[] = {
	{ -1, 0},
	{20, 6}, {36, 6}, {52, 6}, { 4, 4}, { 4, 4}, { 4, 4},
	{ 4, 4}, {19, 3}, {19, 3}, {19, 3}, {19, 3}, {19, 3},
	{19, 3}, {19, 3}, {19, 3}, {35, 3}, {35, 3}, {35, 3},
	{35, 3}, {35, 3}, {35, 3}, {35, 3}, {35, 3}, {51, 3},
	{51, 3}, {51, 3}, {51, 3}, {51, 3}, {51, 3}, {51, 3},
	{51, 3},
};

static const VLCtab MCBPCtab0[] = {
	{ -1, 0},
	{255, 9}, {52, 9}, {36, 9}, {20, 9}, {49, 9}, {35, 8}, {35, 8}, {19, 8}, {19, 8},
	{ 50, 8}, {50, 8}, {51, 7}, {51, 7}, {51, 7}, {51, 7}, {34, 7}, {34, 7}, {34, 7},
	{ 34, 7}, {18, 7}, {18, 7}, {18, 7}, {18, 7}, {33, 7}, {33, 7}, {33, 7}, {33, 7},
	{ 17, 7}, {17, 7}, {17, 7}, {17, 7}, { 4, 6}, { 4, 6}, { 4, 6}, { 4, 6}, { 4, 6},
	{  4, 6}, { 4, 6}, { 4, 6}, {48, 6}, {48, 6}, {48, 6}, {48, 6}, {48, 6}, {48, 6},
	{ 48, 6}, {48, 6}, { 3, 5}, { 3, 5}, { 3, 5}, { 3, 5}, { 3, 5}, { 3, 5}, { 3, 5},
	{  3, 5}, { 3, 5}, { 3, 5}, { 3, 5}, { 3, 5}, { 3, 5}, { 3, 5}, { 3, 5}, { 3, 5},
	{ 32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4},
	{ 32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4},
	{ 32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4},
	{ 32, 4}, {32, 4}, {32, 4}, {32, 4}, {32, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4},
	{ 16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4},
	{ 16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4},
	{ 16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4}, {16, 4},
	{ 16, 4}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3},
	{  2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3},
	{  2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3},
	{  2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3},
	{  2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3},
	{  2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3},
	{  2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3}, { 2, 3},
	{  2, 3}, { 2, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3},
	{  1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3},
	{  1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3},
	{  1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3},
	{  1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3},
	{  1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3},
	{  1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3}, { 1, 3},
	{  1, 3}, { 1, 3}, { 1, 3},
};

static const VLCtab MCBPCtab1[] = {
	{5, 11}, {5, 11},  {5, 11}, {5, 11}, {21, 13}, {21, 13}, {37, 13}, {53, 13},
};

static const VLCtab CBPYtab[48] = {
	{ -1, 0}, { -1, 0}, { 9, 6}, { 6, 6}, { 7, 5}, { 7, 5}, {11, 5}, {11, 5},
	{13, 5}, {13, 5}, {14, 5}, {14, 5}, {15, 4}, {15, 4}, {15, 4}, {15, 4},
	{ 3, 4}, { 3, 4}, { 3, 4}, { 3, 4}, { 5, 4}, { 5, 4}, { 5, 4}, { 5, 4},
	{ 1, 4}, { 1, 4}, { 1, 4}, { 1, 4}, {10, 4}, {10, 4}, {10, 4}, {10, 4},
	{ 2, 4}, { 2, 4}, { 2, 4}, { 2, 4}, {12, 4}, {12, 4}, {12, 4}, {12, 4},
	{ 4, 4}, { 4, 4}, { 4, 4}, { 4, 4}, { 8, 4}, { 8, 4}, { 8, 4}, { 8, 4},
};

static const int DQ_tab[4] = { -1, -2, 1, 2};

static const VLCtab TMNMVtab0[] = {
	{ 3, 4}, {61, 4}, { 2, 3}, { 2, 3}, {62, 3}, {62, 3},
	{ 1, 2}, { 1, 2}, { 1, 2}, { 1, 2}, {63, 2}, {63, 2}, {63, 2}, {63, 2}
};

static const VLCtab TMNMVtab1[] = {
	{12, 10}, {52, 10}, {11, 10}, {53, 10}, {10, 9}, {10, 9},
	{54, 9}, {54, 9}, { 9, 9}, { 9, 9}, {55, 9}, {55, 9},
	{ 8, 9}, { 8, 9}, {56, 9}, {56, 9}, { 7, 7}, { 7, 7},
	{ 7, 7}, { 7, 7}, { 7, 7}, { 7, 7}, { 7, 7}, { 7, 7},
	{57, 7}, {57, 7}, {57, 7}, {57, 7}, {57, 7}, {57, 7},
	{57, 7}, {57, 7}, { 6, 7}, { 6, 7}, { 6, 7}, { 6, 7},
	{ 6, 7}, { 6, 7}, { 6, 7}, { 6, 7}, {58, 7}, {58, 7},
	{58, 7}, {58, 7}, {58, 7}, {58, 7}, {58, 7}, {58, 7},
	{ 5, 7}, { 5, 7}, { 5, 7}, { 5, 7}, { 5, 7}, { 5, 7},
	{ 5, 7}, { 5, 7}, {59, 7}, {59, 7}, {59, 7}, {59, 7},
	{59, 7}, {59, 7}, {59, 7}, {59, 7}, { 4, 6}, { 4, 6},
	{ 4, 6}, { 4, 6}, { 4, 6}, { 4, 6}, { 4, 6}, { 4, 6},
	{ 4, 6}, { 4, 6}, { 4, 6}, { 4, 6}, { 4, 6}, { 4, 6},
	{ 4, 6}, { 4, 6}, {60, 6}, {60, 6}, {60, 6}, {60, 6},
	{60, 6}, {60, 6}, {60, 6}, {60, 6}, {60, 6}, {60, 6},
	{60, 6}, {60, 6}, {60, 6}, {60, 6}, {60, 6}, {60, 6}
};

static const VLCtab TMNMVtab2[] = {
	{32, 12}, {31, 12}, {33, 12}, {30, 11}, {30, 11}, {34, 11},
	{34, 11}, {29, 11}, {29, 11}, {35, 11}, {35, 11}, {28, 11},
	{28, 11}, {36, 11}, {36, 11}, {27, 11}, {27, 11}, {37, 11},
	{37, 11}, {26, 11}, {26, 11}, {38, 11}, {38, 11}, {25, 11},
	{25, 11}, {39, 11}, {39, 11}, {24, 10}, {24, 10}, {24, 10},
	{24, 10}, {40, 10}, {40, 10}, {40, 10}, {40, 10}, {23, 10},
	{23, 10}, {23, 10}, {23, 10}, {41, 10}, {41, 10}, {41, 10},
	{41, 10}, {22, 10}, {22, 10}, {22, 10}, {22, 10}, {42, 10},
	{42, 10}, {42, 10}, {42, 10}, {21, 10}, {21, 10}, {21, 10},
	{21, 10}, {43, 10}, {43, 10}, {43, 10}, {43, 10}, {20, 10},
	{20, 10}, {20, 10}, {20, 10}, {44, 10}, {44, 10}, {44, 10},
	{44, 10}, {19, 10}, {19, 10}, {19, 10}, {19, 10}, {45, 10},
	{45, 10}, {45, 10}, {45, 10}, {18, 10}, {18, 10}, {18, 10},
	{18, 10}, {46, 10}, {46, 10}, {46, 10}, {46, 10}, {17, 10},
	{17, 10}, {17, 10}, {17, 10}, {47, 10}, {47, 10}, {47, 10},
	{47, 10}, {16, 10}, {16, 10}, {16, 10}, {16, 10}, {48, 10},
	{48, 10}, {48, 10}, {48, 10}, {15, 10}, {15, 10}, {15, 10},
	{15, 10}, {49, 10}, {49, 10}, {49, 10}, {49, 10}, {14, 10},
	{14, 10}, {14, 10}, {14, 10}, {50, 10}, {50, 10}, {50, 10},
	{50, 10}, {13, 10}, {13, 10}, {13, 10}, {13, 10}, {51, 10},
	{51, 10}, {51, 10}, {51, 10}
};

static const VLCtab DCT3Dtab0[] = {
	{4225, 7}, {4209, 7}, {4193, 7}, {4177, 7}, { 193, 7}, { 177, 7},
	{ 161, 7}, {   4, 7}, {4161, 6}, {4161, 6}, {4145, 6}, {4145, 6},
	{4129, 6}, {4129, 6}, {4113, 6}, {4113, 6}, { 145, 6}, { 145, 6},
	{ 129, 6}, { 129, 6}, { 113, 6}, { 113, 6}, {  97, 6}, {  97, 6},
	{  18, 6}, {  18, 6}, {   3, 6}, {   3, 6}, {  81, 5}, {  81, 5},
	{  81, 5}, {  81, 5}, {  65, 5}, {  65, 5}, {  65, 5}, {  65, 5},
	{  49, 5}, {  49, 5}, {  49, 5}, {  49, 5}, {4097, 4}, {4097, 4},
	{4097, 4}, {4097, 4}, {4097, 4}, {4097, 4}, {4097, 4}, {4097, 4},
	{   1, 2}, {   1, 2}, {   1, 2}, {   1, 2}, {   1, 2}, {   1, 2},
	{   1, 2}, {   1, 2}, {   1, 2}, {   1, 2}, {   1, 2}, {   1, 2},
	{   1, 2}, {   1, 2}, {   1, 2}, {   1, 2}, {   1, 2}, {   1, 2},
	{   1, 2}, {   1, 2}, {   1, 2}, {   1, 2}, {   1, 2}, {   1, 2},
	{   1, 2}, {   1, 2}, {   1, 2}, {   1, 2}, {   1, 2}, {   1, 2},
	{   1, 2}, {   1, 2}, {  17, 3}, {  17, 3}, {  17, 3}, {  17, 3},
	{  17, 3}, {  17, 3}, {  17, 3}, {  17, 3}, {  17, 3}, {  17, 3},
	{  17, 3}, {  17, 3}, {  17, 3}, {  17, 3}, {  17, 3}, {  17, 3},
	{  33, 4}, {  33, 4}, {  33, 4}, {  33, 4}, {  33, 4}, {  33, 4},
	{  33, 4}, {  33, 4}, {   2, 4}, {   2, 4}, {   2, 4}, {   2, 4},
	{   2, 4}, {   2, 4}, {   2, 4}, {   2, 4},
};


static const VLCtab DCT3Dtab1[] = {
	{   9, 10}, {   8, 10}, {4481, 9}, {4481, 9}, {4465, 9}, {4465, 9},
	{4449, 9}, {4449, 9}, {4433, 9}, {4433, 9}, {4417, 9}, {4417, 9},
	{4401, 9}, {4401, 9}, {4385, 9}, {4385, 9}, {4369, 9}, {4369, 9},
	{4098, 9}, {4098, 9}, { 353, 9}, { 353, 9}, { 337, 9}, { 337, 9},
	{ 321, 9}, { 321, 9}, { 305, 9}, { 305, 9}, { 289, 9}, { 289, 9},
	{ 273, 9}, { 273, 9}, { 257, 9}, { 257, 9}, { 241, 9}, { 241, 9},
	{  66, 9}, {  66, 9}, {  50, 9}, {  50, 9}, {   7, 9}, {   7, 9},
	{   6, 9}, {   6, 9}, {4353, 8}, {4353, 8}, {4353, 8}, {4353, 8},
	{4337, 8}, {4337, 8}, {4337, 8}, {4337, 8}, {4321, 8}, {4321, 8},
	{4321, 8}, {4321, 8}, {4305, 8}, {4305, 8}, {4305, 8}, {4305, 8},
	{4289, 8}, {4289, 8}, {4289, 8}, {4289, 8}, {4273, 8}, {4273, 8},
	{4273, 8}, {4273, 8}, {4257, 8}, {4257, 8}, {4257, 8}, {4257, 8},
	{4241, 8}, {4241, 8}, {4241, 8}, {4241, 8}, { 225, 8}, { 225, 8},
	{ 225, 8}, { 225, 8}, { 209, 8}, { 209, 8}, { 209, 8}, { 209, 8},
	{  34, 8}, {  34, 8}, {  34, 8}, {  34, 8}, {  19, 8}, {  19, 8},
	{  19, 8}, {  19, 8}, {   5, 8}, {   5, 8}, {   5, 8}, {   5, 8},
};


static const VLCtab DCT3Dtab2[] = {
	{4114, 11}, {4114, 11}, {4099, 11}, {4099, 11}, {  11, 11}, {  11, 11},
	{  10, 11}, {  10, 11}, {4545, 10}, {4545, 10}, {4545, 10}, {4545, 10},
	{4529, 10}, {4529, 10}, {4529, 10}, {4529, 10}, {4513, 10}, {4513, 10},
	{4513, 10}, {4513, 10}, {4497, 10}, {4497, 10}, {4497, 10}, {4497, 10},
	{ 146, 10}, { 146, 10}, { 146, 10}, { 146, 10}, { 130, 10}, { 130, 10},
	{ 130, 10}, { 130, 10}, { 114, 10}, { 114, 10}, { 114, 10}, { 114, 10},
	{  98, 10}, {  98, 10}, {  98, 10}, {  98, 10}, {  82, 10}, {  82, 10},
	{  82, 10}, {  82, 10}, {  51, 10}, {  51, 10}, {  51, 10}, {  51, 10},
	{  35, 10}, {  35, 10}, {  35, 10}, {  35, 10}, {  20, 10}, {  20, 10},
	{  20, 10}, {  20, 10}, {  12, 11}, {  12, 11}, {  21, 11}, {  21, 11},
	{ 369, 11}, { 369, 11}, { 385, 11}, { 385, 11}, {4561, 11}, {4561, 11},
	{4577, 11}, {4577, 11}, {4593, 11}, {4593, 11}, {4609, 11}, {4609, 11},
	{  22, 12}, {  36, 12}, {  67, 12}, {  83, 12}, {  99, 12}, { 162, 12},
	{ 401, 12}, { 417, 12}, {4625, 12}, {4641, 12}, {4657, 12}, {4673, 12},
	{4689, 12}, {4705, 12}, {4721, 12}, {4737, 12}, {7167, 7}, {7167, 7},
	{7167, 7}, {7167, 7}, {7167, 7}, {7167, 7}, {7167, 7}, {7167, 7},
	{7167, 7}, {7167, 7}, {7167, 7}, {7167, 7}, {7167, 7}, {7167, 7},
	{7167, 7}, {7167, 7}, {7167, 7}, {7167, 7}, {7167, 7}, {7167, 7},
	{7167, 7}, {7167, 7}, {7167, 7}, {7167, 7}, {7167, 7}, {7167, 7},
	{7167, 7}, {7167, 7}, {7167, 7}, {7167, 7}, {7167, 7}, {7167, 7},
};

static const int roundtab[16] = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2};

static int get_pred_mv(DreamH263 *ctx, int x, int k, int comp)
{
	int mv1, mv2, mv3;

	if (k == 0) {
		mv1 = ctx->left_mv[0][comp];
		mv2 = ctx->top_mv[x * 2][comp];
		mv3 = ctx->top_mv[(x + 1) * 2][comp];
	} else if (k == 1) {
		mv1 = ctx->mv[0][comp];
		mv2 = ctx->top_mv[x * 2 + 1][comp];
		mv3 = ctx->top_mv[(x + 1) * 2][comp];
	} else if (k == 2) {
		mv1 = ctx->left_mv[1][comp];
		mv2 = ctx->mv[0][comp];
		mv3 = ctx->mv[1][comp];
	} else {
		mv1 = ctx->mv[2][comp];
		mv2 = ctx->mv[0][comp];
		mv3 = ctx->mv[1][comp];
	}

	if (mv2 == NO_VEC) {
		mv2 = mv3 = mv1;
	}

	return mv1 + mv2 + mv3 - mmax(mv1, mmax(mv2, mv3)) - mmin(mv1, mmin(mv2, mv3));
}

static int motion_decode(int vec, int pmv, gboolean long_vectors)
{
	if (vec > 31) {
		vec -= 64;
	}

	vec += pmv;

	if (long_vectors) {
		/* Annex D extends the range according to the predicted vector.
		 * H.263+ UMV uses a different code and is not enabled here.
		 */
		if (pmv < -31 && vec < -63)
			vec += 64;
		if (pmv > 32 && vec > 63)
			vec -= 64;
	} else {
		if (vec > 31)
			vec -= 64;
		if (vec < -32)
			vec += 64;
	}

	return vec;
}

static gsize h263_convert(DreamH263 *ctx, const guint8 *inbuf, int inbuf_len, int s263)
{
	int byte_index = 0, bit_index = 7;
	int i, j, k, comp, temp, h263_version, pict_type, quant, mb_width, mb_height;
	int MCBPC, Mode, CBPY, CBP, DQUANT, pred, run, last, level, val, sign, format_bit, coeff_count;
	gboolean long_vectors = FALSE;
	const VLCtab *tab;

	if (!put_byte(ctx, 0)) return 0;
	if (!put_byte(ctx, 0)) return 0;
	if (!put_byte(ctx, 1)) return 0;
	if (!put_byte(ctx, 0xb6)) return 0;

	if (getbits(ctx, 17, &byte_index, &bit_index, inbuf) != 1) return 0;
	h263_version = getbits(ctx, 5, &byte_index, &bit_index, inbuf);
	if (s263 && h263_version > 1) return 0;
	if (!put_byte(ctx, getbits(ctx, 8, &byte_index, &bit_index, inbuf))) return 0;

	if (s263 == 0) {
		temp = getbits(ctx, 5, &byte_index, &bit_index, inbuf);
		if (temp != 16) {
			return 0;
		}

		temp = getbits(ctx, 3, &byte_index, &bit_index, inbuf);

		if (temp == 7) {
			int UFEP, rounding_type;

			UFEP = getbits(ctx, 3, &byte_index, &bit_index, inbuf);
			if (UFEP >= 2) {
				return 0;
			}

			if (UFEP == 1) {
				ctx->source_format = getbits(ctx, 3, &byte_index, &bit_index, inbuf);
				ctx->optional_custom_PCF = getbits(ctx, 1, &byte_index, &bit_index, inbuf);
				temp = getbits(ctx, 14, &byte_index, &bit_index, inbuf);
				if (temp != 8) {
					return 0;
				}
			}

			pict_type = getbits(ctx, 3, &byte_index, &bit_index, inbuf);
			if (pict_type >= 2) {
				return 0;
			}

			temp = getbits(ctx, 2, &byte_index, &bit_index, inbuf);
			if (temp != 0) {
				return 0;
			}

			rounding_type = getbits(ctx, 1, &byte_index, &bit_index, inbuf);

			temp = getbits(ctx, 3, &byte_index, &bit_index, inbuf);
			if (temp != 1) {
				return 0;
			}

			temp = getbits(ctx, 1, &byte_index, &bit_index, inbuf);
			if (temp != 0) {
				return 0;
			}

			if (UFEP == 1) {
				switch (ctx->source_format) {
				case 1:
					ctx->width = 128;
					ctx->height = 96;
					break;
				case 2:
					ctx->width = 176;
					ctx->height = 144;
					break;
				case 3:
					ctx->width = 352;
					ctx->height = 288;
					break;
				case 4:
					ctx->width = 704;
					ctx->height = 576;
					break;
				case 5:
					ctx->width = 1408;
					ctx->height = 1152;
					break;
				case 6:
					temp = getbits(ctx, 4, &byte_index, &bit_index, inbuf);
					ctx->width = getbits(ctx, 9, &byte_index, &bit_index, inbuf);
					ctx->width = (ctx->width + 1) * 4;
					getbits(ctx, 1, &byte_index, &bit_index, inbuf);
					ctx->height = getbits(ctx, 9, &byte_index, &bit_index, inbuf);
					ctx->height = ctx->height * 4;

					if (temp == 15) {
						getbits(ctx, 16, &byte_index, &bit_index, inbuf);
					}

					break;
				default:
					return 0;
				}
			}

			if (!put_byte(ctx, ctx->width >> 8)) return 0;
			if (!put_byte(ctx, ctx->width)) return 0;
			if (!put_byte(ctx, ctx->height >> 8)) return 0;
			if (!put_byte(ctx, ctx->height)) return 0;
			if (!put_byte(ctx, pict_type)) return 0;
			if (!put_byte(ctx, rounding_type)) return 0;

			if (ctx->optional_custom_PCF) {
				getbits(ctx, 10, &byte_index, &bit_index, inbuf);
			}

			quant = getbits(ctx, 5, &byte_index, &bit_index, inbuf);
		} else {
			switch (temp) {
			case 1:
				ctx->width = 128;
				ctx->height = 96;
				break;
			case 2:
				ctx->width = 176;
				ctx->height = 144;
				break;
			case 3:
				ctx->width = 352;
				ctx->height = 288;
				break;
			case 4:
				ctx->width = 704;
				ctx->height = 576;
				break;
			case 5:
				ctx->width = 1408;
				ctx->height = 1152;
				break;
			default:
				return 0;
			}

			if (!put_byte(ctx, ctx->width >> 8)) return 0;
			if (!put_byte(ctx, ctx->width)) return 0;
			if (!put_byte(ctx, ctx->height >> 8)) return 0;
			if (!put_byte(ctx, ctx->height)) return 0;

			pict_type = getbits(ctx, 1, &byte_index, &bit_index, inbuf);
			if (!put_byte(ctx, pict_type)) return 0;
			if (!put_byte(ctx, 0)) return 0;

			long_vectors = getbits(ctx, 1, &byte_index, &bit_index, inbuf);
			/* SAC, advanced prediction and PB frames remain unsupported. */
			temp = getbits(ctx, 3, &byte_index, &bit_index, inbuf);
			if (temp != 0) {
				return 0;
			}

			quant = getbits(ctx, 5, &byte_index, &bit_index, inbuf);

			temp = getbits(ctx, 1, &byte_index, &bit_index, inbuf);
			if (temp != 0) {
				return 0;
			}
		}
	} else {
		temp = getbits(ctx, 3, &byte_index, &bit_index, inbuf);

		switch (temp) {
		case 0:
			ctx->width = getbits(ctx, 8, &byte_index, &bit_index, inbuf);
			ctx->height = getbits(ctx, 8, &byte_index, &bit_index, inbuf);
			break;
		case 1:
			ctx->width = getbits(ctx, 16, &byte_index, &bit_index, inbuf);
			ctx->height = getbits(ctx, 16, &byte_index, &bit_index, inbuf);
			break;
		case 2:
			ctx->width = 352;
			ctx->height = 288;
			break;
		case 3:
			ctx->width = 176;
			ctx->height = 144;
			break;
		case 4:
			ctx->width = 128;
			ctx->height = 96;
			break;
		case 5:
			ctx->width = 320;
			ctx->height = 240;
			break;
		case 6:
			ctx->width = 160;
			ctx->height = 120;
			break;
		default:
			return 0;
		}

		if (!put_byte(ctx, ctx->width >> 8)) return 0;
		if (!put_byte(ctx, ctx->width)) return 0;
		if (!put_byte(ctx, ctx->height >> 8)) return 0;
		if (!put_byte(ctx, ctx->height)) return 0;

		pict_type = getbits(ctx, 2, &byte_index, &bit_index, inbuf);
		if (pict_type == 3) {
			return 0;
		}

		if (!put_byte(ctx, pict_type)) return 0;
		if (!put_byte(ctx, 0)) return 0;

		if (pict_type == 2) {
			pict_type = 1;
		}

		getbits(ctx, 1, &byte_index, &bit_index, inbuf);
		quant = getbits(ctx, 5, &byte_index, &bit_index, inbuf);
	}

	temp = getbits(ctx, 1, &byte_index, &bit_index, inbuf);
	while (temp == 1) {
		getbits(ctx, 8, &byte_index, &bit_index, inbuf);
		temp = getbits(ctx, 1, &byte_index, &bit_index, inbuf);
	}

	if (ctx->failed || ctx->width < 1 || ctx->width > 4096 || ctx->height < 1 || ctx->height > 4096 || quant < 1 || quant > 31) return 0;
	mb_width = (ctx->width + 15) / 16;
	mb_height = (ctx->height + 15) / 16;

	for (k = 0 ; k < mb_width * 2 ; k++)
		for (comp = 0 ; comp < 2 ; comp++) {
			ctx->top_mv[k][comp] = NO_VEC;
		}

	for (k = mb_width * 2 ; k < mb_width * 2 + 2 ; k++)
		for (comp = 0 ; comp < 2 ; comp++) {
			ctx->top_mv[k][comp] = 0;
		}

	for (i = 0 ; i < mb_height ; i++) {
		for (k = 0 ; k < 2 ; k++)
			for (comp = 0 ; comp < 2 ; comp++) {
				ctx->left_mv[k][comp] = 0;
			}

		for (j = 0 ; j < mb_width ; j++) {
			if (showbits(ctx, 16, byte_index, bit_index, inbuf) == 0) {
				if (startcode(ctx, &byte_index, &bit_index, inbuf, inbuf_len) < 0) {
					return 0;
				}

				getbits(ctx, 17, &byte_index, &bit_index, inbuf);
				getbits(ctx, 5, &byte_index, &bit_index, inbuf);
				getbits(ctx, 2, &byte_index, &bit_index, inbuf);
				quant = getbits(ctx, 5, &byte_index, &bit_index, inbuf);

				for (k = 0 ; k < mb_width * 2 ; k++)
					for (comp = 0 ; comp < 2 ; comp++) {
						ctx->top_mv[k][comp] = NO_VEC;
					}

				for (k = mb_width * 2 ; k < mb_width * 2 + 2 ; k++)
					for (comp = 0 ; comp < 2 ; comp++) {
						ctx->top_mv[k][comp] = 0;
					}

				for (k = 0 ; k < 2 ; k++)
					for (comp = 0 ; comp < 2 ; comp++) {
						ctx->left_mv[k][comp] = 0;
					}
			}

			if (byte_index > inbuf_len || (byte_index == inbuf_len && bit_index < 7)) {
				return 0;
			}

			if (pict_type == H263_P_PICTURE) {
				if (getbits(ctx, 1, &byte_index, &bit_index, inbuf) == 1) {
					if (!put_byte(ctx, 0)) return 0;
					ctx->top_mv[j * 2][0] = ctx->top_mv[j * 2][1] = ctx->top_mv[j * 2 + 1][0] = ctx->top_mv[j * 2 + 1][1] = 0;
					ctx->left_mv[0][0] = ctx->left_mv[0][1] = ctx->left_mv[1][0] = ctx->left_mv[1][1] = 0;
					continue;
				}
			}

			if (pict_type == H263_I_PICTURE) {
				temp = showbits(ctx, 9, byte_index, bit_index, inbuf);

				if (temp == 1) {
					flushbits(ctx, 9, &byte_index, &bit_index);
					j--;
					continue;
				}

				if (temp < 8) {
					return 0;
				}

				temp >>= 3;

				if (temp >= 32) {
					flushbits(ctx, 1, &byte_index, &bit_index);
					MCBPC = 3;
				} else {
					flushbits(ctx, MCBPCtabintra[temp].len, &byte_index, &bit_index);
					MCBPC = MCBPCtabintra[temp].val;
				}
			} else {
				temp = showbits(ctx, 13, byte_index, bit_index, inbuf);

				if (temp >> 4 == 1) {
					flushbits(ctx, 9, &byte_index, &bit_index);
					j--;
					continue;
				}

				if (temp == 0) {
					return 0;
				}

				if (temp >= 4096) {
					flushbits(ctx, 1, &byte_index, &bit_index);
					MCBPC = 0;
				} else if (temp >= 16) {
					flushbits(ctx, MCBPCtab0[temp >> 4].len, &byte_index, &bit_index);
					MCBPC = MCBPCtab0[temp >> 4].val;
				} else {
					if (temp < 8) return 0;
					flushbits(ctx, MCBPCtab1[temp - 8].len, &byte_index, &bit_index);
					MCBPC = MCBPCtab1[temp - 8].val;
				}
			}

			if (MCBPC == 255) {
				j--;
				continue;
			}

			if (ctx->failed || MCBPC < 0) return 0;
			Mode = MCBPC & 7;
			if (Mode > MODE_INTER4V_Q) return 0;
			temp = showbits(ctx, 6, byte_index, bit_index, inbuf);

			if (temp < 2) {
				return 0;
			}

			if (temp >= 48) {
				flushbits(ctx, 2, &byte_index, &bit_index);
				CBPY = 0;
			} else {
				flushbits(ctx, CBPYtab[temp].len, &byte_index, &bit_index);
				CBPY = CBPYtab[temp].val;
			}

			if (Mode == MODE_INTRA || Mode == MODE_INTRA_Q) {
				CBPY = CBPY ^ 15;
			}

			CBP = (CBPY << 2) | (MCBPC >> 4);

			if (Mode == MODE_INTER_Q || Mode == MODE_INTRA_Q || Mode == MODE_INTER4V_Q) {
				DQUANT = getbits(ctx, 2, &byte_index, &bit_index, inbuf);
				quant += DQ_tab[DQUANT];
			}

			if (quant > 31 || quant < 1) {
				return 0;
			}

			if (Mode == MODE_INTRA || Mode == MODE_INTRA_Q) {
				if (!put_byte(ctx, 1)) return 0;
				ctx->top_mv[j * 2][0] = ctx->top_mv[j * 2][1] = ctx->top_mv[j * 2 + 1][0] = ctx->top_mv[j * 2 + 1][1] = 0;
				ctx->left_mv[0][0] = ctx->left_mv[0][1] = ctx->left_mv[1][0] = ctx->left_mv[1][1] = 0;
			} else if (Mode == MODE_INTER || Mode == MODE_INTER_Q) {
				if (!put_byte(ctx, 2)) return 0;

				for (comp = 0 ; comp < 2 ; comp++) {
					pred = get_pred_mv(ctx, j, 0, comp);

					if (getbits(ctx, 1, &byte_index, &bit_index, inbuf)) {
						ctx->mv[0][comp] = 0;
					} else {
						temp = showbits(ctx, 12, byte_index, bit_index, inbuf);

						if (temp >= 512) {
							temp = (temp >> 8) - 2;
							flushbits(ctx, TMNMVtab0[temp].len, &byte_index, &bit_index);
							ctx->mv[0][comp] = TMNMVtab0[temp].val;
						} else if (temp >= 128) {
							temp = (temp >> 2) - 32;
							flushbits(ctx, TMNMVtab1[temp].len, &byte_index, &bit_index);
							ctx->mv[0][comp] = TMNMVtab1[temp].val;
						} else {
							temp -= 5;

							if (temp < 0) {
								return 0;
							}

							flushbits(ctx, TMNMVtab2[temp].len, &byte_index, &bit_index);
							ctx->mv[0][comp] = TMNMVtab2[temp].val;
						}
					}

					ctx->mv[0][comp] = motion_decode(ctx->mv[0][comp], pred, long_vectors);

					if (!put_byte(ctx, (ctx->mv[0][comp] >> 7) & 0x3f)) return 0;
					if (!put_byte(ctx, ctx->mv[0][comp] * 2)) return 0;
				}

				ctx->top_mv[j * 2][0] = ctx->top_mv[j * 2 + 1][0] = ctx->mv[0][0];
				ctx->top_mv[j * 2][1] = ctx->top_mv[j * 2 + 1][1] = ctx->mv[0][1];
				ctx->left_mv[0][0] = ctx->left_mv[1][0] = ctx->mv[0][0];
				ctx->left_mv[0][1] = ctx->left_mv[1][1] = ctx->mv[0][1];
			} else {
				int sum, dx, dy;

				if (!put_byte(ctx, 3)) return 0;

				for (k = 0 ; k < 4 ; k++) {
					for (comp = 0 ; comp < 2 ; comp++) {
						pred = get_pred_mv(ctx, j, k, comp);

						if (getbits(ctx, 1, &byte_index, &bit_index, inbuf)) {
							ctx->mv[k][comp] = 0;
						} else {
							temp = showbits(ctx, 12, byte_index, bit_index, inbuf);

							if (temp >= 512) {
								temp = (temp >> 8) - 2;
								flushbits(ctx, TMNMVtab0[temp].len, &byte_index, &bit_index);
								ctx->mv[k][comp] = TMNMVtab0[temp].val;
							} else if (temp >= 128) {
								temp = (temp >> 2) - 32;
								flushbits(ctx, TMNMVtab1[temp].len, &byte_index, &bit_index);
								ctx->mv[k][comp] = TMNMVtab1[temp].val;
							} else {
								temp -= 5;

								if (temp < 0) {
									return 0;
								}

								flushbits(ctx, TMNMVtab2[temp].len, &byte_index, &bit_index);
								ctx->mv[k][comp] = TMNMVtab2[temp].val;
							}
						}

						ctx->mv[k][comp] = motion_decode(ctx->mv[k][comp], pred, long_vectors);

						if (!put_byte(ctx, (ctx->mv[k][comp] >> 7) & 0x3f)) return 0;
						if (!put_byte(ctx, ctx->mv[k][comp] * 2)) return 0;
					}
				}

				sum = ctx->mv[0][0] + ctx->mv[1][0] + ctx->mv[2][0] + ctx->mv[3][0];
				dx = msign(sum) * (roundtab[mabs(sum) % 16] + (mabs(sum) / 16) * 2);
				sum = ctx->mv[0][1] + ctx->mv[1][1] + ctx->mv[2][1] + ctx->mv[3][1];
				dy = msign(sum) * (roundtab[mabs(sum) % 16] + (mabs(sum) / 16) * 2);
				if (!put_byte(ctx, (dx >> 7) & 0x3f)) return 0;
				if (!put_byte(ctx, dx * 2)) return 0;
				if (!put_byte(ctx, (dy >> 7) & 0x3f)) return 0;
				if (!put_byte(ctx, dy * 2)) return 0;

				ctx->top_mv[j * 2][0] = ctx->mv[2][0];
				ctx->top_mv[j * 2][1] = ctx->mv[2][1];
				ctx->top_mv[j * 2 + 1][0] = ctx->mv[3][0];
				ctx->top_mv[j * 2 + 1][1] = ctx->mv[3][1];
				ctx->left_mv[0][0] = ctx->mv[1][0];
				ctx->left_mv[0][1] = ctx->mv[1][1];
				ctx->left_mv[1][0] = ctx->mv[3][0];
				ctx->left_mv[1][1] = ctx->mv[3][1];
			}

			for (comp = 0 ; comp < 6 ; comp++) {
				coeff_count = 0;
				last = 0;

				if (Mode == MODE_INTRA || Mode == MODE_INTRA_Q) {
					coeff_count = 1;
					temp = getbits(ctx, 8, &byte_index, &bit_index, inbuf);

					if (temp == 128) {
						return 0;
					}

					if (temp == 255) {
						temp = 128;
					}

					temp *= 8;

					if (!put_byte(ctx, (temp >> 8) & 0xf)) return 0;
					if (!put_byte(ctx, temp)) return 0;
				}

				if ((CBP & (1 << (5 - comp)))) {
					while (last == 0) {
						temp = showbits(ctx, 12, byte_index, bit_index, inbuf);

						if (temp >= 512) {
							tab = &DCT3Dtab0[(temp >> 5) - 16];
						} else if (temp >= 128) {
							tab = &DCT3Dtab1[(temp >> 2) - 32];
						} else if (temp >= 8) {
							tab = &DCT3Dtab2[(temp >> 0) - 8];
						} else {
							return 0;
						}

						flushbits(ctx, tab->len, &byte_index, &bit_index);

						if (tab->val == ESCAPE) {
							if (s263 == 1 && h263_version == 1) {
								format_bit = getbits(ctx, 1, &byte_index, &bit_index, inbuf);
								last = getbits(ctx, 1, &byte_index, &bit_index, inbuf);
								run = getbits(ctx, 6, &byte_index, &bit_index, inbuf);

								if (format_bit) {
									level = getbits(ctx, 11, &byte_index, &bit_index, inbuf);
									sign = (level >= 1024);

									if (sign) {
										val = 2048 - level;
									} else {
										val = level;
									}
								} else {
									level = getbits(ctx, 7, &byte_index, &bit_index, inbuf);
									sign = (level >= 64);

									if (sign) {
										val = 128 - level;
									} else {
										val = level;
									}
								}
							} else {
								last = getbits(ctx, 1, &byte_index, &bit_index, inbuf);
								run = getbits(ctx, 6, &byte_index, &bit_index, inbuf);
								level = getbits(ctx, 8, &byte_index, &bit_index, inbuf);
								sign = (level >= 128);

								if (sign) {
									val = 256 - level;
								} else {
									val = level;
								}
							}
						} else {
							run = (tab->val >> 4) & 255;
							level = tab->val & 15;
							last = (tab->val >> 12) & 1;

							val = level;
							sign = getbits(ctx, 1, &byte_index, &bit_index, inbuf);
						}

						coeff_count += run + 1;
						if (coeff_count > 64) {
							return 0;
						}

						if ((quant % 2) == 1) {
							temp = (sign ? -(quant * (2 * val + 1)) : quant * (2 * val + 1));
						} else {
							temp = (sign ? -(quant * (2 * val + 1) - 1) : quant * (2 * val + 1) - 1);
						}

						if (run < 15) {
							if (!put_byte(ctx, (run << 4) | ((temp >> 8) & 0xf))) return 0;
							if (!put_byte(ctx, temp)) return 0;
						} else {
							if (!put_byte(ctx, 0xf0)) return 0;
							if (!put_byte(ctx, run)) return 0;
							if (!put_byte(ctx, (temp >> 8) & 0xf)) return 0;
							if (!put_byte(ctx, temp)) return 0;
						}

						if (last) {
							if (!put_byte(ctx, 0xf0)) return 0;
							if (!put_byte(ctx, 0)) return 0;
						}
					}
				} else {
					if (!put_byte(ctx, 0xf0)) return 0;
					if (!put_byte(ctx, 0)) return 0;
				}
			}
		}
	}

	return ctx->failed ? 0 : ctx->outputSize;
}


DreamH263 *dream_h263_new(void)
{
	return g_try_new0(DreamH263, 1);
}

void dream_h263_free(DreamH263 *state)
{
	g_free(state);
}

void dream_h263_reset(DreamH263 *state)
{
	memset(state, 0, sizeof(*state));
}

void dream_h263_get_size(const DreamH263 *state, guint *width, guint *height)
{
	*width = state->width;
	*height = state->height;
}

GstBuffer *dream_h263_transform(DreamH263 *state, GstBuffer *input,
	gboolean sorenson, GError **error)
{
	DreamH263 working = *state;
	GstMapInfo map = GST_MAP_INFO_INIT;
	GstBuffer *output = NULL;
	guint8 *data;
	gsize size;

	if (!gst_buffer_map(input, &map, GST_MAP_READ)) {
		g_set_error_literal(error, DREAM_CODEC_ERROR, DREAM_CODEC_ERROR_MEMORY, "Unable to map H.263 frame");
		return NULL;
	}
	if (!map.size || map.size > DREAM_CODEC_MAX_FRAME_SIZE)
		goto invalid;
	working.inputSize = map.size;
	working.output = NULL;
	working.outputSize = 0;
	working.outputCapacity = DREAM_CODEC_MAX_FRAME_SIZE;
	working.failed = FALSE;
	size = h263_convert(&working, map.data, map.size, sorenson);
	if (!size)
		goto invalid;
	data = g_try_malloc(size);
	if (!data) {
		g_set_error_literal(error, DREAM_CODEC_ERROR, DREAM_CODEC_ERROR_MEMORY, "Unable to allocate H.263 frame");
		goto done;
	}
	working = *state;
	working.inputSize = map.size;
	working.output = data;
	working.outputSize = 0;
	working.outputCapacity = size;
	working.failed = FALSE;
	if (h263_convert(&working, map.data, map.size, sorenson) != size) {
		g_free(data);
		goto invalid;
	}
	output = gst_buffer_new_wrapped_full(0, data, size, 0, size, data, g_free);
	if (!output) {
		g_free(data);
		g_set_error_literal(error, DREAM_CODEC_ERROR, DREAM_CODEC_ERROR_MEMORY, "Unable to wrap H.263 frame");
		goto done;
	}
	if (!gst_buffer_copy_into(output, input, GST_BUFFER_COPY_METADATA, 0, -1)) {
		gst_buffer_unref(output);
		output = NULL;
		g_set_error_literal(error, DREAM_CODEC_ERROR, DREAM_CODEC_ERROR_MEMORY, "Unable to preserve H.263 metadata");
		goto done;
	}
	working.output = NULL;
	working.inputSize = working.outputSize = 0;
	*state = working;
	goto done;
invalid:
	g_set_error_literal(error, DREAM_CODEC_ERROR, DREAM_CODEC_ERROR_DATA,
		"Truncated, unsupported or oversized H.263/Sorenson frame");
done:
	gst_buffer_unmap(input, &map);
	return output;
}

