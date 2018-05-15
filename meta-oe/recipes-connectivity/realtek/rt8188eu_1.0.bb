SUMMARY = "Ralink 8188EU v1.0"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://ifcfg-wlan0;md5=a84acae65af4b2d44d5035aa9f63cd85"

inherit module

PR = "r1"

MACHINE_KERNEL_PR_append = ".0"

SRC_URI = "http://source.mynonpublic.com/rpi-rtl8188eu-20092013.tar.gz"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/rpi-rtl8188eu/"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8188eu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}

SRC_URI[md5sum] = "c3b3ab27fbb5ae58830b83d2e4e09ba8"
SRC_URI[sha256sum] = "213eab7c5586c82d0dc9df1eb2b1cb020d1f8cd3723adb6d8d4b5996f337af33"

