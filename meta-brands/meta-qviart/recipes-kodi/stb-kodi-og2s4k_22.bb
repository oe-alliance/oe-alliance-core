require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

DEPENDS += "qviart-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

RDEPENDS:${PN} += "qviart-libs-${MACHINE}"
RDEPENDS:${PN} += "qviart-opengl-${SOC_FAMILY}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
"
