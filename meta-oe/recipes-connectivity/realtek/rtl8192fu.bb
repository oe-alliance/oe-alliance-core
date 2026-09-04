SUMMARY = "Driver for Realtek USB wireless device 8192fu"
HOMEPAGE = "http://www.realtek.com/"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7fc9206801c1cb9a93c37d7dc0667d87"

DEPENDS = "bc-native"

SRC_URI = "git://github.com/oe-alliance-drivers/rtl8192fu.git;protocol=https;branch=master;destsuffix=s"

S = "${UNPACKDIR}/s"

SRCREV = "${AUTOREV}"

inherit module

PR = "r1"

# WPA3-SAE; the driver leaves this path disabled unless we ask for it.
EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR} KSRC=${STAGING_KERNEL_DIR} \
    USER_EXTRA_CFLAGS=-DCONFIG_KERNEL_PATCH_EXTERNAL_AUTH"

require kcflags.inc

do_install() {
        install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
        install -m 0644 ${S}/8192fu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}
