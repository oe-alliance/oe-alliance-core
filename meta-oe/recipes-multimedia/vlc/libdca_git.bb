SUMMARY = "decoding library for DTS Coherent Acoustics streams"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

inherit autotools gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.0.7+git${SRCPV}"
PKGV = "0.0.7+git${GITPKGV}"

SRC_URI = "git://code.videolan.org/videolan/libdca;protocol=https \
        file://fix-libdts-link-path.patch"

S = "${WORKDIR}/git"
