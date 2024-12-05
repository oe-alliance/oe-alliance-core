require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

SRC_URI:append = " file://HiPlayer-for-kodi-21.patch \
           file://HiPlayer-defaultplayer-21.patch \
           file://HiPlayer-Subs-21.patch \
"

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"
RDEPENDS:${PN} += "airdigital-mali-${HICHIPSET}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=mali-cortexa15 \
    -DWITH_FFMPEG=stb \
"

