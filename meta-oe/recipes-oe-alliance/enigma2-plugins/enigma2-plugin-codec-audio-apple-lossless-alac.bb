SUMMARY = "Apple Lossless Audio Codec (ALAC)"
inherit allarch

require conf/license/license-gplv2.inc

RDEPENDS:${PN} = " \
		gstreamer1.0-libav \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY:${PN} = "1"
