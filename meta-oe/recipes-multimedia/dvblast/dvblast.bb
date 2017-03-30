SUMMARY = "DVBlast is a simple and powerful MPEG-2/TS demux and streaming application"
DESCRIPTION = "DVBlast is written to be the core of a custom IRD, CID, or ASI gateway, \
	based on a PC with a Linux-supported card. It is very lightweight and stable, designed for 24/7 operation."
SECTION = "multimedia"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=ed7e492ee44e70125a5d42e118354a13"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

SRCREV = "446507480162eaeb185c6ac124810a6fe972b798"
DEPENDS = "bitstream libev"

SRC_URI = "git://code.videolan.org/videolan/dvblast.git;protocol=http"

S = "${WORKDIR}/git"

inherit autotools-brokensep

do_compile_prepend() {
        sed -i 's#/usr/local#/usr#' ${S}/Makefile
}

