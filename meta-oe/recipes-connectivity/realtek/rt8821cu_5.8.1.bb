SUMMARY = "Realtek RTL8811CU and RTL8821CU"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ab842b299d0a92fb908d6eb122cd6de9"

DEPENDS = "bc-native"

inherit module

PR = "r1"

SRCREV = "${AUTOREV}"
# Use short destsuffix - the driver has several hundred object files whose
# absolute paths overflow ARG_MAX during kbuild linking on machines with long
# MACHINE names.
SRC_URI = "git://github.com/oe-alliance-drivers/rtl8821cu.git;protocol=https;branch=master;destsuffix=s"
# The default unpack directory "sources" costs six more characters on every
# object path.
UNPACKDIR = "${WORKDIR}/u"

S = "${UNPACKDIR}/s"

require kcflags.inc

# WPA3-SAE; the driver leaves this path disabled unless we ask for it
EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR} LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR} \
    USER_EXTRA_CFLAGS=-DCONFIG_KERNEL_PATCH_EXTERNAL_AUTH"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8821cu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}
