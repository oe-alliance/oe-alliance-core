SUMMARY = "gstreamer dvbmediasink plugin"
SECTION = "multimedia"
PRIORITY = "optional"
require conf/license/license-gplv2.inc

DEPENDS = "gstreamer gst-plugins-base libdca"

SRC_URI = "git://github.com/OpenAZBox/azbox-dvbmediasink.git;protocol=git"

S = "${WORKDIR}/git"

inherit gitpkgv

SRCREV ?= "${AUTOREV}"
PV = "0.10.0+git${SRCPV}"
PKGV = "0.10.0+git${GITPKGV}"
PR = "r00"

inherit autotools pkgconfig

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so*"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"

PACKAGE_ARCH = "${MACHINE_ARCH}"

EXTRA_OECONF = "${DVBMEDIASINK_CONFIG}"
