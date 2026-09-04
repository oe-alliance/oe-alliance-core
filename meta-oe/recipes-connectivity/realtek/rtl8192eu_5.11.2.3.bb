SUMMARY = "Realtek RTL8192EU USB wireless driver"
HOMEPAGE = "http://www.realtek.com/"
SECTION = "kernel/modules"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=1a805122b9d7a745997090ae83a2a4de"

inherit module

PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance-drivers/rtl8192eu.git;protocol=https;branch=master;destsuffix=s"

S = "${UNPACKDIR}/s"

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR}"

require kcflags.inc

# WPA3-SAE, through KCFLAGS because this Makefile has no USER_EXTRA_CFLAGS hook
KCFLAGS:append = " -DCONFIG_KERNEL_PATCH_EXTERNAL_AUTH"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8192eu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}
