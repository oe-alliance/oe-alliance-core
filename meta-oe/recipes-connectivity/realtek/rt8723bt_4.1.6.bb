SUMMARY = "Realtek 8723A v1.0 Bluetooth"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://Makefile;md5=137c892e644370bd9573a3091781d8fa"

inherit module

MACHINE_KERNEL_PR:append = ".0"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance-drivers/rtl8723bt.git;protocol=https;branch=master;destsuffix=s"
inherit module

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

require kcflags.inc

S = "${UNPACKDIR}/s"
FILES:${PN} = "${nonarch_base_libdir}/firmware/"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bluetooth
    install -m 0644 ${S}/rtk_btusb.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bluetooth
    
    install -d ${D}${nonarch_base_libdir}/firmware
    install -d ${D}${nonarch_base_libdir}/firmware/rtk_bt
    install -m 0644 ${S}/rlt8723a_chip_b_cut_bt40_fw_asic_rom_patch-svn8909-0x002DF4E9-20130118-LINUX_USB_NOLPS.bin ${D}${nonarch_base_libdir}/firmware/rtk_bt/rtk8723a.bin
}

