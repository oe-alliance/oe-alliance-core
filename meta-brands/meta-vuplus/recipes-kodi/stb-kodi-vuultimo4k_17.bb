GLPR = "20161206_r0"

require stb-kodi-vuplus.inc
require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

INSANE_SKIP_${PN} += "file-rdeps ldflags"

SRC_URI_append += " \
    http://archive.vuplus.com/download/build_support/kodi/xbmc-support_${MACHINE}_${GLPR}.tar.gz;name=xbmc-support \
"

EXTRA_OECONF += " \
    --with-platform=vuplus-cortexa15 \
    --with-ffmpeg=v3d \
"

SRC_URI[xbmc-support.md5sum] = "765b8c18ca1e452cbb4b387a01cdefa8"
SRC_URI[xbmc-support.sha256sum] = "e4e469dd6cd21fa9d5fd59a662aa526169ace6ec59854ad0ee416baa66af45ae"