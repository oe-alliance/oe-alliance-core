SUMMARY = "Modification is based on open-source skin from Army. It is adapted to work on Unibox images."
MAINTAINER = "Taykun345/OldSkulRide from OpenPLi/ViX forums"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r4"

SRC_URI="git://github.com/hdeeco/default-skin.git;protocol=git"

FILES_${PN} = "/usr/share/enigma2/Army_MoodBlue_mod"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}/usr/
    cp -rp ${S}/usr/* ${D}/usr/
}
