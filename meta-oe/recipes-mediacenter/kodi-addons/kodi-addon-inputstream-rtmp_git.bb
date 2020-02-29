SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://src/main.cpp;beginline=1;endline=17;md5=5eac1e215251c8f88d799f80ed45d5c0"

inherit kodi-addon

DEPENDS += "expat"

SRCREV = "${AUTOREV}"

PV = "2.3.11+gitr${SRCPV}"

SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https \
         "

S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.rtmp"
