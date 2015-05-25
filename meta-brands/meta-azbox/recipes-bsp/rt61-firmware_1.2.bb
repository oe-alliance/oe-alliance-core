SUMMARY = "Firmware for rt61 based USB wifi adaptors"
LICENSE = "CLOSED"
PROVIDES = "rt61-firmware"


SRC_URI = "http://www.ralinktech.com.tw/data/RT61_Firmware_V${PV}.zip"

S = "${WORKDIR}/RT61_Firmware_V${PV}"

do_install() {
    install -d ${D}/${base_libdir}/firmware
    install -m 0644 *.bin ${D}/${base_libdir}/firmware/
}

FILES_${PN} = "${base_libdir}/firmware/"
PACKAGE_ARCH = "all"

SRC_URI[md5sum] = "d4c690c93b470bc9a681297c2adc6281"
SRC_URI[sha256sum] = "481f113c505ed186049287bd8e9ad8fcb8dbbb32f3bb718f04e4dc148b63c8e4"
