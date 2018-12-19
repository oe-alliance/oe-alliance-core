require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN} += "dinobot-opengl-${MACHINE}"

EXTRA_OECONF += " \
    --with-platform=mali-cortexa15 \
    --with-ffmpeg=stb \
"
