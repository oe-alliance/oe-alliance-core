DESCRIPTION = "Jedi-EPG-XStream"
HOMEPAGE = "https://github.com/kiddac/Jedi-EPG-XStream"
MAINTAINER = "kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc

require conf/python/python3-compileall.inc

DEPENDS = "${PYTHON_PN}-backports-lzma"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"
PR = "r3"

inherit gittag allarch

SRC_URI = "git://github.com/kiddac/Jedi-EPG-XStream.git;protocol=https;branch=main"

S = "${WORKDIR}/git/JediEPGXtream"

do_install () {
    install -d ${D}${libdir}
    cp -rf ${S}/usr/lib/* ${D}${libdir}/
    chmod -R a+rX ${D}${libdir}/
    install -d ${D}${sysconfdir}
    cp -rf ${S}/etc/* ${D}${sysconfdir}/
}

FILES:${PN} = "${libdir} ${sysconfdir}"
