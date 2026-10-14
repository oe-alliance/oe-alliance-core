/*
 * drivers/amlogic/media/stream_input/parser/streambuf.h
 *
 * Copyright (C) 2016 Amlogic, Inc. All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 */

#ifndef STREAMBUF_H
#define STREAMBUF_H
#include <linux/amlogic/media/utils/amports_config.h>

#define BUF_FLAG_ALLOC          0x01
#define BUF_FLAG_IN_USE         0x02
#define BUF_FLAG_PARSER         0x04
#define BUF_FLAG_FIRST_TSTAMP   0x08
#define BUF_FLAG_IOMEM          0x10

#define BUF_TYPE_VIDEO      0
#define BUF_TYPE_AUDIO      1
#define BUF_TYPE_SUBTITLE   2
#define BUF_TYPE_USERDATA   3
#define BUF_TYPE_HEVC       4
#define BUF_MAX_NUM         5

#define INVALID_PTS 0xffffffff

#define FETCHBUF_SIZE   (64*1024)
#define USER_DATA_SIZE  (8*1024)

struct vdec_s;

struct stream_buf_s {
	s32 flag;
	u32 type;
	unsigned long buf_start;
	struct page *buf_pages;
	int buf_page_num;
	u32 buf_size;
	u32 default_buf_size;
	u32 canusebuf_size;
	u32 first_tstamp;
	const ulong reg_base;
	wait_queue_head_t wq;
	struct timer_list timer;
	u32 wcnt;
	u32 buf_wp;
	u32 buf_rp;
	u32 max_buffer_delay_ms;
	u64 last_write_jiffies64;
	void *write_thread;
	int for_4k;
	bool is_secure;
} /*stream_buf_t */;

struct stream_port_s {
	/* driver info */
	const char *name;
	struct device *class_dev;
	const struct file_operations *fops;

	/* ports control */
	s32 type;
	s32 flag;
	s32 pcr_inited;

	/* decoder info */
	s32 vformat;
	s32 aformat;
	s32 achanl;
	s32 asamprate;
	s32 adatawidth;

	/* parser info */
	u32 vid;
	u32 aid;
	u32 sid;
	u32 pcrid;
} /*stream_port_t */;
enum drm_level_e {
	DRM_LEVEL1 = 1,
	DRM_LEVEL2 = 2,
	DRM_LEVEL3 = 3,
	DRM_NONE = 4,
};

struct drm_info {
	enum drm_level_e drm_level;
	u32 drm_flag;
	u32 drm_hasesdata;
	u32 drm_priv;
	u32 drm_pktsize;
	u32 drm_pktpts;
	u32 drm_phy;
	u32 drm_vir;
	u32 drm_remap;
	u32 data_offset;
	u32 handle;
	u32 extpad[7];
} /*drminfo_t */;

#define TYPE_DRMINFO_V2  0x100
#define TYPE_DRMINFO   0x80
#define TYPE_PATTERN   0x40

struct vdec_s;

extern void *fetchbuf;

extern u32 stbuf_level(struct stream_buf_s *buf);
extern u32 stbuf_rp(struct stream_buf_s *buf);
extern u32 stbuf_space(struct stream_buf_s *buf);
extern u32 stbuf_size(struct stream_buf_s *buf);
extern u32 stbuf_canusesize(struct stream_buf_s *buf);
extern s32 stbuf_init(struct stream_buf_s *buf, struct vdec_s *vdec,
		bool is_multi);
extern s32 stbuf_wait_space(struct stream_buf_s *stream_buf, size_t count);
extern void stbuf_release(struct stream_buf_s *buf, bool is_multi);
extern int stbuf_change_size(struct stream_buf_s *buf, int size,
				bool is_secure);
extern int stbuf_fetch_init(void);
extern void stbuf_fetch_release(void);
extern u32 stbuf_sub_rp_get(void);
extern void stbuf_sub_rp_set(unsigned int sub_rp);
extern u32 stbuf_sub_wp_get(void);
extern u32 stbuf_sub_start_get(void);
extern u32 stbuf_userdata_start_get(void);
extern struct stream_buf_s *get_stream_buffer(int id);

extern void stbuf_vdec2_init(struct stream_buf_s *buf);

#endif /* STREAMBUF_H */
