require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN} += "ceryon-v3ddriver-${MACHINE}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=v3d-cortexa15 \
    -DWITH_FFMPEG=stb \
"
