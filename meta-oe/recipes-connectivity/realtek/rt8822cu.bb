SUMMARY = "Realtek RTL8812CU and RTL8822CU"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://ifcfg-wlan0;md5=a84acae65af4b2d44d5035aa9f63cd85"

DEPENDS = "bc-native"

inherit module

PR = "r1"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance-drivers/rtl8822cu.git;protocol=https;branch=master;destsuffix=s"

UNPACKDIR = "${WORKDIR}/u"

S = "${UNPACKDIR}/s"
CXXFLAGS:append = "${@bb.utils.contains_any("SOC_FAMILY", "hisi3716mv430 hisi3798mv200 hisi3798mv300 hisi3716mv410 hisi3798mv310", " -DCONFIG_PLATFORM_HISILICON", "", d)}"

require kcflags.inc

# WPA3-SAE; the driver leaves this path disabled unless we ask for it
EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR} LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR} \
    USER_EXTRA_CFLAGS=-DCONFIG_KERNEL_PATCH_EXTERNAL_AUTH"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/88x2cu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}
