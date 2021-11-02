SUMMARY = "snes_init wake up framme buffer for enigma2"
MAINTAINER = "DDamir@2015"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "GPLv2"

DEPENDS = "directfb"

require conf/license/license-gplv2.inc

SRCREV = "${AUTOREV}"
PV = "1.53+git${SRCPV}"
PKGV = "1.53+git${GITPKGV}"
VER ="1.53"

inherit gitpkgv pkgconfig

SRC_URI = "git://github.com/emulatorE2/snes_init.git;protocol=https"

S = "${WORKDIR}/git"

FILES_${PN} = "${bindir}/*"

do_compile() {
    make -f Makefile snes_init
    ${STRIP} ${S}/snes_init
}

do_install() {
    install -d ${D}/${bindir}
    install -m 755 ${S}/snes_init ${D}/${bindir}
}
