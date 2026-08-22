SUMMARY = "libdvdnav wrapper for enigma2 based stbs"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94d55d512a9ba36caa9b7df079bae19f"

DEPENDS = "libdvdnav"

inherit gitpkgv

SRCREV = "9536f50ffef25ac33be60bb7dfed2a2db38bb1c8"
PV = "1.1+git"
PKGV = "1.1+git${GITPKGV}"

SRC_URI = "git://github.com/oe-mirrors/libdreamdvd.git;protocol=https;branch=master"

inherit autotools pkgconfig

CFLAGS += " -std=gnu11"
