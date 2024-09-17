require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

INSANE_SKIP:${PN} += "file-rdeps"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=edision-cortexa15 \
    -DWITH_FFMPEG=stb \
"

