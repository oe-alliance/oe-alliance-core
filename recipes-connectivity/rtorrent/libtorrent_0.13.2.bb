DESCRIPTION = "torrent library"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"
HOMEPAGE = "http://libtorrent.rakshasa.no/"
MAINTAINER = "Jari Sundell <sundell.software@gmail.com>"
LICENSE = "GPLv2"
SECTION = "libs/network"
DEPENDS = "cppunit libsigc++-2.0 openssl"
PR = "r0"

SRC_URI = "http://libtorrent.rakshasa.no/downloads/${PN}-${PV}.tar.gz" 

EXTRA_OECONF = " --enable-aligned --without-kqueue"

inherit autotools pkgconfig

SRC_URI[md5sum] = "96c0b81501357df402ab592f59ecaeab"
SRC_URI[sha256sum] = "ed2f2dea16c29cac63fa2724f6658786d955f975861fa6811bcf1597ff8a5e4f"

