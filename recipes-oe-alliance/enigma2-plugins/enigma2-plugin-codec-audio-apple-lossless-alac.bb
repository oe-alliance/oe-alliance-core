DESCRIPTION = "Apple Lossless Audio Codec (ALAC)"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
	libtheora \
	libavcodec \
	libavformat \
	gst-ffmpeg \	
	"

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"
