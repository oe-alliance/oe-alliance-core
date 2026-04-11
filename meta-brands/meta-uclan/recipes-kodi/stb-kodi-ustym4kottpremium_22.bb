require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

DEPENDS += "uclan-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

RDEPENDS:${PN} += "uclan-libs-${MACHINE}"
RDEPENDS:${PN} += "uclan-opengl-${SOC_FAMILY}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
"
