SUMMARY = "Small command line utility used to perform frequency scans for DVB and ATSC transmissions"
DESCRIPTION = "w_scan2 is a small channel scan tool which generates ATSC, DVB-C, DVB-S/S2 and DVB-T/T2 channels.conf files"
HOMEPAGE = "https://github.com/stefantalpalaru/w_scan2"
MAINTAINER = "Stefan Talpalaru <stefantalpalaru@yahoo.com>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=892f569a555ba9c07a568a7c0c4fa63a"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0.5+git${SRCPV}"
PKGV = "1.0.5+git${GITPKGV}"

SRC_URI = "git://github.com/stefantalpalaru/w_scan2;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit autotools

FILES_${PN} += "${datadir}"
