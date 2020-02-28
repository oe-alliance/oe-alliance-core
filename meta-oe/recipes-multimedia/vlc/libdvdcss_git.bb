DESCRIPTION = "libdvdcss is a simple library designed for accessing DVDs like a block device without having to bother about the decryption."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.4.3+git${SRCPV}"
PKGV = "1.4.3+git${GITPKGV}"

SRC_URI = "git://code.videolan.org/videolan/libdvdcss.git;protocol=https"

S = "${WORKDIR}/git"

inherit autotools

EXTRA_OECONF = "--disable-doc"
