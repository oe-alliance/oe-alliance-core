require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCDATE = "20190628"

#SRC_URI_append = "http://source.mynonpublic.com/gigablue/mv200/hiplayer_gcc8_${SRCDATE}.tar.gz \
#    file://hiplayer.patch 
#"

SRC_URI[md5sum] = "a2d79eacd7b94262fcbcef2a1df84f77"
SRC_URI[sha256sum] = "a34f8511bba7e8b91d19ceab3da9cee57fdb3a76dd89e9166ca73d5aa41bece6"

DEPENDS += "gigablue-libs-${MACHINE}"
PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

RDEPENDS_${PN} += "gigablue-libs-${MACHINE}"
RDEPENDS_${PN} += "gigablue-opengl-${MACHINE}"

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
