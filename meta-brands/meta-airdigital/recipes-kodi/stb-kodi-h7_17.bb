require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN} += "airdigital-v3ddriver-${MACHINE}"

EXTRA_OECONF += " \
    --with-platform=v3d-cortexa15 \
    --with-ffmpeg=stb\
"