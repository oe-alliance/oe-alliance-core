require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"
RDEPENDS:${PN} += "xtrend-v3ddriver-et10000"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=v3d-mipsel \
    -DWITH_FFMPEG=stb \
"