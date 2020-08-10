require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

SRC_URI_remove = " file://0001-add-find-GLIB.patch \
           file://e2player.patch \
           file://0001-introduce-basic-GstPlayer.patch \
"

SRC_URI_append = " file://HiPlayer-for-kodi-18.patch \
           file://HiPlayer-defaultplayer.patch \
           file://HiPlayer-Subs-18.patch \
"

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN} += "maxytec-mali-${HICHIPSET}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=mali-cortexa15 \
    -DWITH_FFMPEG=stb \
"

