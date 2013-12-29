SUMMARY = "gstreamer dvbmediasink plugin"
SECTION = "multimedia"
PRIORITY = "optional"
DEPENDS = "gstreamer gst-plugins-base libdca"
LICENSE = "proprietary"

SRC_URI[md5sum] = "c2054a1ec3c8dd5803af2addba349a13"
SRC_URI[sha256sum] = "93310712e573638629f0ca05cdbfbb5db59b64e4b303d69410689c64339c57e6"

RREPLACES_${PN} = "gst-plugin-dvbmediasink"
RCONFLICTS_${PN} = "gst-plugin-dvbmediasink"

SRC_URI = "http://archiv.openmips.com/dvbmediasink-1.0.tar.gz"

S = "${WORKDIR}/dvbmediasink"

PV = "0.10.0"
PR = "r0"

inherit autotools pkgconfig

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so*"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la ${libdir}/gstreamer-0.10/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"


EXTRA_OECONF = "--with-wma --with-wmv --with-pcm --with-dtsdownmix"


