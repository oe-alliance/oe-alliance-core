SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPLv2+"
require conf/license/license-gplv2.inc

inherit kodi-addon

DEPENDS += "expat"

SRCREV = "${AUTOREV}"

PV = "3.1.1+gitr${SRCPV}"

KODIADDONBRANCH = "Matrix"

SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https;branch=${KODIADDONBRANCH} \
         "

S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.rtmp"
