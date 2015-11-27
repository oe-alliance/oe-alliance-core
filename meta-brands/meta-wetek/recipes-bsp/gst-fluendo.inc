SUMMARY = "Fluendo closed-format GStreamer plug-in"
SECTION = "multimedia"
HOMEPAGE = "http://www.fluendo.com/resources/source-plugins.php"
DEPENDS = "gstreamer zlib"

inherit autotools pkgconfig

SRC_URI = "http://core.fluendo.com/gstreamer/src/${BPN}/${BPN}-${PV}.tar.bz2 \
    file://gst-fluendo-mpegdemux-0.10.72-gstmpegdefs.h.patch \
    file://gst-fluendo-mpegdemux-0.10.72-mpegtsdemux.c.patch \
    file://gst-fluendo-mpegdemux-0.10.72-mpegtsdemux.h.patch \
"

FILES_${PN} += "${libdir}/gstreamer-0.10/*.so"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la ${libdir}/gstreamer-0.10/*.a"

EXTRA_OECONF = "--disable-valgrind"
