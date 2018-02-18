require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN} += "airdigital-mali-${MACHINE}"

EXTRA_OECONF += " \
    --with-platform=mali-cortexa15 \
    --with-ffmpeg=stb \
"