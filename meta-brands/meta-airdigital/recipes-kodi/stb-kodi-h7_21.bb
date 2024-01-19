require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"
RDEPENDS:${PN} += "airdigital-v3ddriver-${MACHINE}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=v3d-cortexa15 \
    -DWITH_FFMPEG=stb \
"
