SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPLv2+"
require conf/license/license-gplv2.inc

inherit kodi-addon

DEPENDS += "expat"

SRCREV = "${AUTOREV}"

PV = "2.3.11+gitr${SRCPV}"

SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https \
         "

S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.rtmp"
