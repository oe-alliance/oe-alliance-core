require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=nextv-cortexa15 \
    -DWITH_FFMPEG=stb \
"

INSANE_SKIP:${PN} += "file-rdeps"
