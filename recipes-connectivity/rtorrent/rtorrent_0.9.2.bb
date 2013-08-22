DESCRIPTION = "torrent client"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
HOMEPAGE = "http://libtorrent.rakshasa.no/"
MAINTAINER = "Jari Sundell <sundell.software@gmail.com>"
LICENSE = "GPLv2"
SECTION = "libs/network"
DEPENDS = "cppunit libtorrent curl ncurses gnutls libgpg-error"
PR = "r0"

SRC_URI = "http://libtorrent.rakshasa.no/downloads/${PN}-${PV}.tar.gz \
	file://autoconf-cross-fix.patch"

inherit autotools

SRC_URI[md5sum] = "72c3e9ab859bda7cc8aa96c0b508b09f"
SRC_URI[sha256sum] = "5c8f8c780bee376afce3c1cde2f5ecb928f40bac23b2b8171deed5cf3c888c3d"

