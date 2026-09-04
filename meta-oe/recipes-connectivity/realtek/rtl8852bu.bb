SUMMARY = "Realtek RTL8832BU and RTL8852BU"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b1918d7d89f091725a3188ff95f7c72b"

inherit module

PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = "${AUTOREV}"
# Use short destsuffix - the driver has ~370 object files whose absolute paths
# overflow ARG_MAX during kbuild linking on machines with long MACHINE names.
SRC_URI = "git://github.com/oe-alliance-drivers/rtl8852bu.git;protocol=https;branch=master;destsuffix=s"
# The default unpack directory "sources" costs six more characters on every
# object path, and this driver has several hundred of them.
UNPACKDIR = "${WORKDIR}/u"

S = "${UNPACKDIR}/s"

require kcflags.inc

# WPA3-SAE; the driver leaves this path disabled unless we ask for it
EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR} USER_EXTRA_CFLAGS=-DCONFIG_KERNEL_PATCH_EXTERNAL_AUTH"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8852bu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}
