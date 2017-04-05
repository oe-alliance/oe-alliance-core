GLPR = "20160331_r0"

require stb-kodi-vuplus.inc
require recipes-mediacenter/kodi/kodi_${PV}.bb

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

SRC_URI[xbmc-support.md5sum] = "1ae0c982f8db9625a7f831874f6f0605"
SRC_URI[xbmc-support.sha256sum] = "1be88ba6d4cbc0a0b1f10c83995132aa9bd5a088cf5b7080e1b0ef90170d96f7"
