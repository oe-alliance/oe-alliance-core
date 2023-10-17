DESCRIPTION = "Jedi-EPG-XStream"
HOMEPAGE = "https://github.com/kiddac/Jedi-EPG-XStream"
MAINTAINER = "kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc

require conf/python/python3-compileall.inc

DEPENDS = "${PYTHON_PN}-backports-lzma"

SRCREV = "${AUTOREV}"
PV = "1.01+git${SRCPV}"
PKGV = "1.01+git${GITPKGV}"
PR = "r2"

inherit gitpkgv allarch

SRC_URI = "git://github.com/kiddac/Jedi-EPG-XStream.git;protocol=https;branch=main"

S = "${WORKDIR}/git/JediEPGXtream"

do_install () {
    install -d ${D}${libdir}
    cp -rf ${S}/usr/lib/* ${D}${libdir}/
    chmod -R a+rX ${D}${libdir}/
    install -d ${D}${sysconfdir}
    cp -rf ${S}/etc/* ${D}${sysconfdir}/
}

pkg_postrm:${PN} () {
#!/bin/sh
    rm -rf /etc/enigma2/jediepgxtream/*.txt > /dev/null 2>&1
    rm -rf /etc/enigma2/jediepgxtream/*/*.txt > /dev/null 2>&1
    exit 0
}

FILES:${PN} = "${libdir} ${sysconfdir}"
