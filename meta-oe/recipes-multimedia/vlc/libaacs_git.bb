SUMMARY = "Open implementation of the AACS specification"
SECTION = "libs/multimedia"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=4b54a1fd55a448865a0b32d41598759d"

DEPENDS = "libgcrypt libgpg-error bison-native"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.10.0+git"
PKGV = "0.10.0+git${GITPKGV}"

SRC_URI = "git://code.videolan.org/videolan/${BPN}.git;protocol=https;branch=master \
        file://libgcrypt-gpg-error-use-pkgconfig.patch"

S = "${WORKDIR}/git"

inherit autotools-brokensep lib_package pkgconfig
