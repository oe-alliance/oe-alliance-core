SUMMARY = "Firmware files for bcm40181a2/ap6210"
LICENSE = "CLOSED"
require conf/license/license-close.inc

inherit allarch


SRC_URI = "file://firmware-ap6210.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware/brcm"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/brcm
    install -m 0644 ap6210-nvram.txt ${D}${nonarch_base_libdir}/firmware/brcm/ap6210-nvram.txt
    install -m 0644 fw_bcm40181a2.bin ${D}${nonarch_base_libdir}/firmware/brcm/fw_bcm40181a2.bin
}