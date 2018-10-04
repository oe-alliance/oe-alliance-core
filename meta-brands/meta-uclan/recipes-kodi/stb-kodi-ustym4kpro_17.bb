require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCDATE = "20180808"

SRC_URI_remove = "file://e2player.patch \
           file://add-gstplayer-support.patch \
"

SRC_URI_append = "http://source.mynonpublic.com/uclan/hiplayer_gcc8_${SRCDATE}.tar.gz \
    file://hiplayer.patch \
"

SRC_URI[md5sum] = "712d859ca8bc93c314ab174cc9247475"
SRC_URI[sha256sum] = "279850b56a96c17bfe00949c01fb060c7a4766b8257285386a6192d193694cf3"

DEPENDS += "uclan-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

RDEPENDS_${PN} += "uclan-libs-${MACHINE}"
RDEPENDS_${PN} += "uclan-opengl-${MACHINE}"

do_configure_append() {
        install -d ${D}${libdir}
        install -m 0755 ${WORKDIR}/hiadp.a      ${WORKDIR}/git/xbmc/linux/hisi/
        install -m 0755 ${WORKDIR}/hiplayer.a   ${WORKDIR}/git/xbmc/cores/hiplayer/
}

EXTRA_OECONF += " \
    --with-platform=clap-cortexa15 \
    --with-ffmpeg=stb \
"
