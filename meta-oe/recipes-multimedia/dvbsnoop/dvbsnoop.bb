DESCRIPTION = "DVB / MPEG stream analyzer"
SUMMARY = "DVB / MPEG stream analyzer"
MAINTAINER = "PersianPros"
AUTHOR = "Rainer Scherg <rasc@users.sourceforge.net>"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit gitpkgv

SRCREV = "3af4a3ed257e15f133aa61b894d787a7feeb64a2"
PV = "1.4.51"
PKGV = "1+git${GITPKGV}"

SRC_URI = "git://github.com/persianpros/dvbsnoop.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools
