require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

DEPENDS += "amiko-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

RDEPENDS:${PN} += "amiko-libs-${MACHINE}"
RDEPENDS:${PN} += "amiko-opengl-${MACHINE}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
"
