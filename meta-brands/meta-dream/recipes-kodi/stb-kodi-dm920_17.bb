require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

EXTRA_OECONF += " \
    --with-platform=dreambox-cortexa15 \
    --with-ffmpeg=v3d \
"
