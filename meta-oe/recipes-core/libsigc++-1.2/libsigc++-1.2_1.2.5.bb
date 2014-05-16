SUMMARY = "A library for loose coupling of C++ method calls"
SECTION = "libs"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=d8045f3b8f929c1cb29a1e3fd737b499"
PR = "r2"

SRC_URI = "${SOURCEFORGE_MIRROR}/libsigc/libsigc++-${PV}.tar.gz \
           file://autofoo.patch \
           file://fix-install.patch \
           file://pkgconfig.patch \
           file://to_1.2.7.patch"
SRC_URI[md5sum] = "d0d1ffcae0eced97ef4f17ce0ba81352"
SRC_URI[sha256sum] = "dcd6d3ea9a2c185b5286f80eefe9ac1402036b8fa21cfc742442d99579bd2b3e"

S = "${WORKDIR}/libsigc++-${PV}"

inherit autotools pkgconfig

EXTRA_AUTORECONF = "--exclude=autoheader"

FILES_${PN}-dev += "${libdir}/sigc++-*/"
