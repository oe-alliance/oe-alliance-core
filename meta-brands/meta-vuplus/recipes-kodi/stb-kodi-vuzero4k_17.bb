require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

DEPENDS += "kodiegl"
RDEPENDS_${PN} += "kodiegl"

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

EXTRA_OECONF += " \
    --with-platform=vuplus-cortexa15 \
    --with-ffmpeg=stb \
"
