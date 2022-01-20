SUMMARY = "Tuxbox Terminal plugin"
MAINTAINER = "Sven Karschewski <seddi@i-have-a-dreambox.com>"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://COPYING;md5=7968df00b3e6be507316ed9cfc8be290"
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "tuxterm"

PV = "0.2+git${SRCPV}"
PR = "r0"

SRC_URI = "git://github.com/sklnet/tuxterm-enigma2-plugin.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit autotools-brokensep

FILES:${PN} = "/"
