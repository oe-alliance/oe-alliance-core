require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCDATE = "20180622"

SRC_URI_remove = "file://e2player.patch \
           file://add-gstplayer-support.patch \
"

SRC_URI_append = "http://source.mynonpublic.com/clap/hiplayer_${SRCDATE}.tar.gz \
    file://hiplayer.patch \
"

SRC_URI[md5sum] = "8794244be0f4772e9ab88bf1f174d5ea"
SRC_URI[sha256sum] = "6b558d522d45a6fa5cd30bcb43ee7567ff628f223a30ac2023df86efa6f873b3"

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
