SUMMARY = "Driver for Realtek USB wireless devices"
HOMEPAGE = "http://www.realtek.com/"
SECTION = "kernel/modules"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://os_dep/linux/os_intfs.c;endline=19;md5=f8d10a6bd2fdfa240c0634a7c660c57f"

PR = "r10"

inherit module

SRC_URI = "http://source.mynonpublic.com/ini/rtl8188C_8192C_usb_linux_v3.4.4_4749.20140602.tar.gz \
    file://additional_productids.patch \
    file://add-3.8-support.patch \
    file://rt8192cu_procfs.patch \
    "

S = "${WORKDIR}/rtl8188C_8192C_usb_linux_v3.4.4_4749.20140602"

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8192cu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}

SRC_URI[md5sum] = "4de401958e154b60227fe3b7b4ad85d5"
SRC_URI[sha256sum] = "a2d10a56781bf81a720119bd5abc2ef53c0c3bc6b666b2bc846cc4b1b26bfae8"

