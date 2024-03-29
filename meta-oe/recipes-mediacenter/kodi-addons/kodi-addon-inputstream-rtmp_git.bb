SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPL-2.0-or-later"
require conf/license/license-gplv2.inc

inherit kodi-addon

DEPENDS += "expat"

SRCREV = "${AUTOREV}"

PV = "2.0.9+gitr"

KODIADDONBRANCH = "Leia"

SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https;branch=${KODIADDONBRANCH} \
         "

S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.rtmp"
