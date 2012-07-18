SECTION = "libs"
DESCRIPTION = "theora  is a high-quality video codec \
that is free of intellectual property restrictions"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "files://LICENSE;md5=82ccf023e3ce1aa18043edc61f948e2c"
DEPENDS = "pkgconfig libogg"

INC_PR = "r3"

SRC_URI = "http://downloads.xiph.org/releases/theora/libtheora-${PV}.tar.bz2" 

inherit autotools

EXTRA_OECONF = "--disable-examples"

LEAD_SONAME = "libtheora.so.0"

