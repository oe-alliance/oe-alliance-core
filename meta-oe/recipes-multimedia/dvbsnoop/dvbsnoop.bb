SUMMARY = "DVB / MPEG stream analyzer"
AUTHOR = "Rainer Scherg <rasc@users.sourceforge.net>"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
PV = "1.4.50"
PR = "r2"

SRC_URI = "http://sourceforge.net/projects/dvbsnoop/files/latest/download/dvbsnoop-${PV}.tar.gz"

SRC_URI[md5sum] = "bdee38f9f9843577ed1123ce433cf4cb"
SRC_URI[sha256sum] = "4e5fb42dc00b06e3bd90d442f73dc23aa14df4fe11e3bac16b88f9185d8c9b51"

S = "${WORKDIR}/dvbsnoop-${PV}"

inherit autotools
