SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPL-2.0-or-later"
require conf/license/license-gplv2.inc

inherit kodi-addon

DEPENDS += "expat openssl rtmpdump zlib"

# Track the Kodi 22 Piers branch.  Its add-on version remains 22.1.2.
SRCREV = "1b995db747b3f8671e04377e069a7b9d9260944d"

PV = "22.1.2+gitr"

KODIADDONBRANCH = "Piers"

SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https;branch=${KODIADDONBRANCH}"

KODIADDONNAME = "inputstream.rtmp"
