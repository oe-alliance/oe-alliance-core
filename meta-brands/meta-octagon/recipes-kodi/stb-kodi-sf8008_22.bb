require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

SRC_URI:append = "file://hiplayer-22.patch"

DEPENDS += "octagon-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

RDEPENDS:${PN} += "octagon-libs-${MACHINE}"
RDEPENDS:${PN} += "octagon-opengl-${SOC_FAMILY}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
    -DWITH_FFMPEG=stb \
"

