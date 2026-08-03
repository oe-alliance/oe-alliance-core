require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"
RDEPENDS:${PN}:append = " nextv-v3ddriver-${MACHINE} platform-util-${MACHINE} octagon-dvb-modules-kodi-${MACHINE}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=nextv-cortexa15 \
"

INSANE_SKIP:${PN} += "file-rdeps"
