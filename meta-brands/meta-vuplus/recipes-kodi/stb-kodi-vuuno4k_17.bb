GLPR = "20161206_r1"

require stb-kodi-vuplus.inc
require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append += " \
    http://archive.vuplus.com/download/build_support/kodi/xbmc-support_${MACHINE}_${GLPR}.tar.gz;name=xbmc-support \
"

EXTRA_OECONF += " \
    --with-platform=vuplus-cortexa15 \
    --with-ffmpeg=v3d \
"

SRC_URI[xbmc-support.md5sum] = "b0f62cb425b0f5bc5dc6ebcbe76f0890"
SRC_URI[xbmc-support.sha256sum] = "b2b91ec85cc7edfef86218906a8743868e372fb5f2736fa1ce066335a23c6bb1"