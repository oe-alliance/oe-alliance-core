SUMMARY = "Apple Lossless Audio Codec (ALAC)"
inherit allarch

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
		${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-libav", "libtheora0 libavcodec53 libavformat53 gst-ffmpeg", d)} \
	"

PV = "1.0"
PR = "r4"

ALLOW_EMPTY_${PN} = "1"
