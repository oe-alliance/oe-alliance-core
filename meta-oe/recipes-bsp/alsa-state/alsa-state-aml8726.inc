FILESEXTRAPATHS:prepend := "${THISDIR}/alsa-state/AML8726:"

SRC_URI:append = " file://alsactl.conf"

do_install:append() {
    install -d ${D}${sysconfdir}/
    install -m 0644 ${WORKDIR}/alsactl.conf ${D}${sysconfdir}/
}

FILES:${PN}:append = " ${sysconfdir}/ "
