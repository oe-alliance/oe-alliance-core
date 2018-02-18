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

SRC_URI[xbmc-support.md5sum] = "f3db678550f3654fcc8dfbb875678943"
SRC_URI[xbmc-support.sha256sum] = "758e75966c1ca513bbeb7eaef0d0359207232ba0e7f4f5e2574c146f5e09cab3"