DESCRIPTION = "Linux Bluetooth Stack Userland v5 configurations"
require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "osnino|osninoplus|osninopro|osmio4k"

RDEPENDS_${PN} = "bluez5"

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/bluetooth/
    echo "[Policy]" > ${D}${sysconfdir}/bluetooth/main.conf
    echo "AutoEnable=true" >> ${D}${sysconfdir}/bluetooth/main.conf
}

do_install_append_osnino() {
    echo "ttyS2 rtk_h5" > ${D}${sysconfdir}/bluetooth/uart.conf
}

do_install_append_osninoplus() {
    echo "ttyS2 rtk_h5" > ${D}${sysconfdir}/bluetooth/uart.conf
}

do_install_append_osninopro() {
    echo "ttyS2 rtk_h5" > ${D}${sysconfdir}/bluetooth/uart.conf
}
