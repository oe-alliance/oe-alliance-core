SUMMARY = "Valis hd fonts"
MAINTAINER = "valis"
require conf/license/license-gplv2.inc
PACKAGE_ARCH = "all"

PV = "2010.05.14"
PR = "r4"

PACKAGES = "font-valis-hd"
PROVIDES = "font-valis-hd"

SRC_URI="file://hd.ttf file://hdi.ttf"

FILES_font-valis-hd = "/usr/*"

do_install() {
    install -d ${D}/usr/share/fonts
    install -m 0644 ${WORKDIR}/*.ttf ${D}/usr/share/fonts
}
