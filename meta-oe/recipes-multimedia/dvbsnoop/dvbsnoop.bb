SUMMARY = "DVB / MPEG stream analyzer"
AUTHOR = "Rainer Scherg <rasc@users.sourceforge.net>"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
PV = "1.4.50"
PR = "r1"

SRC_URI = "http://sourceforge.net/projects/dvbsnoop/files/latest/download/dvbsnoop-${PV}.tar.gz"

SRC_URI[md5sum] = "68a5618c95b4372eea9ac5ec5005f299"
SRC_URI[sha256sum] = "7658498b26a5d2a0242e81f0cfafa0e43a2bec56f8674e7ac197dfc310866ec6"

S = "${WORKDIR}/dvbsnoop-${PV}"

inherit autotools
