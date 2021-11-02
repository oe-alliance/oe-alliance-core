DESCRIPTION = "DVB / MPEG stream analyzer"
SUMMARY = "DVB / MPEG stream analyzer"
MAINTAINER = "Open Vision Developers"
AUTHOR = "Rainer Scherg <rasc@users.sourceforge.net>"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.4.55+git${SRCPV}"
PKGV = "1.4.55+git${GITPKGV}"

SRC_URI = "git://github.com/OpenVisionE2/dvbsnoop.git;protocol=https"

S = "${WORKDIR}/git"

inherit autotools
