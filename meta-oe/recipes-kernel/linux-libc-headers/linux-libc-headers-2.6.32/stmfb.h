/***********************************************************************
 *
 * File: linux/kernel/drivers/video/stmfb.h
 * Copyright (c) 2000, 2004, 2005 STMicroelectronics Limited.
 *
 * This file is subject to the terms and conditions of the GNU General Public
 * License.  See the file COPYING in the main directory of this archive for
 * more details.
 *
\***********************************************************************/

#ifndef _STMFB_H
#define _STMFB_H

/*
 * Surface definitions for usermode, in the kernel driver they are
 * already defined internally as part of the generic framework.
 */
#if !defined(__KERNEL__)
#include <sys/time.h>
typedef enum
{
    SURF_NULL_PAD,
    SURF_RGB565 ,
    SURF_RGB888 ,
    SURF_ARGB8565,
    SURF_ARGB8888,
    SURF_ARGB1555,
    SURF_ARGB4444,
    SURF_CRYCB888,   /* Note the order of the components */
    SURF_YCBCR422R,
    SURF_YCBCR422MB,
    SURF_YCBCR420MB,
    SURF_ACRYCB8888, /* Note the order, not compatible with DirectFB's AYUV */
    SURF_CLUT1,
    SURF_CLUT2,
    SURF_CLUT4,
    SURF_CLUT8,
    SURF_ACLUT44,
    SURF_ACLUT88,
    SURF_A1,
    SURF_A8,
    SURF_BGRA8888, /* Bigendian ARGB */
//#ifdef __TDT__ // Not defined in enigma2... define?
    SURF_BGR888, 
//#endif
    SURF_YUYV,     /* 422R with luma and chroma byteswapped              */
    SURF_YUV420,   /* Planar YUV with 1/2 horizontal and vertical chroma */
                   /* in three separate buffers Y,Cb then Cr             */
    SURF_YVU420,   /* Planar YUV with 1/2 horizontal and vertical chroma */
                   /* in three separate buffers Y,Cr then Cb             */
    SURF_YUV422P,  /* Planar YUV with 1/2 horizontal chroma              */
                   /* in three separate buffers Y,Cb then Cr             */

    SURF_RLD_BD,   /* RLE Decoding controlled by setting source format   */
    SURF_RLD_H2,
    SURF_RLD_H8,
    SURF_CLUT8_ARGB4444_ENTRIES, /* For cursor plane support             */

    SURF_END
}SURF_FMT;
#endif /* !__KERNEL__ */

#define BLT_OP_FLAGS_NULL                    0x00000000
#define BLT_OP_FLAGS_SRC_COLOR_KEY           0x00000001
#define BLT_OP_FLAGS_DST_COLOR_KEY           0x00000002
#define BLT_OP_FLAGS_GLOBAL_ALPHA            0x00000004
#define BLT_OP_FLAGS_BLEND_SRC_ALPHA         0x00000008
#define BLT_OP_FLAGS_BLEND_SRC_ALPHA_PREMULT 0x00000010
#define BLT_OP_FLAGS_PLANE_MASK              0x00000020
#define BLT_OP_FLAGS_FLICKERFILTER           0x00000800
#define BLT_OP_FLAGS_CLUT_ENABLE             0x00001000
#define BLT_OP_FLAGS_BLEND_DST_COLOR         0x00002000
#define BLT_OP_FLAGS_BLEND_DST_MEMORY        0x00004000
#define BLT_OP_FLAGS_PREMULT_COLOUR_ALPHA    0x00008000
#define BLT_OP_FLAGS_COLORIZE                0x00010000
#define BLT_OP_FLAGS_XOR                     0x00020000
#define BLT_OP_FLAGS_BLEND_DST_ZERO          0x00040000
#define BLT_OP_FLAGS_RLE_DECODE              0x00080000

typedef enum
{
  BLT_OP_NULL,
  BLT_OP_FILL,
  BLT_OP_COPY,
  BLT_OP_DRAW_RECTANGLE,
} STMFBIO_BLT_OP;

typedef enum
{
    STMFBGP_FRAMEBUFFER,

    STMFBGP_GFX_FIRST,
    STMFBGP_GFX0 = STMFBGP_GFX_FIRST,
    STMFBGP_GFX1,
    STMFBGP_GFX2,
    STMFBGP_GFX3,
    STMFBGP_GFX4,
    STMFBGP_GFX_LAST = STMFBGP_GFX4

} STMFB_GFXMEMORY_PARTITION;



typedef struct
{
    unsigned long numEntries;
    unsigned long entries[256];
} STMFBIO_PALETTE;


