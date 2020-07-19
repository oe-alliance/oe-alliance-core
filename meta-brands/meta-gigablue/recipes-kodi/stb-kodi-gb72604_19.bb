GLPR = "20180904-r0"

require stb-kodi-gigablue.inc
require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN} += "gb-v3ddriver-${MACHINE}"
RDEPENDS_${PN} += "glibc-gconv-iso8859-1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append += " \
     http://source.mynonpublic.com/gigablue/v3ddriver/xbmc-support_${MACHINE}_${GLPR}.tar.gz;name=xbmc-support \
"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=GB-cortexa15 \
    -DWITH_FFMPEG=stb \
"

SRC_URI[xbmc-support.md5sum] = "c9e208f1adf4da2afa2f924be9895f81"
SRC_URI[xbmc-support.sha256sum] = "8496405b70c0ad61e1f3c36abcd126678bfe0c4ece2c1fb24e7e035c141e5d30"
