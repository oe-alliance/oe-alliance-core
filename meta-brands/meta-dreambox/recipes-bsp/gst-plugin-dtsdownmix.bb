SUMMARY = "gstreamer dtsdownmix plugin"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=7fbc338309ac38fefcd64b04bb903e34"

DEPENDS = "gstreamer gst-plugins-base libdca"

SRC_URI = "git://github.com/athoik/${PN}.git"

S = "${WORKDIR}/git"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.10.0+git${SRCPV}"
PKGV = "0.10.0+git${GITPKGV}"
PR = "r0"

inherit autotools pkgconfig

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so*"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"

PACKAGE_ARCH = "${MACHINE_ARCH}"
