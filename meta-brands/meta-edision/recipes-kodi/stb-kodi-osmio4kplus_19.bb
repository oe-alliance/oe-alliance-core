require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

INSANE_SKIP_${PN} += "file-rdeps"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=edision-cortexa15 \
    -DWITH_FFMPEG=stb \
"

SRC_URI_append = " file://0001-Add-initial-support-for-V4L2-mem2mem-decoder.patch"