typedef struct
{
  STMFBIO_BLT_OP  operation;
  unsigned long   ulFlags;
  unsigned long   colour;
  unsigned long   globalAlpha;
  unsigned long   colourKey;
  unsigned long   planeMask;
  STMFB_GFXMEMORY_PARTITION srcMemBase;
  unsigned long             srcOffset;
  unsigned long             srcPitch;
  STMFB_GFXMEMORY_PARTITION dstMemBase;
  unsigned long             dstOffset;
  unsigned long             dstPitch;
  SURF_FMT        srcFormat;
  SURF_FMT        dstFormat;

  unsigned short  src_top;
  unsigned short  src_bottom;
  unsigned short  src_left;
  unsigned short  src_right;
  unsigned short  dst_top;
  unsigned short  dst_bottom;
  unsigned short  dst_left;
  unsigned short  dst_right;
} STMFBIO_BLT_DATA;

//#ifdef __TDT__ 
typedef struct
{
  STMFBIO_BLT_OP  operation;
  unsigned long   ulFlags;
  unsigned long   colour;
  unsigned long   globalAlpha;
  unsigned long   colourKey;
  unsigned long   planeMask;
  char		 *srcMemBase;
  unsigned long   srcMemSize;
  unsigned long   srcOffset;
  unsigned long   srcPitch;
  char 		 *dstMemBase;
  unsigned long   dstMemSize;
  unsigned long   dstOffset;
  unsigned long   dstPitch;
  SURF_FMT        srcFormat;
  SURF_FMT        dstFormat;

  unsigned short  src_top;
  unsigned short  src_bottom;
  unsigned short  src_left;
  unsigned short  src_right;
  unsigned short  dst_top;
  unsigned short  dst_bottom;
  unsigned short  dst_left;
  unsigned short  dst_right;
} STMFBIO_BLT_EXTERN_DATA;
//#endif


/*
 * Defined for STMFBIO_CGMS_ANALOG (use as bitfield)
 */
#define STMFBIO_CGMS_ANALOG_COPYRIGHT_ASSERTED 1 /* copyright bit */
#define STMFBIO_CGMS_ANALOG_COPYING_RESTRICTED 2 /* generation bit */


#define STMFBIO_COLOURKEY_FLAGS_ENABLE	0x00000001
#define STMFBIO_COLOURKEY_FLAGS_INVERT	0x00000002

typedef enum {
  STMFBIO_FF_OFF,
  STMFBIO_FF_SIMPLE,
  STMFBIO_FF_ADAPTIVE
} stmfbio_ff_state;


#define STMFBIO_VAR_CAPS_NONE           (0)
#define STMFBIO_VAR_CAPS_COLOURKEY      (1L<<1)
#define STMFBIO_VAR_CAPS_FLICKER_FILTER (1L<<2)
#define STMFBIO_VAR_CAPS_PREMULTIPLIED  (1L<<3)
#define STMFBIO_VAR_CAPS_OPACITY        (1L<<4)
#define STMFBIO_VAR_CAPS_GAIN           (1L<<5)
#define STMFBIO_VAR_CAPS_BRIGHTNESS     (1L<<6)
#define STMFBIO_VAR_CAPS_SATURATION     (1L<<7)
#define STMFBIO_VAR_CAPS_CONTRAST       (1L<<8)
#define STMFBIO_VAR_CAPS_TINT           (1L<<9)
#define STMFBIO_VAR_CAPS_ALPHA_RAMP     (1L<<10)
#define STMFBIO_VAR_CAPS_ZPOSITION      (1L<<11)
#define STBFBIO_VAR_CAPS_RESCALE_COLOUR_TO_VIDEO_RANGE (1L<<12)

enum stmfbio_output_id {
	STMFBIO_OUTPUTID_INVALID,

	STMFBIO_OUTPUTID_MAIN,
};


typedef enum _stmfbio_activate {
  STMFBIO_ACTIVATE_IMMEDIATE      = 0x00000000,
  STMFBIO_ACTIVATE_ON_NEXT_CHANGE = 0x00000001,
  STMFBIO_ACTIVATE_TEST           = 0x00000002,
  STMFBIO_ACTIVATE_MASK           = 0x0000000f,

  STMFBIO_ACTIVATE_VBL            = 0x00000010,
} stmfbio_activate;


enum stmfbio_output_standard {
	STMFBIO_STD_UNKNOWN,
	/* */
	STMFBIO_STD_PAL_BDGHI    = 0x4000000000000000LLU,
	STMFBIO_STD_SECAM        = 0x2000000000000000LLU,

	/* analogue standards are a mess - the following values
           coincidentially coincide with the v4l2 defines... */
	STMFBIO_STD_PAL_M        = 0x0000000000000100LLU,
	STMFBIO_STD_PAL_N        = 0x0000000000000200LLU,
	STMFBIO_STD_PAL_Nc       = 0x0000000000000400LLU,
	STMFBIO_STD_PAL_60       = 0x0000000000000800LLU,

