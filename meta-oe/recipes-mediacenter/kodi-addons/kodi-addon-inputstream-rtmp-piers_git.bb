SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPL-2.0-or-later"
require conf/license/license-gplv2.inc

inherit kodi-addon

DEPENDS += "expat openssl rtmpdump zlib"

# Published Piers tag used by CoreELEC 22/LibreELEC.  Prefer the released tag
# over unreleased commits on the moving Piers branch.
SRCREV = "228dbc36ae2666f6e43f023fa083bd3e612af7a2"

PV = "22.1.2+gitr"

KODIADDONBRANCH = "Piers"

SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https;branch=${KODIADDONBRANCH}"

KODIADDONNAME = "inputstream.rtmp"
