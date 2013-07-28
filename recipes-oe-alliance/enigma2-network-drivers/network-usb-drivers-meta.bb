DESCRIPTION = "meta file for USB Network drivers"
PACKAGE_ARCH = "all"

require conf/license/openpli-gplv2.inc

DEPENDS = "\
	enigma2-plugin-drivers-network-usb-smsc75xx \
	enigma2-plugin-drivers-network-usb-asix \
	enigma2-plugin-drivers-network-usb-ax88179-178a \
	${@base_contains("MACHINE", "vusolo", "enigma2-plugin-drivers-network-usb-rt5572" , "", d)} \
	${@base_contains("MACHINE", "vuduo", "enigma2-plugin-drivers-network-usb-rt5572" , "", d)} \
	${@base_contains("MACHINE", "vuuno", "enigma2-plugin-drivers-network-usb-rt5572" , "", d)} \
	${@base_contains("MACHINE", "vuultimo", "enigma2-plugin-drivers-network-usb-rt5572" , "", d)} \
	${@base_contains("MACHINE", "vusolo2", "enigma2-plugin-drivers-network-usb-rt5572" , "", d)} \
	${@base_contains("MACHINE", "vuduo2", "enigma2-plugin-drivers-network-usb-rt5572" , "", d)} \
	${@base_contains("MACHINE", "gbquad", "enigma2-plugin-drivers-network-usb-rt3573" , "", d)} \
	${@base_contains("MACHINE", "inihdp", "enigma2-plugin-drivers-network-usb-rt5572" , "", d)} \
	"

PR = "r4"
