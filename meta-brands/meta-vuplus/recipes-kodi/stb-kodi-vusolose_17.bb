GLPR = "20160331_r0"

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
    --with-platform=vuplus-mipsel \
    --with-ffmpeg=stb \
"

SRC_URI[xbmc-support.md5sum] = "831014212eed47e36ec084f2e803e2d8"
SRC_URI[xbmc-support.sha256sum] = "97bfc26a316bcba4b897f81f31179e8861cc123a0b4d8589a2290f3cd7268c1d"
