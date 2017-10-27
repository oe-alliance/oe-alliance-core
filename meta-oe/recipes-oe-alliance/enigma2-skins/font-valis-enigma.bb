SUMMARY = "font-valis-enigma"
MAINTAINER = "valis"
require conf/license/license-gplv2.inc
inherit allarch

PV = "2009.11.12"
PR = "r0"

S = "${WORKDIR}"

SRC_URI="file://valis_enigma.ttf"

FILES_${PN} = "/usr/*"

do_install() {
    install -d ${D}/usr/share/fonts
    install -m 0644 ${WORKDIR}/valis_enigma.ttf    ${D}/usr/share/fonts/valis_enigma.ttf
}
