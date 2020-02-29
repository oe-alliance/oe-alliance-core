SUMMARY = "kodi inputstream addon for several manifest types"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://src/main.cpp;beginline=1;endline=17;md5=5eac1e215251c8f88d799f80ed45d5c0"

inherit kodi-addon

DEPENDS += "expat"
RDEPENDS_${PN} += "ldd kernel-module-ext2"

SRCREV = "eb5224fbbc6d37c67e7a064b5cb10f4f7a1bc6b5"

PV = "2.3.11+gitr${SRCPV}"
SRC_URI = "git://github.com/peak3d/inputstream.adaptive.git;protocol=https \
          "

S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.adaptive"
