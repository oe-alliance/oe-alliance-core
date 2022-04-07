DESCRIPTION = "Jedi-EPG-XStream"
HOMEPAGE = "https://github.com/kiddac/Jedi-EPG-XStream"
MAINTAINER = "kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
DEPENDS = "${PYTHON_PN}-backports-lzma"
SRCREV = "${AUTOREV}"
PV = "2.04+git${SRCPV}"
PKGV = "2.04+git${GITPKGV}"
inherit gitpkgv allarch
SRC_URI = "git://github.com/kiddac/Jedi-EPG-XStream.git;protocol=https;branch=main"
S = "${WORKDIR}/git/JediEPGXtream"
FILES:${PN} = "${libdir} ${sysconfdir}"
do_install () {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    chmod -R a+rX ${D}${libdir}/
    install -d ${D}${sysconfdir}
    cp -rp ${S}/etc/* ${D}${sysconfdir}/
}

pkg_postrm:${PN} () {
#!/bin/sh
        rm -rf /etc/enigma2/jediepgxtream > /dev/null 2>&1
        echo "Restart GUI to finish uninstall!"
}
