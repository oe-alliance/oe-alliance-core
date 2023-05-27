DESCRIPTION = "E2 Piconizer - Picon Downloader/Creator"
HOMEPAGE = "https://github.com/kiddac/E2_Piconizer"
MAINTAINER = "kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

SRCREV="${AUTOREV}"

PV = "1.01+git${SRCPV}"
PKGV = "1.01+git${GITPKGV}"
PR = "r2"
inherit gitpkgv allarch

SRC_URI = "git://github.com/kiddac/E2_Piconizer.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES:${PN} = "${sysconfdir} ${libdir}"

do_install () {
    install -d ${D}${sysconfdir}/enigma2/E2Piconizer
    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/E2Piconizer
    cp -rf ${S}/E2Piconizer/etc/enigma2/E2Piconizer/* ${D}${sysconfdir}/enigma2/E2Piconizer/
    cp -rf ${S}/E2Piconizer/usr/lib/enigma2/python/Plugins/Extensions/E2Piconizer/* ${D}${libdir}/enigma2/python/Plugins/Extensions/E2Piconizer/
}

pkg_postrm:${PN} () {
#!/bin/sh
    rm -r /usr/lib/enigma2/python/Plugins/Extensions/E2Piconizer > /dev/null 2>&1
    rm -r /etc/enigma2/E2Piconizer/*/*.png > /dev/null 2>&1
    exit 0
}
