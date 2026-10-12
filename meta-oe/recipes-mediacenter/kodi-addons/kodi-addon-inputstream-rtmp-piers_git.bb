SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPL-2.0-or-later"
require conf/license/license-gplv2.inc

inherit kodi-addon

DEPENDS += "expat openssl rtmpdump zlib"

# Track the Kodi 22 Piers branch.
SRCREV = "de59f7862fe677b91a6453f522a5292e9836d0f6"

PV = "22.1.3+gitr"

KODIADDONBRANCH = "Piers"

SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https;branch=${KODIADDONBRANCH}"

KODIADDONNAME = "inputstream.rtmp"
