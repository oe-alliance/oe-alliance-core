SUMMARY = "Firmware files for AIC8800"
LICENSE = "CLOSED"
require conf/license/license-close.inc

inherit allarch

SRC_URI = "https://source.mynonpublic.com/gigablue/firmware-aic8800D80.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"
PR = "r0"

PACKAGES = "${PN}"

FILES:${PN} += "${nonarch_base_libdir}/firmware/aic8800D80"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/aic8800D80
    install -m 0644 aic_powerlimit_8800d80.txt ${D}${nonarch_base_libdir}/firmware/aic8800D80/aic_powerlimit_8800d80.txt
    install -m 0644 aic_userconfig_8800d80.txt ${D}${nonarch_base_libdir}/firmware/aic8800D80/aic_userconfig_8800d80.txt
    install -m 0644 calibmode_8800d80.bin ${D}${nonarch_base_libdir}/firmware/aic8800D80/calibmode_8800d80.bin
    install -m 0644 fmacfw_8800d80_u02.bin ${D}${nonarch_base_libdir}/firmware/aic8800D80/fmacfw_8800d80_u02.bin
    install -m 0644 fmacfw_8800d80_u02_ipc.bin ${D}${nonarch_base_libdir}/firmware/aic8800D80/fmacfw_8800d80_u02_ipc.bin
    install -m 0644 fw_adid_8800d80_u02.bin ${D}${nonarch_base_libdir}/firmware/aic8800D80/fw_adid_8800d80_u02.bin
    install -m 0644 fw_ble_scan_ad_filter.bin ${D}${nonarch_base_libdir}/firmware/aic8800D80/fw_ble_scan_ad_filter.bin
    install -m 0644 fw_patch_8800d80_u02.bin ${D}${nonarch_base_libdir}/firmware/aic8800D80/fw_patch_8800d80_u02.bin
    install -m 0644 fw_patch_table_8800d80_u02.bin ${D}${nonarch_base_libdir}/firmware/aic8800D80/fw_patch_table_8800d80_u02.bin
    install -m 0644 lmacfw_rf_8800d80_u02.bin ${D}${nonarch_base_libdir}/firmware/aic8800D80/lmacfw_rf_8800d80_u02.bin
}

SRC_URI[md5sum] = "d50920417e1d3abd8a111988be81a704"
SRC_URI[sha256sum] = "5af5962fedab7db6eb6fde7d02c83ed7edef398c7b875ff1c64dad1a039e5f38"
