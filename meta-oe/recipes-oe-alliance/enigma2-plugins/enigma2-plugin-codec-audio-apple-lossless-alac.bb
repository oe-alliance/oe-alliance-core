SUMMARY = "Apple Lossless Audio Codec (ALAC)"
inherit allarch

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
		gstreamer1.0-libav \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
