LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = " file://${CURRENT_FW}"

RPROVIDES:${PN} += " nrf52-firmware"

do_compile() {
    :
}
do_install() {
    install -d ${D}/usr/share/nrf52-firmware
    install -m 644 ${UNPACKDIR}/../${CURRENT_FW} ${D}/usr/share/nrf52-firmware
}

FILES:${PN} = "/usr/share/nrf52-firmware"
