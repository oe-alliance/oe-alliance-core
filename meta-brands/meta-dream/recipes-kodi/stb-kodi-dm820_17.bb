require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

EXTRA_OECONF += " \
    --with-platform=dreambox-mipsel \
    --with-ffmpeg=v3d \
"
