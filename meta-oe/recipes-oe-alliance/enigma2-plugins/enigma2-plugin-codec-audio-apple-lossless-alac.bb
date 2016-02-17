SUMMARY = "Apple Lossless Audio Codec (ALAC)"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
	gstreamer1.0-libav \
	"

PV = "1.0"
PR = "r4"

ALLOW_EMPTY_${PN} = "1"
