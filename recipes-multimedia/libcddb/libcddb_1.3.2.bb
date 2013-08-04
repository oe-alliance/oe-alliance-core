DESCRIPTION = "A library for accessing a CDDB server"
HOMEPAGE = "http://libcddb.sourceforge.net"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6e29c688d912da12b66b73e32b03d812"

PR = "r1"

DEPENDS = "libcdio virtual/gettext"

SRC_URI = "http://downloads.sourceforge.net/${PN}/${P}.tar.bz2"

SRC_URI[md5sum] = "8bb4a6f542197e8e9648ae597cd6bc8a"
SRC_URI[sha256sum] = "35ce0ee1741ea38def304ddfe84a958901413aa829698357f0bee5bb8f0a223b"

inherit autotools pkgconfig

