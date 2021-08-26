SUMMARY = "preinstall several wifi driver packages"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r13"

OPTIONAL_WIFIDRIVERS = "${@bb.utils.contains("MACHINE_FEATURES", "wifiusblegacy", "enigma2-plugin-drivers-network-usb-rt3070", " \
    enigma2-plugin-drivers-network-usb-ath9k-htc \
    enigma2-plugin-drivers-network-usb-carl9170 \
    enigma2-plugin-drivers-network-usb-rt2500 \
    enigma2-plugin-drivers-network-usb-rt2800 \
    enigma2-plugin-drivers-network-usb-rtl8187 \
    enigma2-plugin-drivers-network-usb-zd1211rw \
    enigma2-plugin-drivers-network-usb-r8188eu \
    ", d)} \
    enigma2-plugin-drivers-network-usb-rt73 \
    enigma2-plugin-drivers-network-usb-r8712u \
    "

DEPENDS = "enigma2 enigma2-plugins enigma2-oe-alliance-plugins network-usb-drivers-meta"

RDEPENDS_${PN} = "\
    enigma2-plugin-systemplugins-wirelesslan \
    ${@bb.utils.contains("MACHINE", "vuduo2", "enigma2-plugin-drivers-network-usb-rt3070", "", d)} \
    ${@bb.utils.contains("MACHINE", "inihdp", "enigma2-plugin-drivers-network-usb-rt3070 enigma2-plugin-drivers-network-usb-r8723a", "", d)} \
    ${@bb.utils.contains("MACHINE_BRAND", "Vu+", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@bb.utils.contains("MACHINE_BRAND", "GigaBlue", "enigma2-plugin-drivers-network-usb-rtl8192cu-rev2", "", d)} \
    ${@bb.utils.contains("MACHINE_BRAND", "GigaBlue", "enigma2-plugin-drivers-network-usb-rtl8192eu", "", d)} \
    ${@bb.utils.contains("MACHINE_BRAND", "GigaBlue", "enigma2-plugin-drivers-network-usb-rtl8812au enigma2-plugin-drivers-network-usb-rtl8822bu", "", d)} \
    ${@bb.utils.contains("BRAND_OEM", "ini", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "wifi61", "rt61", "", d)} \
    ${@bb.utils.contains("DISTRO_FEATURES", "wifi-extra", "${OPTIONAL_WIFIDRIVERS}", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "openatv", "enigma2-plugin-drivers-network-usb-rtl8192cu-rev2 enigma2-plugin-drivers-network-usb-rtl8812au", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "beyonwiz", "enigma2-plugin-drivers-network-usb-rtl8192cu-rev2 enigma2-plugin-drivers-network-usb-rtl8812au", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "openvix", "enigma2-plugin-drivers-network-usb-rtl8192cu-rev2", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "opennfr", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "openeight", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "egami", "enigma2-plugin-drivers-network-usb-rtl8192cu-rev2", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "opendroid", "enigma2-plugin-drivers-network-usb-rtl8192cu-rev2", "", d)} \
    ${@bb.utils.contains("DISTRO_NAME", "openhdf", "enigma2-plugin-drivers-network-usb-rtl8192cu-rev2 enigma2-plugin-drivers-network-usb-rtl8812au", "", d)} \
    ${@bb.utils.contains("MACHINEBUILD", "bre2ze", "enigma2-plugin-drivers-network-usb-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "9900lx", "enigma2-plugin-drivers-network-usb-mt7601u", "", d)} \
    ${@bb.utils.contains("MACHINE", "8100s", "enigma2-plugin-drivers-network-usb-mt7601u", "", d)} \
    ${@bb.utils.contains("BRAND_OEM", "xcore", "enigma2-plugin-drivers-network-usb-rt8723bs", "", d)} \
    ${@bb.utils.contains("MACHINE", "osnino", "enigma2-plugin-drivers-network-usb-rt8723bs", "", d)} \
    ${@bb.utils.contains("MACHINE", "osninoplus", "enigma2-plugin-drivers-network-usb-rt8723bs enigma2-plugin-drivers-network-usb-rt8723ds", "", d)} \
    ${@bb.utils.contains("MACHINE", "osninopro", "enigma2-plugin-drivers-network-usb-rt8723bs enigma2-plugin-drivers-network-usb-rt8723ds", "", d)} \
    ${@bb.utils.contains("MACHINEBUILD", "spycat4k", "enigma2-plugin-drivers-network-usb-qca9377", "", d)} \
    ${@bb.utils.contains("MACHINEBUILD", "spycat4kcombo", "enigma2-plugin-drivers-network-usb-qca9377", "", d)} \
    ${@bb.utils.contains("BRAND_OEM", "dinobot", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-rtl8822bu enigma2-plugin-drivers-network-usb-rtl8188fu", "", d)} \
    ${@bb.utils.contains("MACHINE", "alien5", "rtl8189es", "", d)} \
    ${@bb.utils.contains("MACHINE", "cc1", "enigma2-plugin-drivers-network-usb-rtl8192eu", "", d)} \
    ${@bb.utils.contains("MACHINE", "sf8008", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-mt7601u enigma2-plugin-drivers-network-usb-rtl8822cu", "", d)} \
    ${@bb.utils.contains("MACHINE", "sf8008m", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-mt7601u enigma2-plugin-drivers-network-usb-rtl8822cu", "", d)} \
    ${@bb.utils.contains("MACHINE", "sf8008opt", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-mt7601u enigma2-plugin-drivers-network-usb-rtl8822cu", "", d)} \
    ${@bb.utils.contains("MACHINE", "h9", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-mt7601u enigma2-plugin-drivers-network-usb-rtl8192fu", "", d)} \
    ${@bb.utils.contains("MACHINE", "h9se", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-mt7601u enigma2-plugin-drivers-network-usb-rtl8192fu", "", d)} \
    ${@bb.utils.contains("MACHINE", "h8", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-mt7601u enigma2-plugin-drivers-network-usb-rtl8192fu", "", d)} \
    ${@bb.utils.contains("MACHINE", "hzero", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-mt7601u enigma2-plugin-drivers-network-usb-rtl8192fu", "", d)} \
    ${@bb.utils.contains("MACHINE", "h9combo", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-mt7601u enigma2-plugin-drivers-network-usb-rtl8192fu", "", d)} \
    ${@bb.utils.contains("MACHINE", "h9combose", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-mt7601u enigma2-plugin-drivers-network-usb-rtl8192fu", "", d)} \
    ${@bb.utils.contains("MACHINE", "h10", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-mt7601u enigma2-plugin-drivers-network-usb-rtl8822bu enigma2-plugin-drivers-network-usb-rtl8192fu", "", d)} \
    ${@bb.utils.contains("MACHINE", "h11", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-mt7601u enigma2-plugin-drivers-network-usb-rtl8822bu enigma2-plugin-drivers-network-usb-rtl8192fu", "", d)} \
    ${@bb.utils.contains("MACHINE", "i55plus", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-mt7601u enigma2-plugin-drivers-network-usb-rtl8192fu", "", d)} \
    ${@bb.utils.contains("MACHINE", "i55se", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-mt7601u enigma2-plugin-drivers-network-usb-rtl8192fu", "", d)} \
    ${@bb.utils.contains("MACHINE", "ustym4kpro", "enigma2-plugin-drivers-network-usb-rtl8192eu enigma2-plugin-drivers-network-usb-rtl8822cu", "", d)} \
    ${@bb.utils.contains("MACHINEBUILD", "osmio4k", "enigma2-plugin-drivers-network-usb-qca6174", "", d)} \
    ${@bb.utils.contains("MACHINEBUILD", "osmio4kplus", "enigma2-plugin-drivers-network-usb-qca6174", "", d)} \
    ${@bb.utils.contains("MACHINEBUILD", "formuler1", "enigma2-plugin-drivers-network-usb-rtl8822bu", "", d)} \
    ${@bb.utils.contains("MACHINEBUILD", "hd51", "enigma2-plugin-drivers-network-usb-rtl8822bu", "", d)} \
    "

RDEPENDS_${PN}_remove_odroidc2 = "enigma2-plugin-drivers-network-usb-rtl8192cu"
RDEPENDS_${PN}_remove_odroidc2 = "enigma2-plugin-drivers-network-usb-rtl8192cu-rev2"
RDEPENDS_${PN}_remove_cube = "enigma2-plugin-drivers-network-usb-rtl8812au"

