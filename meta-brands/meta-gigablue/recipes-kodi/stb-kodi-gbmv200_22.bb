require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

DEPENDS += "gigablue-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

RDEPENDS:${PN} += "gigablue-libs-${MACHINE}"
RDEPENDS:${PN} += "gigablue-opengl-${MACHINE}"


EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
    -DWITH_FFMPEG=stb \
"
