SUMMARY = "Firmware files for QCA6174"
LICENSE = "CLOSED"
require conf/license/license-close.inc

inherit allarch

SRC_URI = "http://source.mynonpublic.com/edision/firmware-qca6174.zip"

S = "${WORKDIR}"

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

SRC_URI[md5sum] = "47997f7990c208077993c77c8685a4fd"
SRC_URI[sha256sum] = "8462eb74c766573969e30febaa505307bf9fc2308647d0bbb55bd18ab728f8f1"
