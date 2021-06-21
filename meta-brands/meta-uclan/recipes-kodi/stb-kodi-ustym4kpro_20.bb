require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

SRC_URI_append = "file://hiplayer-20.patch"

DEPENDS += "uclan-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

RDEPENDS_${PN} += "uclan-libs-${MACHINE}"
RDEPENDS_${PN} += "uclan-opengl-${MACHINE}"


EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
    -DWITH_FFMPEG=stb \
"