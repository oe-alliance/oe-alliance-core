DESCRIPTION = "Linux Bluetooth Stack Userland v5 configurations"
require conf/license/license-gplv2.inc

DEPENDS = "glib-2.0 bluez5"

SRC_URI = "file://main.conf file://uart.conf"

S = "${WORKDIR}"

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/bluetooth/
    install -m 0644 ${S}/main.conf ${D}${sysconfdir}/bluetooth/main.conf
    install -m 0644 ${S}/uart.conf ${D}${sysconfdir}/bluetooth/uart.conf
}

do_install_append_osnino() {
    echo "rtk_hciattach ttyS2 rtk_h5" >> ${D}${sysconfdir}/bluetooth/uart.conf
}

do_install_append_osninoplus() {
    echo "rtk_hciattach ttyS2 rtk_h5" >> ${D}${sysconfdir}/bluetooth/uart.conf
}

do_install_append_osninopro() {
    echo "rtk_hciattach ttyS2 rtk_h5" >> ${D}${sysconfdir}/bluetooth/uart.conf
}

do_install_append_xc7362() {
    echo "hciattach ttyS2 rtk_h5" >> ${D}${sysconfdir}/bluetooth/uart.conf
}

do_install_append_xc7346() {
    echo "hciattach ttyS1 rtk_h5" >> ${D}${sysconfdir}/bluetooth/uart.conf
}
