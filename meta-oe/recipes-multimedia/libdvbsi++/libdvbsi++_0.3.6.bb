SUMMARY = "C++ parsing library for Service Information (SI) in DVB systems"
AUTHOR = "Andreas Oberritter"
SECTION = "libs/multimedia"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343"
PR = "r1"

SRC_URI = "http://www.saftware.de/${PN}/${P}.tar.bz2"
SRC_URI[md5sum] = "4e9fb95c3ab8bb31ff051ed1aa98c8c5"
SRC_URI[sha256sum] = "7f9a8fc7bed9372784ecb101fc45042dcb36dcd4949c57aa524365366f71ebf2"

inherit autotools pkgconfig
