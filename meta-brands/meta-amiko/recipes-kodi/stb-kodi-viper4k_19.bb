require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCDATE = "20200730"

SRC_URI_append = " http://source.mynonpublic.com/kodi/hiplayer_kodi_19_${SRCDATE}.tar.gz \
                file://hiplayer-19.patch \
"

SRC_URI[md5sum] = "0fe757060f95a7a3a082444a0da7eb03"
SRC_URI[sha256sum] = "c4a43104bc449665f6b182af7f7098b223e17b09e341c96403a218f5232086eb"

DEPENDS += "amiko-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

RDEPENDS_${PN} += "amiko-libs-${MACHINE}"
RDEPENDS_${PN} += "amiko-opengl-${MACHINE}"

do_configure_append() {
        install -d ${D}${libdir}
        install -d ${WORKDIR}/git/xbmc/platform/linux/hisi/
        install -d ${WORKDIR}/git/xbmc/cores/hiplayer/
        install -m 0755 ${WORKDIR}/hiadp.a      ${WORKDIR}/git/xbmc/platform/linux/hisi/
        install -m 0755 ${WORKDIR}/hiplayer.a   ${WORKDIR}/git/xbmc/cores/hiplayer/
}

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
    -DWITH_FFMPEG=stb \
"
