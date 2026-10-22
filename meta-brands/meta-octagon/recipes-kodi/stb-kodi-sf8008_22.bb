require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

DEPENDS += "octagon-libs-${MACHINEBUILD}"
PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

RDEPENDS:${PN} += "octagon-libs-${MACHINEBUILD}"
RDEPENDS:${PN} += "octagon-opengl-${SOC_FAMILY}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
"

