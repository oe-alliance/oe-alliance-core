DESCRIPTION = "DVB / MPEG stream analyzer"
SUMMARY = "DVB / MPEG stream analyzer"
MAINTAINER = "Persian Professionals"
AUTHOR = "Rainer Scherg <rasc@users.sourceforge.net>"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit gitpkgv

SRCREV = "c1ec72fc63339dc5fc38f6a912f21aa23a688511"
PV = "1.4.53"
PKGV = "1.4.53+git${GITPKGV}"

SRC_URI = "git://github.com/PLi-metas/dvbsnoop.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools
