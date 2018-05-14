SUMMARY = "Tool to rip DVDs from the command line"
SECTION = "console/multimedia"
PRIORITY = "optional"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "libdvdread virtual/gettext"
PR = "r2"

SRC_URI = "http://sourceforge.mirrorservice.org/d/dv/dvdbackup/dvdbackup/${BP}/${BP}.tar.xz"
SRC_URI += "file://autoconf-warnings-are-not-errors.patch"

SRC_URI[md5sum] = "28f273b2f27a3afea3a3c965ddbede86"
SRC_URI[sha256sum] = "ef8c56fbb82b15b7eef00d2d3118c8253f9770009ed7bb2a5d4849acf88183e6"

inherit autotools
