SUMMARY = "DVBlast is a simple and powerful MPEG-2/TS demux and streaming application"
DESCRIPTION = "DVBlast is written to be the core of a custom IRD, CID, or ASI gateway, \
            based on a PC with a Linux-supported card. It is very lightweight and \
            stable, designed for 24/7 operation."
SECTION = "multimedia"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=ed7e492ee44e70125a5d42e118354a13"

DEPENDS = "bitstream libev"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "3.4+git"
PKGV = "3.4+git${GITPKGV}"

SRC_URI = "git://code.videolan.org/videolan/dvblast.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit autotools-brokensep

do_compile:prepend() {
        sed -i 's#/usr/local#/usr#' ${S}/Makefile
}
