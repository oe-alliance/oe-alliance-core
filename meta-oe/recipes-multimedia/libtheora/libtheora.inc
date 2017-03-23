SECTION = "libs"
SUMMARY = "theora  is a high-quality video codec \
that is free of intellectual property restrictions"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=cf91718f59eb6a83d06dc7bcaf411132"
DEPENDS = "pkgconfig libogg"

INC_PR = "r3"

SRC_URI = "http://downloads.xiph.org/releases/theora/libtheora-${PV}.tar.bz2" 

inherit autotools pkgconfig

EXTRA_OECONF = "--disable-examples"

LEAD_SONAME = "libtheora.so.0"

SRC_URI[md5sum] = "292ab65cedd5021d6b7ddd117e07cd8e"
SRC_URI[sha256sum] = "b6ae1ee2fa3d42ac489287d3ec34c5885730b1296f0801ae577a35193d3affbc"
