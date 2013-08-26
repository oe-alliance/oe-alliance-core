DESCRIPTION = "Apple Lossless Audio Codec (ALAC)"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
	libtheora0 \
	libavcodec53 \
	libavformat53 \
	gst-ffmpeg \
	"

PV = "1.0"
PR = "r3"

ALLOW_EMPTY_${PN} = "1"
