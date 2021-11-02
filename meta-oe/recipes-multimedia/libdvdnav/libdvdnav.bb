SUMMARY = "DVD navigation multimeda library"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
DEPENDS = "libdvdread"
PV = "6.0.0"
SRCREV = "${AUTOREV}"

CFLAGS_append_sh4 += "-std=gnu11"

inherit autotools pkgconfig git-project

SRC_URI = "git://github.com/xbmc/libdvdnav.git;protocol=https"
