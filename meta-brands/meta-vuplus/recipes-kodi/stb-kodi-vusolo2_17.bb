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

SRC_URI[xbmc-support.md5sum] = "e29a91b185133ec60a59e94a8229d2b4"
SRC_URI[xbmc-support.sha256sum] = "3c56b7ee890b3e21f378acd79db3752d721de0880b6d763bbd37fa942c2ae2b5"
