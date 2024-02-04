SUMMARY = "meta file for USB Network drivers"
inherit packagegroup

require conf/license/license-gplv2.inc

export KERNEL_VERSION = "${@oe.utils.read_file('${STAGING_KERNEL_BUILDDIR}/kernel-abiversion')}"

DEPENDS = "\
    ${@bb.utils.contains("MACHINE_FEATURES", "wifiusblegacy", "", " \
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
    enigma2-plugin-drivers-network-usb-rtl8814au \
    enigma2-plugin-drivers-network-usb-rtl8822bu \
    enigma2-plugin-drivers-network-usb-rtl8188fu \
    enigma2-plugin-drivers-network-usb-rtl8192eu \
    enigma2-plugin-drivers-network-usb-rtl8192fu \
    enigma2-plugin-drivers-network-usb-rtl8821cu \
    ", d)} \
    enigma2-plugin-drivers-network-usb-asix \
    enigma2-plugin-drivers-network-usb-ax88179-178a \
    enigma2-plugin-drivers-network-usb-rt73 \
    enigma2-plugin-drivers-network-usb-rt3070 \
    enigma2-plugin-drivers-network-usb-rt3573 \
    enigma2-plugin-drivers-network-usb-rt5572 \
    enigma2-plugin-drivers-network-usb-r8712u \
    enigma2-plugin-drivers-network-usb-rtl8192cu \
    enigma2-plugin-drivers-network-usb-rtl8192cu-rev2 \
    enigma2-plugin-drivers-network-usb-rtl8192ce \
    enigma2-plugin-drivers-network-usb-r8188eu \
    enigma2-plugin-drivers-network-usb-rtl8152 \
    enigma2-plugin-drivers-network-usb-lan78xx \
    ${OPTIONAL_WIFI_PACKAGES} \
    "

OPTIONAL_WIFI_PACKAGES = "\
    ${@ 'enigma2-plugin-drivers-network-usb-rtl88xxau'     if ("${KERNEL_VERSION}" and bb.utils.vercmp_string("${KERNEL_VERSION}", '4.4') >= 0) else '' } \
    ${@ 'enigma2-plugin-drivers-network-usb-rtl8852cu'     if ("${KERNEL_VERSION}" and bb.utils.vercmp_string("${KERNEL_VERSION}", '3.10') >= 0) else '' } \
"

PR = "r1"
