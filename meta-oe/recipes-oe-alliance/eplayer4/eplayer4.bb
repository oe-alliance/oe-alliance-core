SUMMARY = "eplayer4 a simple gst videoplayer"
MAINTAINER = "Duckbox Team"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "gstreamer gst-plugins-base glib-2.0 libxml2"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r2"

inherit pkgconfig

SRC_URI = "file://eplayer4.c file://Makefile"

S = "${WORKDIR}"

FILES_${PN} = "${bindir}/*"

do_compile() {
    make -f Makefile eplayer4
    ${STRIP} ${S}/eplayer4
}

do_install() {
    install -d ${D}/${bindir}
    install -m 755 ${S}/eplayer4 ${D}/${bindir}
}

