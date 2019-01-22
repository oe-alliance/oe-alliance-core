require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

SRC_URI_remove = "file://e2player.patch \
           file://add-gstplayer-support.patch \
"

SRC_URI_append = " file://0001-add-MALI_HIPLAYER.patch \
           file://HiPlayer.patch \
           file://HiPlayer-Subs.patch \
           file://playercorefactory-register-extention-for-hiplayer.patch \
"

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN} += "airdigital-mali-${HICHIPSET}"

EXTRA_OECONF += " \
    --with-platform=mali-cortexa15 \
    --with-ffmpeg=stb \
"
