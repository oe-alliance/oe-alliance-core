DESCRIPTION = "Linux Bluetooth Stack Userland v5 configurations"
require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "xc7362|xc7346|xc7439"

RDEPENDS_${PN} = "bluez5"

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/bluetooth/
    echo "[Policy]" > ${D}${sysconfdir}/bluetooth/main.conf
    echo "AutoEnable=true" >> ${D}${sysconfdir}/bluetooth/main.conf
}

do_install_append_xc7362() {
    echo "ttyS2 rtk_h5" > ${D}${sysconfdir}/bluetooth/uart.conf
}

do_install_append_xc7346() {
    echo "ttyS1 rtk_h5" > ${D}${sysconfdir}/bluetooth/uart.conf
}

do_install_append_xc7439() {
    echo "ttyS1 qca" > ${D}${sysconfdir}/bluetooth/uart.conf
}
