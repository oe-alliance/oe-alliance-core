SUMMARY = "Modification is based on open-source skin from Army. It is adapted to work on OpenPLi, OpenViX, OpenATV images."
MAINTAINER = "Taykun345/OldSkulRide from OpenPLi/ViX forums"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI="git://github.com/Taykun345/skin-PLi-Army-MoodBlueHD-mod.git;protocol=https"

FILES_${PN} = "/usr/share/enigma2/Army_MoodBlue_mod"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}/usr/
    cp -rp ${S}/usr/* ${D}/usr/
}

do_package_qa[noexec] = "1"
