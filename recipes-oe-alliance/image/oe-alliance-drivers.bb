DESCRIPTION = "preinstall several driver packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit task

PV = "1.0"
PR = "r6"
PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY_{PN} = "1"

DEPENDS_{PN} = "enigma2 network-usb-drivers-meta"

RDEPENDS_{PN} = "\
	${@base_contains("MACHINE", "gbquad", "enigma2-plugin-drivers-network-usb-smsc75xx", "", d)} \
	"
