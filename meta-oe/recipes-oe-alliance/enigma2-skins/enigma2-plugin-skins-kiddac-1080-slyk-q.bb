DESCRIPTION = "Kiddac Slyk-q-1080 skin"
MAINTAINER = "Kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc

RDEPENDS_${PN} = "enigma2-plugin-skincomponents-kiddac-shared-skin"

inherit gitpkgv

SRCREV = "${AUTOREV}"

PV = "1.30+git${SRCPV}"
PKGV = "1.30+git${GITPKGV}"
PR = "r1"

SRC_URI="git://github.com/kiddac/Enigma2_Skins.git;protocol=https;branch=master"

S = "${WORKDIR}/git/1080_Skins/Slyk_Q_1080/Slyk_Q_1080"

FILES_${PN} = "${datadir}"

do_install() {
    install -d ${D}${datadir}
    cp -rf ${S}${datadir}/* ${D}${datadir}/
}
