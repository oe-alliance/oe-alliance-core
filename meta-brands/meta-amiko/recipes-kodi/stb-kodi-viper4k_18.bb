require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCDATE = "20200325"
SRC_URI_remove = " file://0001-add-find-GLIB.patch \
           file://e2player.patch \
           file://0001-introduce-basic-GstPlayer.patch \
"
SRC_URI_append = " http://source.mynonpublic.com/kodi/hiplayer_kodi_186_${SRCDATE}.tar.gz \
                file://hiplayer.patch \
"

SRC_URI[md5sum] = "a38517ad4fb20dad037ae65c45f2e967"
SRC_URI[sha256sum] = "3fa986728edc463bfd9cf6738999b269ef1969401b0e51583b8ea5ea537ac7d3"

DEPENDS += "amiko-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

RDEPENDS_${PN} += "amiko-libs-${MACHINE}"
RDEPENDS_${PN} += "amiko-opengl-${MACHINE}"

do_configure_append() {
        install -d ${D}${libdir}
        install -d ${WORKDIR}/git/xbmc/linux/hisi/
        install -d ${WORKDIR}/git/xbmc/cores/hiplayer/
        install -m 0755 ${WORKDIR}/hiadp.a      ${WORKDIR}/git/xbmc/linux/hisi/
        install -m 0755 ${WORKDIR}/hiplayer.a   ${WORKDIR}/git/xbmc/cores/hiplayer/
}

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
    -DWITH_FFMPEG=stb \
"
