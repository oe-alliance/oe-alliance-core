SUMMARY = "Valis hd fonts"
MAINTAINER = "valis"
require conf/license/license-gplv2.inc
PACKAGE_ARCH = "all"

PV = "2010.05.14"
PR = "r1"


SRC_URI="file://hd.ttf file://hdi.ttf"

FILES_${PN} = "/usr/*"

do_install() {
    install -d ${D}/usr/share/fonts
    install -m 0644 ${WORKDIR}/*.ttf ${D}/usr/share/fonts
}
