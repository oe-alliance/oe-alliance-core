SUMMARY = "Firmware files for QCA6174"
LICENSE = "CLOSED"
require conf/license/license-close.inc

inherit allarch

SRC_URI = "http://source.mynonpublic.com/edision/firmware-qca6174.zip"

S = "${WORKDIR}"
PR = "r1"

PACKAGES = "${PN}"
FILES_${PN} += "${nonarch_base_libdir}/firmware"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 btfw32.tlv ${D}${nonarch_base_libdir}/firmware/btfw32.tlv

    install -d ${D}${nonarch_base_libdir}/firmware/ath10k/QCA6174/hw3.0
    install -m 0644 board.bin ${D}${nonarch_base_libdir}/firmware/ath10k/QCA6174/hw3.0/board.bin
    install -m 0644 firmware-4.bin ${D}${nonarch_base_libdir}/firmware/ath10k/QCA6174/hw3.0/firmware-4.bin

    install -d ${D}${nonarch_base_libdir}/firmware/wlan
    install -m 0644 bdwlan30.bin ${D}${nonarch_base_libdir}/firmware/bdwlan30.bin
    install -m 0644 otp30.bin ${D}${nonarch_base_libdir}/firmware/otp30.bin
    install -m 0644 qwlan30.bin ${D}${nonarch_base_libdir}/firmware/qwlan30.bin
    install -m 0644 utf30.bin ${D}${nonarch_base_libdir}/firmware/utf30.bin
    install -m 0644 wlan/cfg.dat ${D}${nonarch_base_libdir}/firmware/wlan/cfg.dat
    install -m 0644 wlan/qcom_cfg.ini ${D}${nonarch_base_libdir}/firmware/wlan/qcom_cfg.ini
}

SRC_URI[md5sum] = "f193e6b80752e37234d0a70d56c72ed6"
SRC_URI[sha256sum] = "b86d0160a7b84c3a1da251fd3b591d2828b66b8d050afd9d06327f2768f5ce4d"
