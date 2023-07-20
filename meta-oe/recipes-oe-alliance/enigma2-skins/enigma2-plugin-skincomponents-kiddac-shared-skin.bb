DESCRIPTION = "Shared component for skins by KiddaC"
HOMEPAGE = "https://github.com/kiddac/shared-skin-components"
MAINTAINER = "kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gittag

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "${GITPKGVTAG}"

SRC_URI="git://github.com/kiddac/shared-skin-components.git;protocol=https;branch=master"

S = "${WORKDIR}/git/kiddac-shared-skin-components"

FILES:${PN} = "${libdir} ${datadir}"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${datadir}
    cp -rf ${S}${libdir}/* ${D}${libdir}/
    cp -rf ${S}${datadir}/* ${D}${datadir}/
}
