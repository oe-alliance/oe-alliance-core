require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"
RDEPENDS:${PN} += "gb-v3ddriver-${MACHINE_DRIVER}"
RDEPENDS:${PN} += "glibc-gconv-iso8859-1"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=GB-cortexa15 \
"
