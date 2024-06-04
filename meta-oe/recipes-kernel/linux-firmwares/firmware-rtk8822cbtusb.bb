LICENSE = "CLOSED"
require conf/license/license-close.inc
SRC_URI = "file://rtk8822cbtusb.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

PACKAGES = "${PN}"


SUMMARY = "Firmware for rtk8822cbtusb"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/
    install -m 0644 ${UNPACKDIR}/rtl8822cu_config ${D}${nonarch_base_libdir}/firmware/
    install -m 0644 ${UNPACKDIR}/rtl8822cu_fw ${D}${nonarch_base_libdir}/firmware/
}

FILES:${PN} += "${nonarch_base_libdir}/firmware"
