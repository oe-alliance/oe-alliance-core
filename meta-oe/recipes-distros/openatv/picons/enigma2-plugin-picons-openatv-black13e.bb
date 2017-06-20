SUMMARY = "picons-openatv-black13E"
MAINTAINER = "ATV Team"
SECTION = "base"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r1"


SRC_URI="git://github.com/openatv-picons/enigma2-plugin-picons-openatv-black13E.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/* "

do_install() {
    cp -rp ${S}/usr ${D}/
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"