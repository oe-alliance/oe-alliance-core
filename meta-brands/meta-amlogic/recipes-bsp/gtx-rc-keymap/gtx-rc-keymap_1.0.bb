SUMMARY = "GTX Combo NEC keymap and stock AO firmware IR wake configuration"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"
inherit module
COMPATIBLE_MACHINE = "^gtxcombo$"
PACKAGE_ARCH = "${MACHINE_ARCH}"
SRC_URI = "file://gtxcombo.toml file://81-gtx-remote.rules file://gtx-ir-keymap file://gtx-ir-wake.c file://Makefile"
S = "${UNPACKDIR}"
RDEPENDS:${PN} += "ir-keytable kernel-module-gtx-ir-wake kernel-module-meson-ir kernel-module-ir-nec-decoder"
KERNEL_MODULE_AUTOLOAD += "gtx-ir-wake"
FILES:${PN} += "${sbindir}/gtx-ir-keymap ${sysconfdir}/rc_keymaps/gtxcombo.toml ${sysconfdir}/udev/rules.d/81-gtx-remote.rules ${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/modules.order.*"

do_install:append() {
    install -Dm0755 ${S}/gtx-ir-keymap ${D}${sbindir}/gtx-ir-keymap
    install -Dm0644 ${S}/gtxcombo.toml ${D}${sysconfdir}/rc_keymaps/gtxcombo.toml
    install -Dm0644 ${S}/81-gtx-remote.rules ${D}${sysconfdir}/udev/rules.d/81-gtx-remote.rules
}
