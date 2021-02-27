require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCDATE = "20200807"

SRC_URI_append = " http://source.mynonpublic.com/kodi/hiplayer_kodi_19_${SRCDATE}.tar.gz \
                file://hiplayer-19.patch \
"

SRC_URI[md5sum] = "b6418190cc17e9e9f562cf6a72680730"
SRC_URI[sha256sum] = "846d8cd32c255e761157d7ff19de6089ab3e8b22ea087ceeda82699be00a476a"

DEPENDS += "dags-libs-${SOC_FAMILY}"
PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

RDEPENDS_${PN} += "dags-libs-${SOC_FAMILY}"
RDEPENDS_${PN} += "dags-opengl-${SOC_FAMILY}"

do_configure_append() {
        install -d ${D}${libdir}
        install -d ${WORKDIR}/git/xbmc/linux/hisi/
        install -d ${WORKDIR}/git/xbmc/cores/hiplayer/
        install -m 0755 ${WORKDIR}/hiadp.a      ${WORKDIR}/git/xbmc/linux/hisi/
        install -m 0755 ${WORKDIR}/hiplayer.a   ${WORKDIR}/git/xbmc/cores/hiplayer/
}

do_package_qa() {
}

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=clap-cortexa15 \
    -DWITH_FFMPEG=stb \
"
INSANE_SKIP_${PN} = "already-stripped dev-so"
