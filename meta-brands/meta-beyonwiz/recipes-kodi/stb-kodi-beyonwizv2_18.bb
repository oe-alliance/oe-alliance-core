require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCDATE = "20200805"
SRC_URI_remove = " file://0001-add-find-GLIB.patch \
           file://e2player.patch \
           file://0001-introduce-basic-GstPlayer.patch \
"
SRC_URI_append = " http://source.mynonpublic.com/kodi/hiplayer_kodi_188_${SRCDATE}.tar.gz \
                file://hiplayer.patch \
"

SRC_URI[md5sum] = "67d90c410b819db5bf0e6b91760954b3"
SRC_URI[sha256sum] = "1c86a4b032311d32d6fcc6f70a353b9b2ed5128ef5c02953b01e03bf6737a3da"

DEPENDS += "beyonwiz-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

RDEPENDS_${PN} += "beyonwiz-libs-${MACHINE}"
RDEPENDS_${PN} += "beyonwiz-opengl-${MACHINE}"

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
