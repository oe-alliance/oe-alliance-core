SUMMARY = "Gigablue Blootooth RCU keymaps"
SECTION = "extra"
PRIORITY = "optional"
LICENSE = "GPL-2.0-only"
require conf/license/license-gplv2.inc

#different BT RCU device have it's own kl on /etc/keymap/
#according to vendor id and product id of device.

PR = "r0"

SRC_URI  = "file://Vendor_0508_Product_0110.kl"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/keymap
    install -m 0755 ${S}/Vendor_0508_Product_0110.kl ${D}${sysconfdir}/keymap/
}

do_package_qa() {
}

FILES:${PN}  = "${sysconfdir}"


