SUMMARY = "Small command line utility used to perform frequency scans for DVB and ATSC transmissions"
DESCRIPTION = "Wscan is a dvb channel scanner that doesn't require an initial frequency table"
HOMEPAGE = "http://wirbel.htpc-forum.de/w_scan/index2.html"
MAINTAINER = "wirbel <w_scan@gmx-topmail.de>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=892f569a555ba9c07a568a7c0c4fa63a"

SRC_URI = "http://wirbel.htpc-forum.de/w_scan/w_scan-${PV}.tar.bz2"

SRC_URI[md5sum] = "57aa860b4c9e6aa480ca6eb0504bd4f5"
SRC_URI[sha256sum] = "2077af7f8b42b7af038e83abf0565f96f59461bbc5e14c4516b68f50b5c00d79"

S = "${WORKDIR}/w_scan-${PV}"

inherit autotools

FILES_${PN} += "${datadir}"
