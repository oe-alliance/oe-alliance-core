SUMMARY = "meta file for USB Network drivers"
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

require conf/license/license-gplv2.inc

DEPENDS = "\
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
    enigma2-plugin-drivers-network-usb-asix \
    enigma2-plugin-drivers-network-usb-ax88179-178a \
    enigma2-plugin-drivers-network-usb-rt73 \
    enigma2-plugin-drivers-network-usb-rt3070 \
    enigma2-plugin-drivers-network-usb-rt3573 \
    enigma2-plugin-drivers-network-usb-rt5572 \
    enigma2-plugin-drivers-network-usb-r8712u \
    enigma2-plugin-drivers-network-usb-rtl8192cu \
    enigma2-plugin-drivers-network-usb-rtl8192ce \
    enigma2-plugin-drivers-network-usb-r8188eu \
    enigma2-plugin-drivers-network-usb-rtl8152 \
    enigma2-plugin-drivers-network-usb-lan78xx \
    ${OPTIONAL_WIFI_PACKAGES} \
    "

OPTIONAL_WIFI_PACKAGES = "\
    ${@ 'enigma2-plugin-drivers-network-usb-rtl8821au'     if bb.utils.vercmp_string_op('${PREFERRED_VERSION_${PREFERRED_PROVIDER_virtual/kernel}}', '4.4', '>=') else '' } \
    ${@ 'enigma2-plugin-drivers-network-usb-rtl8156'       if bb.utils.vercmp_string_op('${PREFERRED_VERSION_${PREFERRED_PROVIDER_virtual/kernel}}', '3.18', '>=') else '' } \
    ${@ 'enigma2-plugin-drivers-network-usb-rtl8852cu'     if bb.utils.vercmp_string_op('${PREFERRED_VERSION_${PREFERRED_PROVIDER_virtual/kernel}}', '3.10', '>=') else '' } \
    ${@ 'enigma2-plugin-drivers-network-usb-rtl8852bu'     if bb.utils.vercmp_string_op('${PREFERRED_VERSION_${PREFERRED_PROVIDER_virtual/kernel}}', '3.10', '>=') else '' } \
"

PR = "r4"
