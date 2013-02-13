DESCRIPTION = "font-valis-enigma"
MAINTAINER = "valis"
require conf/license/license-gplv2.inc
PACKAGE_ARCH = "all"

PV = "2009.11.12"
PR = "r1"


SRC_URI="file://valis_enigma.ttf"

do_install() {
	install -d ${D}/usr/share/fonts
	install -m 0644 ${WORKDIR}/valis_enigma.ttf	${D}/usr/share/fonts/valis_enigma.ttf
}
