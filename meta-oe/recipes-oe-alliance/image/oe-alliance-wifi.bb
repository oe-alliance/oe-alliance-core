SUMMARY = "preinstall several wifi driver packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r18"
PACKAGE_ARCH = "${MACHINE_ARCH}"

OPTIONAL_WIFIDRIVERS = "${@base_contains("MACHINE_FEATURES", "wifiusblegacy", "enigma2-plugin-drivers-network-usb-rt3070", " \
    enigma2-plugin-drivers-network-usb-ath9k-htc \
    enigma2-plugin-drivers-network-usb-carl9170 \
    enigma2-plugin-drivers-network-usb-rt2500 \
    enigma2-plugin-drivers-network-usb-rt2800 \
    enigma2-plugin-drivers-network-usb-rtl8187 \
    enigma2-plugin-drivers-network-usb-zd1211rw \
    ", d)} \
    enigma2-plugin-drivers-network-usb-rt73 \
    enigma2-plugin-drivers-network-usb-r8712u \
    "

DEPENDS = "enigma2 enigma2-plugins enigma2-oe-alliance-plugins network-usb-drivers-meta"

RDEPENDS_${PN} = "\
    enigma2-plugin-systemplugins-wirelesslan \
    ${@base_contains("MACHINE", "vuduo2", "enigma2-plugin-drivers-network-usb-rt3070", "", d)} \
    ${@base_contains("MACHINE", "inihdp", "enigma2-plugin-drivers-network-usb-rt3070 enigma2-plugin-drivers-network-usb-r8723a", "", d)} \
    ${@base_contains("MACHINE_BRAND", "Vu+", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@base_contains("MACHINE_BRAND", "GigaBlue", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@base_contains("BRAND_OEM", "ini", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "wifi61", "rt61", "", d)} \
    ${@base_contains("DISTRO_FEATURES", "wifi-extra", "${OPTIONAL_WIFIDRIVERS}", "", d)} \
    ${@base_contains("DISTRO_NAME", "openatv", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@base_contains("DISTRO_NAME", "axassupport", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@base_contains("DISTRO_NAME", "odinsupport", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    "
