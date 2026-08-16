FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# BlueZ ships attach drivers for ST, TI, Atheros, Qualcomm, Intel and Broadcom
# but none for Realtek, which receivers with a built-in Realtek module need.
SRC_URI:append = " file://0001-tools-Add-support-for-rtk_h5-type.patch"

# enigma2-plugin-extensions-btdevicesmanager depends on bluez-hcidump.
PROVIDES += "bluez-hcidump"
RPROVIDES:${PN} += "bluez-hcidump"

# Keep the profile set lean; oe-core enables the whole LE Audio stack by
# default, which no receiver uses. deprecated and tools are what build
# hciattach, so the Realtek driver above needs them.
PACKAGECONFIG ?= "\
    a2dp-profiles \
    avrcp-profiles \
    deprecated \
    hid-profiles \
    hog-profiles \
    network-profiles \
    obex-profiles \
    readline \
    tools \
    udev \
"

do_install:append() {
    install -d ${D}${sysconfdir}/bluetooth/
    install -m 0644 ${S}/profiles/network/network.conf ${D}${sysconfdir}/bluetooth/
    install -m 0644 ${S}/profiles/input/input.conf ${D}${sysconfdir}/bluetooth/
    rm -f ${D}${sysconfdir}/bluetooth/main.conf
}
