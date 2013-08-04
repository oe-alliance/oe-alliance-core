SUMMARY = "Wscan is a dvb channel scanner that doesn't require an initial frequency table"
LICENSE = "GPLv2"
PR = "r1"

SRC_URI = "http://wirbel.htpc-forum.de/w_scan/w_scan-${PV}.tar.bz2"

SRC_URI[md5sum] = "93c1a61992cac35d0efcf14a0ef2bd25"
SRC_URI[sha256sum] = "1731a17317a75f9c37732654c10070acf4058b70757f762de8be05d8f5fcf838"

S = "${WORKDIR}/w_scan-${PV}"

inherit autotools

FILES_${PN} += "${datadir}"

