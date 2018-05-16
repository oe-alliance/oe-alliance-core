require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
DEPENDS_append = " wetek-libamadec-${MACHINE} wetek-libamcodec-${MACHINE} wetek-libamavutils-${MACHINE}"

EXTRA_OECONF += " \
    --with-platform=aml-aarch64 \
    --with-ffmpeg=stb\
"