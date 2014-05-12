SUMMARY = "Tuxbox Terminal plugin"
MAINTAINER = "Sven Karschewski <seddi@i-have-a-dreambox.com>"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://COPYING;md5=7968df00b3e6be507316ed9cfc8be290"

RDEPENDS_${PN} = "tuxterm"

PV = "0.2+svn${SRCPV}"
PR = "r2"

SRC_URI = "${PLISVNURL}/${PLISVNBRANCH}/external;module=tuxterm-enigma2-plugin;protocol=${PLISVNPROTO}"

S = "${WORKDIR}/tuxterm-enigma2-plugin"

inherit autotools-brokensep

FILES_${PN} = "/"
