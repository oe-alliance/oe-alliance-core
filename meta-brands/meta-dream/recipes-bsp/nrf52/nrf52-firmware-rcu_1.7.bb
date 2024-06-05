LICENSE = "CLOSED"

CURRENT_FW = "update_full20210715-1.7.zip"
SRC_URI = " file://${CURRENT_FW}"

do_compile() {
    :
}
do_install() {
    install -d ${D}${datadir}/nrf52-firmware-rcu
    install -m 644 ${UNPACKDIR}/../manifest.json ${D}${datadir}/nrf52-firmware-rcu
    install -m 644 ${UNPACKDIR}/../sd_bl.dat ${D}${datadir}/nrf52-firmware-rcu
    install -m 644 ${UNPACKDIR}/../sd_bl.bin ${D}${datadir}/nrf52-firmware-rcu
    install -m 644 ${UNPACKDIR}/../nrf52832_xxaa.dat ${D}${datadir}/nrf52-firmware-rcu
    install -m 644 ${UNPACKDIR}/../nrf52832_xxaa.bin ${D}${datadir}/nrf52-firmware-rcu
}

FILES:${PN} = "/usr/share/nrf52-firmware-rcu"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"
