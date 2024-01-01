require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

SRC_URI:append = "file://hiplayer-21.patch"

DEPENDS += "dags-libs-${SOC_FAMILY}"
PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

RDEPENDS:${PN} += "dags-libs-${SOC_FAMILY}"
RDEPENDS:${PN} += "dags-opengl-${SOC_FAMILY}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
    -DWITH_FFMPEG=stb \
"
