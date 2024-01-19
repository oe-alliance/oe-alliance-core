require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

SRC_URI:append = " file://HiPlayer-for-kodi-20.patch \
           file://HiPlayer-defaultplayer-20.patch \
           file://HiPlayer-Subs-20.patch \
"

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"
RDEPENDS:${PN} += "abcom-mali-${HICHIPSET}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=mali-cortexa15 \
    -DWITH_FFMPEG=stb \
"

