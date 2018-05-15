SUMMARY = "Realtek 8723A v1.0 Bluetooth"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;md5=137c892e644370bd9573a3091781d8fa"

inherit module

PR = "r7"

MACHINE_KERNEL_PR_append = ".0"

SRC_URI = "http://source.mynonpublic.com/ini/8723AE_8723AU_Linux_BT_20140623.tar.gz"

inherit module

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"
S = "${WORKDIR}/8723AE_8723AU_Linux_BT_20140623"

FILES_${PN} = "${nonarch_base_libdir}/firmware/"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bluetooth
    install -m 0644 ${S}/rtk_btusb.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bluetooth
    
    install -d ${D}${nonarch_base_libdir}/firmware
    install -d ${D}${nonarch_base_libdir}/firmware/rtk_bt
    install -m 0644 ${S}/rlt8723a_chip_b_cut_bt40_fw_asic_rom_patch-svn8909-0x002DF4E9-20130118-LINUX_USB_NOLPS.bin ${D}${nonarch_base_libdir}/firmware/rtk_bt/rtk8723a.bin
}

SRC_URI[md5sum] = "729ccdc7e70b17af6d835ea79ffea551"
SRC_URI[sha256sum] = "5af8ee1bf54cd427c0629f15a3ae2d20d20255770a290479afa7423dc341cc7f"


