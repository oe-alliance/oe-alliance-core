SUMMARY = "Libav-based GStreamer 1.x plugin"
HOMEPAGE = "http://gstreamer.freedesktop.org/"
SECTION = "multimedia"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d"

require gstreamer1.0-common.inc

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base ffmpeg"

SRCREV_FORMAT = "gst_libav"

SRC_URI = "git://gitlab.freedesktop.org/gstreamer/gst-libav;protocol=https;branch=master;name=gst_libav"

inherit pkgconfig

EXTRA_OEMESON = "-Ddoc=disabled"

CFLAGS += "-Wno-implicit-function-declaration -Wno-stringop-overflow"

CFLAGS_remove_sh4 = "-Wno-stringop-overflow"
CFLAGS_append_sh4 = " -std=gnu99"

FILES_${PN} += "${libdir}/gstreamer-1.0/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-1.0/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-1.0/*.a"
