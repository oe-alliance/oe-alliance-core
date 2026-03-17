/*
 * AV201x Airoha Technology silicon tuner driver
 *
 * Copyright (C) 2014 Luis Alves <ljalvs@gmail.com>
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License along
 *    with this program; if not, write to the Free Software Foundation, Inc.,
 *    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

#ifndef AV201X_PRIV_H
#define AV201X_PRIV_H

struct av201x_priv {
	struct av201x_config *cfg;
	struct i2c_adapter *i2c;
};

enum av201x_regs_addr {
	REG_FN		= 0x00,
	REG_BWFILTER	= 0x05,
	REG_TUNER_STAT	= 0x0b,
	REG_TUNER_CTRL	= 0x0c,
	REG_FT_CTRL	= 0x25,
};

/* REG_TUNER_STAT */
#define AV201X_PLLLOCK		(1<<0)

/* REG_TUNER_CTRL */
#define AV201X_SLEEP		(1<<5)
#define AV201X_RFLP		(1<<6)

/* REG_FT_CTRL */
#define AV201X_FT_EN		(1<<1)
#define AV201X_FT_BLK		(1<<2)

struct av201x_regtable {
	u8 addr;
	u8 setmask;
	u8 clrmask;
	int sleep;
};

static struct av201x_regtable av201x_inittuner0[] = {
	{0x00, 0x38, 0xff, 0},
	{0x01, 0x00, 0xff, 0},
	{0x02, 0x00, 0xff, 0},
	{0x03, 0x50, 0xff, 0},
	{0x04, 0x1f, 0xff, 0},
	{0x05, 0xa3, 0xff, 0},
	{0x06, 0xfd, 0xff, 0},
	{0x07, 0x58, 0xff, 0},
	{0x08, 0x36, 0xff, 0},
	{0x09, 0xc2, 0xff, 0},
	{0x0a, 0x88, 0xff, 0},
	{0x0b, 0xb4, 0xff, 20},
	{0x0d, 0x40, 0xff, 0},
};

static struct av201x_regtable av201x_inittuner1a[] = {
	{0x0e, 0x94, 0xff, 0},
	{0x0f, 0x9a, 0xff, 0},
};

static struct av201x_regtable av201x_inittuner1b[] = {
	{0x0e, 0x5b, 0xff, 0},
	{0x0f, 0x6a, 0xff, 0},
};

static struct av201x_regtable av201x_inittuner2[] = {
	{0x10, 0x66, 0xff, 0},
	{0x11, 0x40, 0xff, 0},
	{0x12, 0x80, 0xff, 0},
	{0x13, 0x2b, 0xff, 0},
	{0x14, 0x6a, 0xff, 0},
	{0x15, 0x50, 0xff, 0},
	{0x16, 0x91, 0xff, 0},
	{0x17, 0x27, 0xff, 0},
	{0x18, 0x8f, 0xff, 0},
	{0x19, 0xcc, 0xff, 0},
	{0x1a, 0x21, 0xff, 0},
	{0x1b, 0x10, 0xff, 0},
	{0x1c, 0x80, 0xff, 0},
	{0x1d, 0x02, 0xff, 0},
	{0x1e, 0xf5, 0xff, 0},
	{0x1f, 0x7f, 0xff, 0},
	{0x20, 0x4a, 0xff, 0},
	{0x21, 0x9b, 0xff, 0},
	{0x22, 0xe0, 0xff, 0},
	{0x23, 0xe0, 0xff, 0},
	{0x24, 0x36, 0xff, 0},
	{0x25, 0x00, 0xff, 0},
	{0x26, 0xab, 0xff, 0},
	{0x27, 0x97, 0xff, 0},
	{0x28, 0xc5, 0xff, 0},
	{0x29, 0xa8, 0xff, 20},
};

#endif /* AV201X_PRIV_H */
