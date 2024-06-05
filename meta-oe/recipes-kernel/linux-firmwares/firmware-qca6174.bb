SUMMARY = "Firmware files for QCA6174"
LICENSE = "CLOSED"
require conf/license/license-close.inc

inherit allarch

SRC_URI = "https://source.mynonpublic.com/edision/firmware-qca6174_v2.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"
PR = "r2"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

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

    install -d ${D}${nonarch_base_libdir}/firmware/qca
    install -m 0644 nvm_00440302.bin ${D}${nonarch_base_libdir}/firmware/qca/nvm_00440302.bin
    install -m 0644 rampatch_00440302.bin ${D}${nonarch_base_libdir}/firmware/qca/rampatch_00440302.bin
}

SRC_URI[md5sum] = "d0661e4962e32e7b3861da4169216e39"
SRC_URI[sha256sum] = "2f2274266aba216bc1b6f79fee4862d2df23c5fe5441a8eb52ed765a4f6b5a8b"
