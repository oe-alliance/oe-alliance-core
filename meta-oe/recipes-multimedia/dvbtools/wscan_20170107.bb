SUMMARY = "Small command line utility used to perform frequency scans for DVB and ATSC transmissions"
DESCRIPTION = "Wscan is a dvb channel scanner that doesn't require an initial frequency table"
HOMEPAGE = "http://wirbel.htpc-forum.de/w_scan/index2.html"
MAINTAINER = "wirbel <w_scan@gmx-topmail.de>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=892f569a555ba9c07a568a7c0c4fa63a"

SRC_URI = "http://wirbel.htpc-forum.de/w_scan/w_scan-${PV}.tar.bz2"

SRC_URI[md5sum] = "c7f9adf92d46b8da5391be80be6fbd72"
SRC_URI[sha256sum] = "38e0f38a7bf06cff6d6ea01652ad4ee60da2cb0e937360468f936da785b46ffe"

S = "${WORKDIR}/w_scan-${PV}"

inherit autotools

FILES_${PN} += "${datadir}"
