require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN}_append = "nextv-v3ddriver-${MACHINE} platform-util-${MACHINE} octagon-dvb-modules-kodi-${MACHINE}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=nextv-cortexa15 \
    -DWITH_FFMPEG=stb \
"

INSANE_SKIP_${PN} += "file-rdeps"
