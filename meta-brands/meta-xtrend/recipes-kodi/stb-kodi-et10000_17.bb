require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN} += "xtrend-v3ddriver-et10000"

EXTRA_OECONF += " \
    --with-platform=v3d-mipsel \
    --with-ffmpeg=stb \
"