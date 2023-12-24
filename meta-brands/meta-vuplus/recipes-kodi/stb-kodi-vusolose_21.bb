GLPR = "20160331_r0"

require stb-kodi-vuplus.inc
require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

INSANE_SKIP:${PN} += "file-rdeps ldflags"

SRC_URI:append = " \
    http://code.vuplus.com/download/release/kodi/xbmc-support_${MACHINE}_${GLPR}.tar.gz;name=xbmc-support \
"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=vuplus-mipsel \
    -DWITH_FFMPEG=stb \
"

SRC_URI[xbmc-support.md5sum] = "831014212eed47e36ec084f2e803e2d8"
SRC_URI[xbmc-support.sha256sum] = "97bfc26a316bcba4b897f81f31179e8861cc123a0b4d8589a2290f3cd7268c1d"
