SUMMARY = "preinstall several wifi driver packages"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "5.2"
PR = "r3"

OPTIONAL_WIFIDRIVERS = "${@bb.utils.contains("MACHINE_FEATURES", "wifiusblegacy", "enigma2-plugin-drivers-network-usb-rt3070", " \
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

DEPENDS_remove_xc7362 = "network-usb-drivers-meta"
DEPENDS_remove_wetekplay = "network-usb-drivers-meta"

RDEPENDS_${PN} = "\
    enigma2-plugin-systemplugins-wirelesslan \
    ${@bb.utils.contains("MACHINE", "vuduo2", "enigma2-plugin-drivers-network-usb-rt3070", "", d)} \
    ${@bb.utils.contains("MACHINE", "inihdp", "enigma2-plugin-drivers-network-usb-rt3070 enigma2-plugin-drivers-network-usb-r8723a", "", d)} \
    ${@bb.utils.contains("MACHINE_BRAND", "Vu+", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@bb.utils.contains("MACHINE_BRAND", "GigaBlue", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@bb.utils.contains("MACHINE_BRAND", "GigaBlue", "enigma2-plugin-drivers-network-usb-rtl8192eu", "", d)} \
    ${@bb.utils.contains("BRAND_OEM", "ini", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "wifi61", "rt61", "", d)} \
    ${@bb.utils.contains("DISTRO_FEATURES", "wifi-extra", "${OPTIONAL_WIFIDRIVERS}", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "openatv", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "openvix", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "axassupport", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "openxta", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "opennfr", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "odinsupport", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@bb.utils.contains("MACHINEBUILD", "bre2ze", "enigma2-plugin-drivers-network-usb-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "xc7362", "enigma2-plugin-drivers-network-usb-rt8723bs", "", d)} \
    "