	STMFBIO_STD_NTSC_M       = 0x0000000000001000LLU,
	STMFBIO_STD_NTSC_M_JP    = 0x0000000000002000LLU,
	STMFBIO_STD_NTSC_443     = 0x0000000000004000LLU,

	/* */
	STMFBIO_STD_VGA_60       = 0x0000000100000000LLU,
	STMFBIO_STD_VGA_59_94    = 0x0000000200000000LLU,
	STMFBIO_STD_480P_60      = 0x0000000400000000LLU,
	STMFBIO_STD_480P_59_94   = 0x0000000800000000LLU,
	STMFBIO_STD_576P_50      = 0x0000001000000000LLU,
	STMFBIO_STD_1080P_60     = 0x0000002000000000LLU,
	STMFBIO_STD_1080P_59_94  = 0x0000004000000000LLU,
	STMFBIO_STD_1080P_50     = 0x0000008000000000LLU,
	STMFBIO_STD_1080P_23_976 = 0x0000010000000000LLU,
	STMFBIO_STD_1080P_24     = 0x0000020000000000LLU,
	STMFBIO_STD_1080P_25     = 0x0000040000000000LLU,
	STMFBIO_STD_1080P_29_97  = 0x0000080000000000LLU,
	STMFBIO_STD_1080P_30     = 0x0000100000000000LLU,
	STMFBIO_STD_1080I_60     = 0x0000200000000000LLU,
	STMFBIO_STD_1080I_59_94  = 0x0000400000000000LLU,
	STMFBIO_STD_1080I_50     = 0x0000800000000000LLU,
	STMFBIO_STD_720P_60      = 0x0001000000000000LLU,
	STMFBIO_STD_720P_59_94   = 0x0002000000000000LLU,
	STMFBIO_STD_720P_50      = 0x0004000000000000LLU,

	STMFBIO_STD_QFHD3660     = 0x0010000000000000LLU,
	STMFBIO_STD_QFHD3650     = 0x0020000000000000LLU,
	STMFBIO_STD_WQFHD5660    = 0x0040000000000000LLU,
	STMFBIO_STD_WQFHD5650    = 0x0080000000000000LLU,
	STMFBIO_STD_QFHD5660     = 0x0100000000000000LLU,
	STMFBIO_STD_QFHD5650     = 0x0200000000000000LLU,
	STMFBIO_STD_QFHD1830     = 0x0400000000000000LLU,
	STMFBIO_STD_QFHD1825     = 0x0800000000000000LLU,
};


#define STMFBIO_STD_NTSC      (STMFBIO_STD_NTSC_M \
                               | STMFBIO_STD_NTSC_M_JP)

#define STMFBIO_STD_PAL       (STMFBIO_STD_PAL_BDGHI)

#define STMFBIO_STD_525_60    (STMFBIO_STD_PAL_M    \
                               | STMFBIO_STD_PAL_60 \
                               | STMFBIO_STD_NTSC   \
                               | STMFBIO_STD_NTSC_443)
#define STMFBIO_STD_625_50    (STMFBIO_STD_PAL      \
                               | STMFBIO_STD_PAL_N  \
                               | STMFBIO_STD_PAL_Nc \
                               | STMFBIO_STD_SECAM)

#define STMFBIO_STD_SMPTE293M (STMFBIO_STD_480P_60      \
                               | STMFBIO_STD_480P_59_94 \
                               | STMFBIO_STD_576P_50)

#define STMFBIO_STD_SMPTE274M (STMFBIO_STD_1080P_60       \
                               | STMFBIO_STD_1080P_59_94  \
                               | STMFBIO_STD_1080P_50     \
                               | STMFBIO_STD_1080P_23_976 \
                               | STMFBIO_STD_1080P_24     \
                               | STMFBIO_STD_1080P_25     \
                               | STMFBIO_STD_1080P_29_97  \
                               | STMFBIO_STD_1080P_30     \
                               | STMFBIO_STD_1080I_60     \
                               | STMFBIO_STD_1080I_59_94  \
                               | STMFBIO_STD_1080I_50)

#define STMFBIO_STD_SMPTE296M (STMFBIO_STD_720P_60      \
                               | STMFBIO_STD_720P_59_94 \
                               | STMFBIO_STD_720P_50)

#define STMFBIO_STD_SD        (STMFBIO_STD_525_60 \
                               | STMFBIO_STD_625_50)

#define STMFBIO_STD_ED        (STMFBIO_STD_SMPTE293M)

#define STMFBIO_STD_HD        (STMFBIO_STD_SMPTE274M \
                               | STMFBIO_STD_SMPTE296M)

