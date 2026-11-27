SUMMARY = "Bluetooth HID keymap for the GTMedia GTX Combo remote"
LICENSE = "GPL-2.0-only AND MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"
inherit module
PR = "r0.1"
COMPATIBLE_MACHINE = "^gtxcombo$"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://81-gtx-bt-remote.rules file://Makefile file://hid-gtmedia-rcu.c"
S = "${UNPACKDIR}"

KERNEL_MODULE_AUTOLOAD += "hid-gtmedia-rcu"
RDEPENDS:${PN} += "kernel-module-hid-gtmedia-rcu"
FILES:${PN} += "${sysconfdir}/udev/rules.d/81-gtx-bt-remote.rules ${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/modules.order.*"

do_install:append() {
    install -Dm0644 ${S}/81-gtx-bt-remote.rules ${D}${sysconfdir}/udev/rules.d/81-gtx-bt-remote.rules
}
