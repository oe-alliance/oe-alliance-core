SUMMARY = "DVD access multimeda library"
SECTION = "libs/multimedia"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=64e753fa7d1ca31632bc383da3b57c27"
PV = "6.0"
SRCREV = "${AUTOREV}"

inherit autotools pkgconfig git-project

SRC_URI = "git://github.com/xbmc/libdvdread.git;protocol=https;branch=master"