#define STMFBIO_STD_VESA      (STMFBIO_STD_VGA_60 \
                               | STMFBIO_STD_VGA_59_94)

#define STMFBIO_STD_CEA861    (STMFBIO_STD_525_60      \
                               | STMFBIO_STD_625_50    \
                               | STMFBIO_STD_VGA_60    \
                               | STMFBIO_STD_VGA_59_94 \
                               | STMFBIO_STD_SMPTE293M \
                               | STMFBIO_STD_SMPTE274M \
                               | STMFBIO_STD_SMPTE296M)

#define STMFBIO_STD_NTG5      (STMFBIO_STD_QFHD3660    \
                               | STMFBIO_STD_QFHD3650  \
                               | STMFBIO_STD_WQFHD5660 \
                               | STMFBIO_STD_WQFHD5650 \
                               | STMFBIO_STD_QFHD5660  \
                               | STMFBIO_STD_QFHD5650  \
                               | STMFBIO_STD_QFHD1830  \
                               | STMFBIO_STD_QFHD1825)

#define STMFBIO_STD_INTERLACED (STMFBIO_STD_525_60        \
                                | STMFBIO_STD_625_50      \
                                | STMFBIO_STD_1080I_60    \
                                | STMFBIO_STD_1080I_59_94 \
                                | STMFBIO_STD_1080I_50)

#define STMFBIO_STD_PROGRESSIVE (STMFBIO_STD_VGA_60         \
                                 | STMFBIO_STD_VGA_59_94    \
                                 | STMFBIO_STD_SMPTE293M    \
                                 | STMFBIO_STD_1080P_60     \
                                 | STMFBIO_STD_1080P_59_94  \
                                 | STMFBIO_STD_1080P_50     \
                                 | STMFBIO_STD_1080P_23_976 \
                                 | STMFBIO_STD_1080P_24     \
                                 | STMFBIO_STD_1080P_25     \
                                 | STMFBIO_STD_1080P_29_97  \
                                 | STMFBIO_STD_1080P_30     \
                                 | STMFBIO_STD_SMPTE296M    \
                                 | STMFBIO_STD_NTG5)

struct stmfbio_outputinfo
{
	enum stmfbio_output_id outputid;
	enum _stmfbio_activate activate;
	enum stmfbio_output_standard standard;
};

struct stmfbio_outputstandards
{
	enum stmfbio_output_id outputid;
	enum stmfbio_output_standard all_standards;
};

struct stmfbio_plane_dimension {
	__u32 w;
	__u32 h;
};

struct stmfbio_plane_rect {
	__u32 x;
	__u32 y;
	struct stmfbio_plane_dimension dim;
};

struct stmfbio_plane_config {
	unsigned long                  baseaddr;
	/* FIXME: the source API is incomplete! Should be updated to support a
           real source 'viewport' inside a source surface. */
	struct stmfbio_plane_dimension source;  /* resolution */
	struct stmfbio_plane_rect      dest;
	SURF_FMT                       format;
	__u32                          pitch; /* desired pitch */

	/* private */
	__u32 bitdepth; /* will be updated on successful return */
};

struct stmfbio_planeinfo
{
	__u32 layerid; /* must be 0 (for now) */
	enum _stmfbio_activate activate;
	struct stmfbio_plane_config config;
};

struct stmfbio_plane_pan
{
	__u32 layerid; /* must be 0 (for now) */
	enum _stmfbio_activate activate;
	unsigned long baseaddr;
};

struct stmfbio_var_screeninfo_ex
{
  /*
   * Display layer to operate on, 0 is always the layer showing the framebuffer.
   * No other layers have to be defined and the IOCTL will return ENODEV
   * if given an invalid layerid.
   */
  __u32 layerid;
  __u32 caps;                    /* Valid entries in structure               */
  __u32 failed;                  /* Indicates entries that failed during set */
  stmfbio_activate activate;

  /*
   * STMFBIO_VAR_CAPS_COLOURKEY
   */
  __u32 min_colour_key;
  __u32 max_colour_key;
  __u32 colourKeyFlags;

  stmfbio_ff_state ff_state;          /* STMFBIO_VAR_CAPS_FLICKER_FILTER      */

  __u8 premultiplied_alpha;           /* STMFBIO_VAR_CAPS_PREMULTIPLIED       */
  __u8 rescale_colour_to_video_range; /* STBFBIO_VAR_CAPS_RESCALE_COLOUR_...  */

