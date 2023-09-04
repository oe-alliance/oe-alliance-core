SUMMARY = "decoding library for DTS Coherent Acoustics streams"
SECTION = "libs/multimedia"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.0.7+git"
PKGV = "0.0.7+git${GITPKGV}"

SRC_URI = "git://code.videolan.org/videolan/libdca;protocol=https;branch=master \
        file://fix-libdts-link-path.patch"

S = "${WORKDIR}/git"

inherit autotools
