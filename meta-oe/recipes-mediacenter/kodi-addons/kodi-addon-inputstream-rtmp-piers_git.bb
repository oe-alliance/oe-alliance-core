SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPL-2.0-or-later"
require conf/license/license-gplv2.inc

inherit kodi-addon

DEPENDS += "expat"

SRCREV = "4cf0d41bf26f673fda458c20467fd66db6d1a54d"

PV = "22.0.0+gitr"

KODIADDONBRANCH = "Piers"

SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https;branch=${KODIADDONBRANCH}"

S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.rtmp"
