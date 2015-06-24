SUMMARY = "preinstall several driver packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

PV = "1.0"
PR = "r11"
PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY_${PN} = "1"

DEPENDS = "enigma2 network-usb-drivers-meta"

DEPENDS_remove_xc7362 = "network-usb-drivers-meta"

RDEPENDS_${PN}_gbquad = "enigma2-plugin-drivers-network-usb-smsc75xx"
RDEPENDS_${PN}_gbquadplus = "enigma2-plugin-drivers-network-usb-smsc75xx"