  __u8 opacity;                       /* STMFBIO_VAR_CAPS_OPACITY             */
  __u8 gain;                          /* STMFBIO_VAR_CAPS_GAIN                */
  __u8 brightness;                    /* STMFBIO_VAR_CAPS_BRIGHTNESS          */
  __u8 saturation;                    /* STMFBIO_VAR_CAPS_SATURATION          */
  __u8 contrast;                      /* STMFBIO_VAR_CAPS_CONTRAST            */
  __u8 tint;                          /* STMFBIO_VAR_CAPS_HUE                 */
  __u8 alpha_ramp[2];                 /* STMFBIO_CAR_CAPS_ALPHA_RAMP          */

  __u32 z_position;                   /* STMFBIO_VAR_CAPS_ZPOSITION           */
};


/*
 * Note: The standards are a bitfield in order to match the internal driver
 *       representation. Do not modify!
 */
#define STMFBIO_OUTPUT_STD_PAL_BDGHI       (1L<<0)
#define STMFBIO_OUTPUT_STD_PAL_M           (1L<<1)
#define STMFBIO_OUTPUT_STD_PAL_N           (1L<<2)
#define STMFBIO_OUTPUT_STD_PAL_Nc          (1L<<3)
#define STMFBIO_OUTPUT_STD_NTSC_M          (1L<<4)
#define STMFBIO_OUTPUT_STD_NTSC_J          (1L<<5)
#define STMFBIO_OUTPUT_STD_NTSC_443        (1L<<6)
#define STMFBIO_OUTPUT_STD_SECAM           (1L<<7)
#define STMFBIO_OUTPUT_STD_PAL_60          (1L<<8)

#define STMFBIO_OUTPUT_ANALOGUE_RGB        (1L<<0)
#define STMFBIO_OUTPUT_ANALOGUE_YPrPb      (1L<<1)
#define STMFBIO_OUTPUT_ANALOGUE_YC         (1L<<2)
#define STMFBIO_OUTPUT_ANALOGUE_CVBS       (1L<<3)
#define STMFBIO_OUTPUT_ANALOGUE_MASK       (0xf)

#define STMFBIO_OUTPUT_ANALOGUE_CLIP_VIDEORANGE (0)
#define STMFBIO_OUTPUT_ANALOGUE_CLIP_FULLRANGE  (1L<<8)

#define STMFBIO_OUTPUT_ANALOGUE_COLORSPACE_AUTO (0)
#define STMFBIO_OUTPUT_ANALOGUE_COLORSPACE_601  (1L<<9)
#define STMFBIO_OUTPUT_ANALOGUE_COLORSPACE_709  (2L<<9)
#define STMFBIO_OUTPUT_ANALOGUE_COLORSPACE_MASK (3L<<9)


#define STMFBIO_OUTPUT_DVO_ENABLED            (0)
#define STMFBIO_OUTPUT_DVO_DISABLED           (1L<<0)
#define STMFBIO_OUTPUT_DVO_YUV_444_16BIT      (0)
#define STMFBIO_OUTPUT_DVO_YUV_444_24BIT      (1L<<1)
#define STMFBIO_OUTPUT_DVO_YUV_422_16BIT      (2L<<1)
#define STMFBIO_OUTPUT_DVO_ITUR656            (3L<<1)
#define STMFBIO_OUTPUT_DVO_RGB_24BIT          (4L<<1)
#define STMFBIO_OUTPUT_DVO_MODE_MASK          (7L<<1)

#define STMFBIO_OUTPUT_DVO_CLIP_VIDEORANGE        (0)
#define STMFBIO_OUTPUT_DVO_CLIP_FULLRANGE         (1L<<8)
#define STMFBIO_OUTPUT_DVO_CHROMA_FILTER_DISABLED (0)
#define STMFBIO_OUTPUT_DVO_CHROMA_FILTER_ENABLED  (1L<<9)

#define STMFBIO_OUTPUT_HDMI_ENABLED           (0)
#define STMFBIO_OUTPUT_HDMI_DISABLED          (1L<<0)
#define STMFBIO_OUTPUT_HDMI_RGB               (0)
#define STMFBIO_OUTPUT_HDMI_YUV               (1L<<1)
#define STMFBIO_OUTPUT_HDMI_444               (0)
#define STMFBIO_OUTPUT_HDMI_422               (1L<<2)
#define STMFBIO_OUTPUT_HDMI_COLOURDEPTH_SHIFT (3)
#define STMFBIO_OUTPUT_HDMI_COLOURDEPTH_24BIT (0)
#define STMFBIO_OUTPUT_HDMI_COLOURDEPTH_30BIT (1L<<STMFBIO_OUTPUT_HDMI_COLOURDEPTH_SHIFT)
#define STMFBIO_OUTPUT_HDMI_COLOURDEPTH_36BIT (2L<<STMFBIO_OUTPUT_HDMI_COLOURDEPTH_SHIFT)
#define STMFBIO_OUTPUT_HDMI_COLOURDEPTH_48BIT (3L<<STMFBIO_OUTPUT_HDMI_COLOURDEPTH_SHIFT)
#define STMFBIO_OUTPUT_HDMI_COLOURDEPTH_MASK  (3L<<STMFBIO_OUTPUT_HDMI_COLOURDEPTH_SHIFT)

