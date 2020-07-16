require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

#SRCDATE = "20200415"
#SRC_URI_remove = " file://0001-add-find-GLIB.patch \
#           file://e2player.patch \
#           file://0001-introduce-basic-GstPlayer.patch \
#"
#SRC_URI_append = " http://source.mynonpublic.com/kodi/hiplayer_kodi_186_${SRCDATE}.tar.gz \
#                file://hiplayer-19.patch \
#"

#SRC_URI[md5sum] = "0a041ed3501bc5ff7f6c8cb67422e5be"
#SRC_URI[sha256sum] = "152d0b2d21a909fbe1c4ab23c95973460c6c2bfa687dcb45313c5b95580980c0"

DEPENDS += "octagon-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

RDEPENDS_${PN} += "octagon-libs-${MACHINE}"
RDEPENDS_${PN} += "octagon-opengl-${SOC_FAMILY}"

#do_configure_append() {
#        install -d ${D}${libdir}
#        install -d ${WORKDIR}/git/xbmc/linux/hisi/
#        install -d ${WORKDIR}/git/xbmc/cores/hiplayer/
#        install -m 0755 ${WORKDIR}/hiadp.a      ${WORKDIR}/git/xbmc/linux/hisi/
#        install -m 0755 ${WORKDIR}/hiplayer.a   ${WORKDIR}/git/xbmc/cores/hiplayer/
#}

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
    -DWITH_FFMPEG=stb \
"

