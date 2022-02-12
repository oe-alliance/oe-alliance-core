require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_remove = " file://0001-add-find-GLIB.patch \
           file://e2player.patch \
           file://0001-introduce-basic-GstPlayer.patch \
"

SRC_URI_append = " file://hiplayer_70.patch"

DEPENDS += "qviart-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

RDEPENDS_${PN} += "qviart-libs-${MACHINE}"
RDEPENDS_${PN} += "qviart-opengl-${SOC_FAMILY}"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
    -DWITH_FFMPEG=stb \
"

