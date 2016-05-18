SUMMARY = "meta file for USB Network drivers"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

DEPENDS = "\
    ${@base_contains("MACHINE_FEATURES", "wifiusblegacy", "", " \
    enigma2-plugin-drivers-network-usb-ath9k-htc \
    enigma2-plugin-drivers-network-usb-carl9170 \
    enigma2-plugin-drivers-network-usb-rt2500 \
    enigma2-plugin-drivers-network-usb-rt2800 \
    enigma2-plugin-drivers-network-usb-rtl8187 \
    enigma2-plugin-drivers-network-usb-smsc75xx \
    enigma2-plugin-drivers-network-usb-zd1211rw \
    enigma2-plugin-drivers-network-usb-rtl8812au \
    enigma2-plugin-drivers-network-usb-r8723a \
    enigma2-plugin-drivers-network-usb-rt8723bs \
    enigma2-plugin-drivers-network-usb-mt7601u \
    enigma2-plugin-drivers-network-usb-mt7610u \
    enigma2-plugin-drivers-network-usb-rtl8192eu \
    ", d)} \
    enigma2-plugin-drivers-network-usb-asix \
    enigma2-plugin-drivers-network-usb-ax88179-178a \
    enigma2-plugin-drivers-network-usb-rt73 \
    enigma2-plugin-drivers-network-usb-rt3070 \
    enigma2-plugin-drivers-network-usb-rt3573 \
    enigma2-plugin-drivers-network-usb-rt5572 \
    enigma2-plugin-drivers-network-usb-r8712u \
    enigma2-plugin-drivers-network-usb-rtl8192cu \
    "

PR = "r21"