#define STMFBIO_OUTPUT_HDMI_CLIP_VIDEORANGE   (0)
#define STMFBIO_OUTPUT_HDMI_CLIP_FULLRANGE    (1L<<8)

#define STMFBIO_OUTPUT_CAPS_NONE             (0)
#define STMFBIO_OUTPUT_CAPS_SDTV_ENCODING    (1L<<0)
#define STMFBIO_OUTPUT_CAPS_ANALOGUE_CONFIG  (1L<<1)
#define STMFBIO_OUTPUT_CAPS_DVO_CONFIG       (1L<<2)
#define STMFBIO_OUTPUT_CAPS_HDMI_CONFIG      (1L<<3)
#define STMFBIO_OUTPUT_CAPS_MIXER_BACKGROUND (1L<<4)
#define STMFBIO_OUTPUT_CAPS_BRIGHTNESS       (1L<<5)
#define STMFBIO_OUTPUT_CAPS_SATURATION       (1L<<6)
#define STMFBIO_OUTPUT_CAPS_CONTRAST         (1L<<7)
#define STMFBIO_OUTPUT_CAPS_HUE              (1L<<8)
#define STMFBIO_OUTPUT_CAPS_PSI_MASK         (STMFBIO_OUTPUT_CAPS_BRIGHTNESS | \
                                              STMFBIO_OUTPUT_CAPS_SATURATION | \
                                              STMFBIO_OUTPUT_CAPS_CONTRAST   | \
                                              STMFBIO_OUTPUT_CAPS_HUE)

struct stmfbio_output_configuration
{
  enum stmfbio_output_id outputid;
  __u32 caps;
  __u32 failed;
  stmfbio_activate activate;

  __u32 sdtv_encoding;
  __u32 analogue_config;
  __u32 dvo_config;
  __u32 hdmi_config;
  __u32 mixer_background;
  __u8  brightness;
  __u8  saturation;
  __u8  contrast;
  __u8  hue;

};


/*
 * Picture configuration allows the application to set information used to
 * construct Line21/23 WSS information in NTSC/PAL analogue output and to
 * construct the AVI InfoFrame in HDMI output where available. For a particular
 * display pipeline, accessed by its associated framebuffer IOCTLs, changing
 * the picture information will effect both analogue and HDMI outputs if they
 * are enabled. Note that VBI information is not supported on YPrPb component
 * analogue output in ED or HD display modes, only in SD (PAL/NTSC).
 *
 * The definitions below match values defined by internal types in
 * <include/stmmetadata.h> and should not be changed.
 */

/*
 * The picture aspect ratio indicates the intended aspect ratio
 * of the full content of a 720x480 or 720x576 picture being
 * displayed on an SD/ED display, i.e. it is indicating the pixel aspect ratio
 * of the image. It does not necessarily indicate (but in all probability is the
 * same as) the aspect ratio of the display.
 *
 * HD pictures always have a pixel aspect ratio of 1:1, hence the picture
 * aspect ratio should always match the display size ratio i.e. 16:9.
 */
#define STMFBIO_PIC_PICTURE_ASPECT_UNKNOWN    (0)
#define STMFBIO_PIC_PICTURE_ASPECT_4_3        (1)
#define STMFBIO_PIC_PICTURE_ASPECT_16_9       (2)

/*
 * The video aspect indicates the actual aspect ratio of video contained in
 * the picture on the display.
 */
#define STMFBIO_PIC_VIDEO_ASPECT_UNKNOWN      (0)
#define STMFBIO_PIC_VIDEO_ASPECT_4_3          (1)
#define STMFBIO_PIC_VIDEO_ASPECT_16_9         (2)
#define STMFBIO_PIC_VIDEO_ASPECT_14_9         (3)
#define STMFBIO_PIC_VIDEO_ASPECT_GT_16_9      (4)

/*
 * When the picture aspect ratio and video aspect ratio do not match then
 * the letterbox style indicates how the video is positioned inside the picture.
 * See CEA-861 Annex H, or standards for analogue video VBI signals
 * for details.
 */
#define STMFBIO_PIC_LETTERBOX_NONE            (0)
#define STMFBIO_PIC_LETTERBOX_CENTER          (1)
#define STMFBIO_PIC_LETTERBOX_TOP             (2)
#define STMFBIO_PIC_LETTERBOX_SAP_14_9        (3)
#define STMFBIO_PIC_LETTERBOX_SAP_4_3         (4)

