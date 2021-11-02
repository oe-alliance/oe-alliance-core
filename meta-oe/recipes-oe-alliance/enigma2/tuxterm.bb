SUMMARY = "TuxTerm"
MAINTAINER = "Sven Karschewski <seddi@i-have-a-dreambox.com>"
DEPENDS = "freetype"

LICENSE = "PD"
LIC_FILES_CHKSUM = "file://COPYING;md5=7968df00b3e6be507316ed9cfc8be290"

PN = "tuxterm"
PV = "0.2+git${SRCPV}"
PR = "r0"

SRC_URI = "git://github.com/sklnet/tuxterm.git;protocol=https"

S = "${WORKDIR}/git"

inherit autotools

FILES_${PN} = "/"

EXTRA_OECONF = "${@bb.utils.contains("MACHINE_FEATURES", "32bpp", "--with-bpp=32" , "--with-bpp=8", d)}"
