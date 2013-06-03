DESCRIPTION = "meta file for USB Network drivers"
PACKAGE_ARCH = "all"

require conf/license/openpli-gplv2.inc

DEPENDS = "\
	enigma2-plugin-drivers-network-usb-smsc75xx \
	enigma2-plugin-drivers-network-usb-asix \
	enigma2-plugin-drivers-network-usb-ax88179-178a \
	"

PR = "r2"
