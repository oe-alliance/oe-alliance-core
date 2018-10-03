require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

SRC_URI_remove = "file://e2player.patch \
           file://add-gstplayer-support.patch \
"

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN} += "gfutures-mali-${MACHINE}"

EXTRA_OECONF += " \
    --with-platform=mali-cortexa15 \
    --with-ffmpeg=stb \
"
