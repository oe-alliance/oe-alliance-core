SUMMARY = "eplayer5 a simple gst videoplayer"
MAINTAINER = "Duckbox Team"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "GPLv2"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base glib-2.0 libxml2"

require conf/license/license-gplv2.inc

PV = "1.0"

inherit pkgconfig

SRC_URI = "file://eplayer5.c file://Makefile"

S = "${WORKDIR}"

FILES_${PN} = "${bindir}/*"

do_compile() {
    make -f Makefile eplayer5
}

do_install() {
    install -d ${D}/${bindir}
    install -m 755 ${S}/eplayer5 ${D}/${bindir}
}

INSANE_SKIP_${PN} += "ldflags"
