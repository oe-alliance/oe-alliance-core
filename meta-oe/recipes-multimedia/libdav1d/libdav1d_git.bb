SUMMARY = "dav1d is an AV1 decoder"
HOMEPAGE = "https://code.videolan.org/videolan/dav1d"
SECTION = "libs/multimedia"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=c8055cfe7548dfdaa3a6dc45d8793669"

inherit gitpkgv

SRCREV = "c719d4a4e13dc865909b220118d935ac698ac9ba"
PV = "0.9.1+git${SRCPV}"
PKGV = "0.9.1+git${GITPKGV}"

SRC_URI = "git://code.videolan.org/videolan/dav1d.git;protocol=https"

S = "${WORKDIR}/git"

inherit meson
