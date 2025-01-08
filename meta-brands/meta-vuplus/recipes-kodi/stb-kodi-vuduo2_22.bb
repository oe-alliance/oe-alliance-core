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
    https://source.mynonpublic.com/vuplus/release/kodi/xbmc-support_${MACHINE}_${GLPR}.tar.gz;name=xbmc-support \
"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=vuplus-mipsel \
    -DWITH_FFMPEG=stb \
"

SRC_URI[xbmc-support.md5sum] = "f3db678550f3654fcc8dfbb875678943"
SRC_URI[xbmc-support.sha256sum] = "758e75966c1ca513bbeb7eaef0d0359207232ba0e7f4f5e2574c146f5e09cab3"
