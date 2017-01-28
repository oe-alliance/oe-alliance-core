SUMMARY = "SNES Game Manager"
MAINTAINER = "open alliance"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

RDEPENDS_${PN} += "directfb libsdl snes9x-sdl snes-init"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "0.1+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
VER ="0.1"
PR = "r0"

SRC_URI="git://github.com/emulatorE2/snes_manager.git"

S = "${WORKDIR}/git/src"

FILES_${PN} = "/usr/* "


do_install() {
    cp -rp ${S}/usr ${D}/
    chmod 777 ${D}/usr/bin/snes_run.sh
}

do_package_qa[noexec] = "1"