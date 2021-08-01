FILESEXTRAPATHS:prepend := "${THISDIR}/alsa-state/AMLS905:"

SRC_URI:append = " file://alsactl.conf"

do_install:append() {
    install -d ${D}${sysconfdir}/
    install -m 0644 ${WORKDIR}/alsactl.conf ${D}${sysconfdir}/
}

FILES:${PN}:append = " ${sysconfdir}/ "
