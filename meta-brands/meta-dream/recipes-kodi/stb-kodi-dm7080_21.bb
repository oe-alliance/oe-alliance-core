require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=dreambox-mipsel \
    -DWITH_FFMPEG=stb \
"
