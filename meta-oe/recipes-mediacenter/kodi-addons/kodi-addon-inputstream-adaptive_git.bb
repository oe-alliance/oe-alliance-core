SUMMARY = "kodi inputstream addon for several manifest types"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://src/main.cpp;beginline=1;endline=17;md5=5eac1e215251c8f88d799f80ed45d5c0"

inherit kodi-addon

DEPENDS += "expat"
RDEPENDS:${PN} += "ldd"
RRECOMMENDS:${PN} = "kernel-module-ext2"

SRCREV = "${AUTOREV}"

KODIADDONBRANCH = "Leia"

PV = "2.4.7+gitr"
SRC_URI = "git://github.com/xbmc/inputstream.adaptive.git;protocol=https;branch=${KODIADDONBRANCH} \
          "

S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.adaptive"
