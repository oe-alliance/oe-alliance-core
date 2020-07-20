SUMMARY = "Open implementation of the AACS specification"
SECTION = "libs/multimedia"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4b54a1fd55a448865a0b32d41598759d"

DEPENDS = "libgcrypt libgpg-error bison-native"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.10.0+git${SRCPV}"
PKGV = "0.10.0+git${GITPKGV}"

SRC_URI = "git://code.videolan.org/videolan/${BPN}.git;protocol=https \
        file://libgcrypt-gpg-error-use-pkgconfig.patch"

SRC_URI_append_sh4 = " file://add-missing-includes-for-gcc-4-9-4.patch"

S = "${WORKDIR}/git"

inherit autotools-brokensep lib_package pkgconfig
