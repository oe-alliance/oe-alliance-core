require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

SRC_URI:append = "file://hiplayer_22_opt.patch"

DEPENDS += "uclan-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

RDEPENDS:${PN} += "uclan-libs-${MACHINE}"
RDEPENDS:${PN} += "uclan-opengl-${SOC_FAMILY}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
    -DWITH_FFMPEG=stb \
"
