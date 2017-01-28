SUMMARY = "CoolTVGuide MultiEPG"
MAINTAINER = "Coolman <coolman@uni.de>"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "7.6+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
VER ="7.6"
PR = "r0"


SRC_URI="git://github.com/openatv/enigma2-plugin-extensions-cooltvguide.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"


do_install() {
    cp -rp ${S}/usr ${D}/
}

do_package_qa[noexec] = "1"
