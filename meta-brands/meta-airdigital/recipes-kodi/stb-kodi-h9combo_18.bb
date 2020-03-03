require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

#SRC_URI_append = " file://0001-add-MALI_HIPLAYER.patch \
#           file://HiPlayer.patch \
#           file://HiPlayer-Subs.patch \
#"

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN} += "airdigital-mali-${HICHIPSET}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=mali-cortexa15 \
    -DWITH_FFMPEG=stb \
"

