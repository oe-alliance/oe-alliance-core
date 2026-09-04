SUMMARY = "Driver for Realtek 8723BS wireless/bluetooth devices"
HOMEPAGE = "http://www.realtek.com/"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
require conf/license/license-gplv2.inc

PR = "r3"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/oe-alliance-drivers/rtl8723bs.git;protocol=https;branch=master;destsuffix=s"
inherit module

S = "${UNPACKDIR}/s"

require kcflags.inc

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/staging/rtl8723bs
    install -m 0644 ${S}/r8723bs.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/staging/rtl8723bs
}

python do_package:prepend() {
    d.appendVar('PKGV', '-')
    d.appendVar('PKGV', d.getVar("KERNEL_VERSION", True).split("-")[0])
}
