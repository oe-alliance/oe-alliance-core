require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
DEPENDS_append = " odroid-libamadec-${MACHINE} odroid-libamcodec-${MACHINE} odroid-libamavutils-${MACHINE}"

EXTRA_OECONF += " \
    --with-platform=aml-aarch64 \
    --with-ffmpeg=stb\
"