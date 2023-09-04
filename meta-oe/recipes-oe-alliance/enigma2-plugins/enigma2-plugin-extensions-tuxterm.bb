SUMMARY = "Tuxbox Terminal plugin"
MAINTAINER = "Sven Karschewski <seddi@i-have-a-dreambox.com>"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://COPYING;md5=7968df00b3e6be507316ed9cfc8be290"
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "tuxterm"

PV = "V0.2+git"
PKGV = "V0.2+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/sklnet/tuxterm-enigma2-plugin.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit gitpkgv autotools-brokensep

FILES:${PN} = "/"
