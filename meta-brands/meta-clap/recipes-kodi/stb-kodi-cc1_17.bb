require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_remove = "file://e2player.patch \
           file://add-gstplayer-support.patch \
"

SRC_URI_append = "file://hiplayer.patch \
                 file://hiadp.a \
                 file://hiplayer.a \
"

DEPENDS += "clap-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

RDEPENDS_${PN} += "clap-libs-${MACHINE}"
RDEPENDS_${PN} += "clap-opengl-${MACHINE}"

do_configure_append() {
        install -d ${D}${libdir}
        install -m 0755 ${WORKDIR}/hiadp.a      ${WORKDIR}/git/xbmc/linux/hisi/
        install -m 0755 ${WORKDIR}/hiplayer.a   ${WORKDIR}/git/xbmc/cores/hiplayer/
}

EXTRA_OECONF += " \
    --with-platform=clap-cortexa15 \
    --with-ffmpeg=stb \
"
