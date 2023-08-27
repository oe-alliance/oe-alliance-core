SUMMARY = "Query CD-Text or information from CDDB"
AUTHOR = "Andreas Frisch <andreas.frisch@multimedia-labs.de>"
SECTION = "console"
LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
DEPENDS = "libcddb libcdio"

SRC_URI = "https://source.mynonpublic.com/dreambox/${P}.tar.bz2 \
           file://libcdio090.patch \
"

SRC_URI[md5sum] = "8fa7eeae52093bb90c1453ca4b8a29c9"
SRC_URI[sha256sum] = "62498d3db29c5de2e9e9119b315d2c8d7558c5a45264f26aabcf1f989437af33"

inherit autotools pkgconfig
