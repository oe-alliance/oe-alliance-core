DESCRIPTION = "Shared component for skins by KiddaC"
HOMEPAGE = "https://github.com/kiddac/Enigma2_Skins"
MAINTAINER = "kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc

SRCREV = "${AUTOREV}"

PV = "1.01+git${SRCPV}"
PKGV = "1.01+git${GITPKGV}"
PR = "r1"

inherit gitpkgv
SRC_URI="git://github.com/kiddac/Enigma2_Skins.git;protocol=https;branch=master"

S = "${WORKDIR}/git/1080_Skins/kiddac-shared-skin-components/kiddac-shared-skin-components"

FILES_${PN}-src = "${datadir}/enigma2/slyk-common/*.txt"
FILES_${PN} = "${libdir} ${datadir}"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${datadir}
    cp -rf ${S}${libdir}/* ${D}${libdir}/
    cp -rf ${S}${datadir}/* ${D}${datadir}/
}
