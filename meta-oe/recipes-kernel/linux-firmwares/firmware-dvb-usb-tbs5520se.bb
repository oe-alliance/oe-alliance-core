SUMMARY = "Firmware for TBS 5520SE USB DVB tuner"
DESCRIPTION = "Firmware files required by the TBS 5520SE multi-standard \
USB DVB device: the USB bridge firmware (dvb-usb-id5520se.fw) and the \
Si2183 demodulator firmware (dvb-demod-si2183-b60-01.fw)."
LICENSE = "CLOSED"

SRC_URI = " \
    file://dvb-usb-id5520se.fw \
    file://dvb-demod-si2183-b60-01.fw \
"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${WORKDIR}/dvb-usb-id5520se.fw ${D}${nonarch_base_libdir}/firmware/
    install -m 0644 ${WORKDIR}/dvb-demod-si2183-b60-01.fw ${D}${nonarch_base_libdir}/firmware/
}
