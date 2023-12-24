SUMMARY = "CoolTVGuide MultiEPG"
MAINTAINER = "Coolman <coolman@uni.de>"
SECTION = "base"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "7.6+git"
PKGV = "7.6+git${GITPKGV}"
VER ="7.6"
PR = "r0"


SRC_URI="git://github.com/openatv/enigma2-plugin-extensions-cooltvguide.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES:${PN} = "${libdir}"


do_install() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
}

do_package_qa[noexec] = "1"