#define STMFBIO_PIC_RESCALE_NONE              (0)
#define STMFBIO_PIC_RESCALE_HORIZONTAL        (1)
#define STMFBIO_PIC_RESCALE_VERTICAL          (2)
#define STMFBIO_PIC_RESCALE_BOTH              (3)

#define STMFBIO_PIC_BAR_DISABLE               (0)
#define STMFBIO_PIC_BAR_ENABLE                (1)

#define STMFBIO_PICTURE_FLAGS_PICUTRE_ASPECT   (1L<<0)
#define STMFBIO_PICTURE_FLAGS_VIDEO_ASPECT     (1L<<1)
#define STMFBIO_PICTURE_FLAGS_LETTERBOX        (1L<<2)
#define STMFBIO_PICTURE_FLAGS_RESCALE_INFO     (1L<<3)
#define STMFBIO_PICTURE_FLAGS_BAR_INFO         (1L<<4)
#define STMFBIO_PICTURE_FLAGS_BAR_INFO_ENABLE  (1L<<5)

struct stmfbio_picture_configuration
{
  /* Which fields in the structure should be changed */
  __u32 flags;

  /*
   *  {0,0} = immediate, otherwise this should be a time in the
   *  future based on clock_gettime(CLOCK_MONOTONIC,...), not gettimeofday()
   */
  struct timeval timestamp;

  __u16  picture_aspect;
  __u16  video_aspect;
  __u16  letterbox_style;

  /* Indicate if the displayed image (usually video) has been rescaled */
  __u16  picture_rescale;

  /*
   * Black bar information, in pixels/lines from the start of the active video
   * area. The enable allows bar information to be switched on/off without
   * changing the actual bar data.
   */
  __u16 bar_enable;
  __u16 top_bar_end;
  __u16 bottom_bar_start;
  __u16 left_bar_end;
  __u16 right_bar_start;
};


struct stmfbio_auxmem2
{
  __u32 index;
  __u32 physical;
  __u32 size;
};

/* returns the address of a 'struct _STMFBBDispSharedArea', which shall be
   mmap()ed for communication w/ the BDisp IRQ handler */
struct stmfbio_shared
{
  __u32 physical;
  __u32 size;
};

/* Note that this must always match the public declaration for enum
   stm_blitter_device_priv in include/stmdisplayblitter.h
   FIXME: Having this twice just sucks. */
enum stm_blitter_device {
  STM_BLITTER_VERSION_7109c2,
  STM_BLITTER_VERSION_1000,
  STM_BLITTER_VERSION_ZEUS,
  STM_BLITTER_VERSION_7109c3,
  STM_BLITTER_VERSION_7200c1,
  STM_BLITTER_VERSION_CHRONUS,
  STM_BLITTER_VERSION_7200c2_7111_7141_7105,
  STM_BLITTER_VERSION_5197,
  STM_BLITTER_VERSION_7106_7108,
    /* for compatibility with old code */
    STM_BLITTER_VERSION_7108 = STM_BLITTER_VERSION_7106_7108,
  STM_BLITTER_VERSION_5206,
};

/* Note that this must always match the private declaration for
   struct _STMFBBdispSharedAreaPriv in include/stmdisplayblitter.h
   FIXME: need a way to enforce this during compilation... */
/* This struct occupies the first PAGE_SIZE bytes of the shared area. An
   application using it should mmap() this first page and the rest of the
   shared area independently, so as to get a cached view of this struct, and
   an uncached view of the remaining data. */
typedef struct { volatile int counter; } stmfb_atomic_t;
typedef volatile struct
{
  /* 0 */
  const unsigned long nodes_phys;

  /* 4 */
  unsigned long last_free; /* offset of last free node */

  /* 8,12,14,16 */
  unsigned long        bdisp_running;
  const unsigned short aq_index; /* which AQ is associated w/ this area, first is 0 */
  unsigned short       updating_lna;
  unsigned long        prev_set_lna; /* previously set LNA */

  /* 20,24,28 */
  unsigned int  num_irqs; /* total number of IRQs (not only LNA + NODE) */
  unsigned int  num_lna_irqs;  /* total LNA IRQs */
  unsigned int  num_node_irqs; /* total node IRQs */

  /**** cacheline ****/

  /* 0x20 32 */
  unsigned int  num_idle;

  /* 36 */
  unsigned long next_free;

  /* 40,44 */
  unsigned int  num_wait_idle;
  unsigned int  num_wait_next;

  /* 48,52,56 */
  unsigned int  num_starts;
  unsigned int  num_ops_hi;
  unsigned int  num_ops_lo;

  /* 60 */
  const unsigned long nodes_size;

  /**** cacheline ****/

  /* 0x40 64 */
  unsigned long last_lut;

  /* 68,76 */
  unsigned long long bdisp_ops_start_time;
  unsigned long long ticks_busy;

  /* 84,88,92 */
  stmfb_atomic_t lock; /* a lock for concurrent access -> use with
                          atomic_cmpxchg() in kernel and
                          __sync_bool_compare_and_swap() in userspace */
  unsigned char  locked_by; /* 0 == none, 2 == userspace, 3 == kernel
                               1 == old version of this struct, it had
                               the version information here */
  unsigned long  :24;  /* pad 89 */
  unsigned long  :32;  /* pad 92 */

  /**** cacheline ****/

  /* 0x60 96,100 */
  const unsigned long           version;  /* version of this structure */
  const enum stm_blitter_device device;   /* BDisp implementation */
} STMFBBDispSharedArea;


