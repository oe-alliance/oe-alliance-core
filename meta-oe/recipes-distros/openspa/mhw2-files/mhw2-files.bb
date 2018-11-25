SUMMARY = "Config files to EPG MHW2"
MAINTAINER = "OpenSPA"
SECTION = "base"
LICENSE = "proprietary"

inherit allarch

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"

SRC_URI = " \
    file://mhw_Chann.epg \
    file://mhw_Equiv.epg \
    file://mhw_Log.epg \
    "

do_install() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/mhw_Chann.epg ${D}${sysconfdir}/mhw_Chann.epg
    install -m 0644 ${WORKDIR}/mhw_Equiv.epg ${D}${sysconfdir}/mhw_Equiv.epg
    install -m 0644 ${WORKDIR}/mhw_Log.epg ${D}${sysconfdir}/mhw_Log.epg
}

FILES_${PN} = "${sysconfdir}"
