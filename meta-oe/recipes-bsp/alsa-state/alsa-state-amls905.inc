FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/AMLS905:"

SRC_URI_append = " file://alsactl.conf"

do_install_append() {
    install -d ${D}${sysconfdir}/
    install -m 0644 ${WORKDIR}/alsactl.conf ${D}${sysconfdir}/
}

FILES_${PN}_append = " ${sysconfdir}/ "
