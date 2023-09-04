DESCRIPTION = "gstreamer subsink plugin"
SECTION = "multimedia"
PRIORITY = "optional"
DEPENDS = "glib-2.0-native gstreamer1.0 gstreamer1.0-plugins-base"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=7fbc338309ac38fefcd64b04bb903e34"

SRC_URI = "git://github.com/christophecvr/gstreamer1.0-plugin-subsink.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit gitpkgv

GSTVERSION = "1.0"

PV = "${GSTVERSION}+git"
PKGV = "${GSTVERSION}+git${GITPKGV}"
PR = "r0"

EXTRA_OECONF = "--with-gstversion=${GSTVERSION}"

do_configure:prepend() {
        sed -i 's/AC_INIT.*$/AC_INIT(gst-plugin-subsink, 1.0.0, @pli4)/' ${S}/configure.ac
        sed -i 's/AM_INIT_AUTOMAKE.*$/AM_INIT_AUTOMAKE([foreign subdir-objects])/' ${S}/configure.ac
}

inherit autotools pkgconfig

FILES:${PN} = "${libdir}/gstreamer-${GSTVERSION}/*.so*"
FILES:${PN}-dev += "${libdir}/gstreamer-${GSTVERSION}/*.la"
FILES:${PN}-staticdev += "${libdir}/gstreamer-${GSTVERSION}/*.a"
FILES:${PN}-dbg += "${libdir}/gstreamer-${GSTVERSION}/.debug"

PACKAGE_ARCH = "${MACHINE_ARCH}"
