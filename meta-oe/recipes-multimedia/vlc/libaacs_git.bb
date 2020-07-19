SUMMARY = "Open implementation of the AACS specification"
SECTION = "libs/multimedia"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4b54a1fd55a448865a0b32d41598759d"

DEPENDS = "libgcrypt libgpg-error bison-native"

inherit gitpkgv

SRCREV = "${AUTOREV}"
SRCREV_sh4 = "d21f488eb8c8c23ed693f4551e1428e4622ec25b"

PV = "0.9.0+git${SRCPV}"
PKGV = "0.9.0+git${GITPKGV}"

SRC_URI = "git://code.videolan.org/videolan/${BPN}.git;protocol=https \
        file://libgcrypt-gpg-error-use-pkgconfig.patch"

S = "${WORKDIR}/git"

inherit autotools-brokensep lib_package pkgconfig
