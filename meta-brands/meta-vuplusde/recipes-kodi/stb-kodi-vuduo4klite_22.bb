require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"
RDEPENDS:${PN} += "vuplus-v3ddriver-${MACHINE}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=GB-cortexa15 \
"
