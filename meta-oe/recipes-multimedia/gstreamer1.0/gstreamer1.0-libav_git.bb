SUMMARY = "Libav-based GStreamer 1.x plugin"
HOMEPAGE = "http://gstreamer.freedesktop.org/"
SECTION = "multimedia"

# ffmpeg has comercial license flags so add it as we need ffmpeg as a dependency
LICENSE_FLAGS = "commercial"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://ext/libav/gstav.h;beginline=1;endline=18;md5=a752c35267d8276fd9ca3db6994fca9c \
                    "

require gstreamer1.0-common.inc

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base ffmpeg"

SRC_URI = "git://gitlab.freedesktop.org/gstreamer/gst-libav.git;protocol=https;branch=1.18;name=gst_libav"

inherit meson pkgconfig

CFLAGS += "-Wno-implicit-function-declaration -Wno-stringop-overflow"

CFLAGS_remove_sh4 = "-Wno-stringop-overflow"
CFLAGS_append_sh4 = " -std=gnu99"

FILES_${PN} += "${libdir}/gstreamer-1.0/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-1.0/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-1.0/*.a"
