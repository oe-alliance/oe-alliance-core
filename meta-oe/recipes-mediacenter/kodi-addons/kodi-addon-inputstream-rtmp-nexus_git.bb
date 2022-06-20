SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPL-2.0-or-later"
require conf/license/license-gplv2.inc

inherit kodi-addon

DEPENDS += "expat"

SRCREV = "d3be647f7db255b70a3b72ad5ad20c2c9176945d"

PV = "20.2.0+gitr${SRCPV}"

KODIADDONBRANCH = "Nexus"

SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https;branch=${KODIADDONBRANCH}"

S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.rtmp"
