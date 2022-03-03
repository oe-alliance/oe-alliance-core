SUMMARY = "DVD navigation multimeda library"
SECTION = "libs/multimedia"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
DEPENDS = "libdvdread"
PV = "6.0.0"
SRCREV = "${AUTOREV}"


inherit autotools pkgconfig git-project

SRC_URI = "git://github.com/xbmc/libdvdnav.git;protocol=https;branch=master"
