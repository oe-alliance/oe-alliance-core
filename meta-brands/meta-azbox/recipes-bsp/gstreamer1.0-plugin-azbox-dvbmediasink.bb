SUMMARY = "gstreamer dvbmediasink plugin"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LIC_FILES_CHKSUM = "file://COPYING;md5=7fbc338309ac38fefcd64b04bb903e34"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base libdca"

GSTVERSION = "1.0"

#SRC_URI = "git://github.com/christophecvr/gstreamer1.0-plugin-multibox-dvbmediasink;protocol=https"
SRC_URI = "git://github.com/dazulrich/gstreamer1.0-plugin-multibox-dvbmediasink.git;branch=oatv6-dev;protocol=https"

S = "${WORKDIR}/git"

inherit gitpkgv pkgconfig

SRCREV = "${AUTOREV}"
PV = "${GSTVERSION}+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
PR = "r01"

inherit autotools

do_configure_prepend() {
    sed -i 's/AC_INIT.*$/AC_INIT(gst-plugin-dvbmediasink, 1.0.0, @pli4)/' ${S}/configure.ac
    sed -i 's/AM_INIT_AUTOMAKE.*$/AM_INIT_AUTOMAKE([foreign])/' ${S}/configure.ac
    touch ${S}/NEWS
    touch ${S}/README
    touch ${S}/AUTHORS
    touch ${S}/ChangeLog
}

FILES_${PN} = "${libdir}/gstreamer-${GSTVERSION}/*.so*"
FILES_${PN}-dev += "${libdir}/gstreamer-${GSTVERSION}/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-${GSTVERSION}/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-${GSTVERSION}/.debug"

EXTRA_OECONF = "${DVBMEDIASINK_CONFIG} --with-gstversion=${GSTVERSION} --with-machine=${MACHINE}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
