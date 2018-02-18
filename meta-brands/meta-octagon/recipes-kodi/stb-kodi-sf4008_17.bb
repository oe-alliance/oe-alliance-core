require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN}_append = "nextv-v3ddriver-${MACHINE} platform-util-${MACHINE} octagon-dvb-modules-kodi-${MACHINE}"

EXTRA_OECONF += " \
    --with-platform=nextv-cortexa15 \
    --with-ffmpeg=stb \
"
