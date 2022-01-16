DESCRIPTION = "E2 Piconizer - Picon Downloader/Creator"
HOMEPAGE = "https://github.com/kiddac/E2_Piconizer"
MAINTAINER = "kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc

SRCREV="${AUTOREV}"

PV = "1.xx+git${SRCPV}"
PKGV = "1.xx+git${GITPKGV}"
PR = "r0"
inherit gitpkgv allarch

SRC_URI = "git://github.com/kiddac/E2_Piconizer.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES_${PN} = "${sysconfdir} ${libdir}"

do_install () {
    install -d ${D}${sysconfdir}/enigma2/E2Piconizer
    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/E2Piconizer
    cp -rf ${S}/E2Piconizer/etc/enigma2/E2Piconizer/* ${D}${sysconfdir}/enigma2/E2Piconizer/
    cp -rf ${S}/E2Piconizer/usr/lib/enigma2/python/Plugins/Extensions/E2Piconizer/* ${D}${libdir}/enigma2/python/Plugins/Extensions/E2Piconizer/
}

pkg_postrm_${PN} () {
#!/bin/sh
echo "Removing E2Piconizer Plugin from the system ..."
rm -r /usr/lib/enigma2/python/Plugins/Extensions/E2Piconizer > /dev/null 2>&1
rm -r /etc/enigma2/E2Piconizer > /dev/null 2>&1
exit 0
}
