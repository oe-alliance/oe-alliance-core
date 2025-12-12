SUMMARY = "stein17 BootlogoSwitcher Plugin for ATV Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"
VER = "1.0"

SRC_URI = "git://github.com/stein17/Skins-for-openATV.git;protocol=https;branch=python3"

FILES:${PN} = "${libdir}"

S = "${UNPACKDIR}/${BP}/BootlogoSwitscher"

do_install() {
    install -d ${D}${libdir}
    cp --no-preserve=ownership --recursive ${S}/usr/lib/* ${D}${libdir}/
}
