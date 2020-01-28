SUMMARY = "dav1d is an AV1 decoder"
HOMEPAGE = "https://code.videolan.org/videolan/dav1d"
SECTION = "libs/multimedia"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=c8055cfe7548dfdaa3a6dc45d8793669"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.5.2+git${SRCPV}"
PKGV = "0.5.2+git${GITPKGV}"

SRC_URI = "git://code.videolan.org/videolan/dav1d.git;protocol=https"

S = "${WORKDIR}/git"

inherit meson
