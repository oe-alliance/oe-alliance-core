SUMMARY = "DVD access multimeda library"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=64e753fa7d1ca31632bc383da3b57c27"
PV = "4.2.0+svnr${SRCPV}"
PR = "r1"

SRC_URI = "svn://svn.mplayerhq.hu/dvdnav/trunk;module=${PN}"

SRCREV = "1243"
S = "${WORKDIR}/${PN}"

inherit autotools lib_package binconfig pkgconfig
