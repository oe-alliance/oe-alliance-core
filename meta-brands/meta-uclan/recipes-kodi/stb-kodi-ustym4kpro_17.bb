require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCDATE = "20190320"

SRC_URI_remove = "file://e2player.patch \
           file://add-gstplayer-support.patch \
"

SRC_URI_append = "http://source.mynonpublic.com/uclan/hiplayer_gcc8_${SRCDATE}.tar.gz \
    file://hiplayer.patch \
"

SRC_URI[md5sum] = "9fc70dcf40e43cecd8af6af782a08e01"
SRC_URI[sha256sum] = "0c2b5eeefecdbaede5cb2b89c6e143287f4032650ef860907100e4cb21b97ee6"

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
