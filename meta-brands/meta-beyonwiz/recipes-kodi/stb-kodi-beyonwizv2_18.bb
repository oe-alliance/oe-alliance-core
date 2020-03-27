require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCDATE = "20200324"
SRC_URI_remove = " file://0001-add-find-GLIB.patch \
           file://e2player.patch \
           file://0001-introduce-basic-GstPlayer.patch \
"
SRC_URI_append = " http://source.mynonpublic.com/kodi/hiplayer_kodi_185_${SRCDATE}.tar.gz \
                file://hiplayer.patch \
"

SRC_URI[md5sum] = "eb09dbc1393b705fc26fad3e9d36e19b"
SRC_URI[sha256sum] = "6290319003cf2f18a6ae9ce15bd84e0a78c0812d39d4cafdffff8abb38fd4bb8"

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
