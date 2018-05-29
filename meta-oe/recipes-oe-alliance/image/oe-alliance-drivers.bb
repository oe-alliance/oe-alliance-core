SUMMARY = "preinstall several driver packages"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

PV = "${IMAGE_VERSION}"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"

DEPENDS = "enigma2 network-usb-drivers-meta"

RDEPENDS_${PN}_gbquad = "enigma2-plugin-drivers-network-usb-smsc75xx"
RDEPENDS_${PN}_gbquadplus = "enigma2-plugin-drivers-network-usb-smsc75xx"