/*
 * non-standard ioctls to control the FB plane and blitter, although
 * these can be used directly they are really provided for the DirectFB
 * driver
 */
#define STMFBIO_GET_OUTPUTSTANDARDS     _IOWR('B', 0x10, struct stmfbio_outputstandards)
#define STMFBIO_GET_OUTPUTINFO          _IOWR('B', 0x11, struct stmfbio_outputinfo)
#define STMFBIO_SET_OUTPUTINFO          _IOWR('B', 0x12, struct stmfbio_outputinfo)
#define STMFBIO_GET_PLANEMODE           _IOWR('B', 0x11, struct stmfbio_planeinfo)
#define STMFBIO_SET_PLANEMODE           _IOW ('B', 0x12, struct stmfbio_planeinfo)
#define STMFBIO_PAN_PLANE               _IOW ('B', 0x10, struct stmfbio_planeinfo)

#define STMFBIO_GET_VAR_SCREENINFO_EX   _IOR ('B', 0x12, struct stmfbio_var_screeninfo_ex)
#define STMFBIO_SET_VAR_SCREENINFO_EX   _IOWR('B', 0x13, struct stmfbio_var_screeninfo_ex)
#define STMFBIO_GET_OUTPUT_CONFIG       _IOR ('B', 0x14, struct stmfbio_output_configuration)
#define STMFBIO_SET_OUTPUT_CONFIG       _IOWR('B', 0x15, struct stmfbio_output_configuration)
#define STMFBIO_SET_PICTURE_CONFIG      _IOWR('B', 0x16, struct stmfbio_picture_configuration)
#define STMFBIO_GET_AUXMEMORY2          _IOWR('B', 0x17, struct stmfbio_auxmem2)
#define STMFBIO_GET_SHARED_AREA         _IOR ('B', 0x18, struct stmfbio_shared)
#define STMFBIO_WAIT_NEXT               _IO  ('B', 0x19) /* similar to STMFBIO_SYNC_BLITTER, but will wakeup
                                                            upon next node interrupt, not necessarily LNA */
#define STMFBIO_GET_BLTLOAD             _IOR ('B', 0x1a, unsigned long)
#define STMFBIO_BLITTER_KICK            _IOW ('B', 0x1a, unsigned long)
#define STMFBIO_BLT                     _IOW ('B', 0x3,  STMFBIO_BLT_DATA)
#define STMFBIO_SET_BLITTER_PALETTE     _IOW ('B', 0x4,  STMFBIO_PALETTE)
#define STMFBIO_SYNC_BLITTER            _IO  ('B', 0x5)
#define STMFBIO_PRE_AUTH                _IO  ('B', 0xa)
#define STMFBIO_POST_AUTH               _IO  ('B', 0xb)
#define STMFBIO_WAIT_FOR_REAUTH         _IO  ('B', 0xc)
#define STMFBIO_SET_DAC_HD_POWER        _IO  ('B', 0xd)
#define STMFBIO_SET_CGMS_ANALOG         _IO  ('B', 0xe)
#define STMFBIO_SET_DAC_HD_FILTER       _IO  ('B', 0xf)
//#ifdef __TDT__ 
#define STMFBIO_BLT_EXTERN              _IOW ('B', 0x33,  STMFBIO_BLT_EXTERN_DATA)
//#endif

/*
 * Implement the matrox FB extension for VSync synchronisation, again for
 * DirectFB.
 */
#ifndef FBIO_WAITFORVSYNC
#define FBIO_WAITFORVSYNC       _IOW('F', 0x20, u_int32_t)
#endif

/* Accelerator type, reported to userspace applications, DirectFB etc.. */
#define FB_ACCEL_ST_NONE        99
#define FB_ACCEL_ST_GAMMA      100
#define FB_ACCEL_ST_BDISP      101
#define FB_ACCEL_ST_BDISP_USER 102

#endif /* _STMFB_H */
