SUMMARY = "Libav-based GStreamer 1.x plugin"
HOMEPAGE = "http://gstreamer.freedesktop.org/"
SECTION = "multimedia"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d"

require gstreamer1.0-common.inc

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base ffmpeg"

SRCREV_FORMAT = "gst_libav"

SRC_URI = "git://gitlab.freedesktop.org/gstreamer/gst-libav;protocol=https;branch=1.18;name=gst_libav"

inherit pkgconfig

EXTRA_OEMESON = "-Ddoc=disabled"

CFLAGS += "-Wno-implicit-function-declaration -Wno-stringop-overflow"

CFLAGS:remove:sh4 = "-Wno-stringop-overflow"
CFLAGS:append:sh4 = " -std=gnu99"

FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"
FILES:${PN}-dev += "${libdir}/gstreamer-1.0/*.la"
FILES:${PN}-staticdev += "${libdir}/gstreamer-1.0/*.a"
