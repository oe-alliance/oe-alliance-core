SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPL-2.0-or-later"
require conf/license/license-gplv2.inc

inherit kodi-addon

DEPENDS += "expat"

SRCREV = "74d1e7654eb19dac9203a6e4e64bf06d5a7250c4"

PV = "22.0.0+gitr"

KODIADDONBRANCH = "Piers"

SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https;branch=${KODIADDONBRANCH}"

KODIADDONNAME = "inputstream.rtmp"
