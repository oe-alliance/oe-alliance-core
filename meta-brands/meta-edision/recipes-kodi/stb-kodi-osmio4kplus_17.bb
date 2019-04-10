require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

SRC_URI_append = "file://0001-osmio4k-support.patch"

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

INSANE_SKIP_${PN} += "file-rdeps"

EXTRA_OECONF += " \
    --with-platform=edision-aarch64 \
    --with-ffmpeg=stb \
"
