DESCRIPTION = "Show movie details from TMDb"
HOMEPAGE = "https://github.com/oe-mirrors/enigma2-plugin-extensions-tmdb"
MAINTAINER = "schomi"
PRIORITY = "optional"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "${PYTHON_PN}-json ${PYTHON_PN}-requests ${PYTHON_PN}-pillow"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0.1+git${SRCPV}"
PKGV = "1.0.1+git${GITPKGV}"
VER ="1.0.1"
PR = "r0"

SRC_URI = "git://github.com/oe-mirrors/enigma2-plugin-extensions-tmdb.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

FILES:${PN} = "${libdir}"

do_install () {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
}

do_package_qa[noexec] = "1"
