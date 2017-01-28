DESCRIPTION = "DVB / MPEG stream analyzer"
SUMMARY = "DVB / MPEG stream analyzer"
MAINTAINER = "Persian Professionals"
AUTHOR = "Rainer Scherg <rasc@users.sourceforge.net>"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit gitpkgv

SRCREV = "7763d9995c06dc1625ad7d873dde578a8c61d480"
PV = "1.4.53"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/persianpros/dvbsnoop.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools
