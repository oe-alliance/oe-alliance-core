SUMMARY = "Enigma2 Skin DMConcinnity HD"
MAINTAINER = "kerni"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "3.0+git"
PKGV = "3.0+git${GITPKGV}"
VER ="3.0"
PR = "r0"

SRC_URI="git://github.com/openatv/enigma2-plugin-skins-dmcconcinnityhd.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES:${PN} = "${libdir} /usr/share"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/usr/share/* ${D}/usr/share/
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"