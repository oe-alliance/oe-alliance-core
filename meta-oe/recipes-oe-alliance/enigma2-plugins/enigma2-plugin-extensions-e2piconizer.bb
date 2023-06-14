DESCRIPTION = "E2 Piconizer - Picon Downloader/Creator"
HOMEPAGE = "https://github.com/kiddac/E2_Piconizer"
MAINTAINER = "kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gittag allarch

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/kiddac/E2_Piconizer.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES:${PN} = "${sysconfdir} ${libdir}"

do_install () {
    install -d ${D}${sysconfdir}/enigma2/E2Piconizer
    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/E2Piconizer
    cp -rf ${S}/E2Piconizer/etc/enigma2/E2Piconizer/* ${D}${sysconfdir}/enigma2/E2Piconizer/
    cp -rf ${S}/E2Piconizer/usr/lib/enigma2/python/Plugins/Extensions/E2Piconizer/* ${D}${libdir}/enigma2/python/Plugins/Extensions/E2Piconizer/
}
