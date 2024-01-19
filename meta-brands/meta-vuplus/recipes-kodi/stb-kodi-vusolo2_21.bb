GLPR = "20160331_r0"

require stb-kodi-vuplus.inc
require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
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

SRC_URI[xbmc-support.md5sum] = "e29a91b185133ec60a59e94a8229d2b4"
SRC_URI[xbmc-support.sha256sum] = "3c56b7ee890b3e21f378acd79db3752d721de0880b6d763bbd37fa942c2ae2b5"
