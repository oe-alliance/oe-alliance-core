DESCRIPTION = "Kiddac Slyk-q-1080 skin"
MAINTAINER = "Kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "enigma2-plugin-skincomponents-kiddac-shared-skin"

inherit gittag

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI="git://github.com/kiddac/Slyk_Q_1080.git;protocol=https;branch=master"

S = "${WORKDIR}/git//Slyk_Q_1080"

FILES:${PN} = "${datadir}"

do_install() {
    install -d ${D}${datadir}
    cp -rf ${S}${datadir}/* ${D}${datadir}/
}
