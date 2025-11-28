SUMMARY = "Ralink RTL8192EU WPA2"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=cab676681a0415e7c5d9a42fd47514df"

inherit module

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/atvcaptain/rtl8192eu-5.11.2.3.git;protocol=https;branch=5.11.2.3"

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR}"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8192eu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}
