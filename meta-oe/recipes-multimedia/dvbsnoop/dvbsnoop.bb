DESCRIPTION = "DVB / MPEG stream analyzer"
SUMMARY = "DVB / MPEG stream analyzer"
MAINTAINER = "Persian Professionals"
AUTHOR = "Rainer Scherg <rasc@users.sourceforge.net>"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit gitpkgv

SRCREV = "28c325e56102fc08c2d30d77ea825be6ce4547e0"
PV = "1.4.52"
PKGV = "1.4.52+git${GITPKGV}"

SRC_URI = "git://github.com/persianpros/dvbsnoop.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools
