require recipes-mediacenter/kodi/kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

EXTRA_OECONF += " \
    --with-platform=v3d-mipsel \
    --with-ffmpeg=v3d \
"