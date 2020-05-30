SUMMARY = "A simple Python API for Bluetooth D-Bus calls"
DESCRIPTION = "Allows easy pairing, connecting and scanning. Also provides a TCP-to-RFCOMM socket bridge for data transfer."
HOMEPAGE = "https://github.com/emlid/bluetool"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4fe869ee987a340198fb0d54c55c47f1"

RDEPENDS_${PN} = "${PYTHON_PN}-dbus ${PYTHON_PN}-tcpbridge ${PYTHON_PN}-pygobject"

inherit pypi setuptools3

SRC_URI[md5sum] = "f65c673c45c5291bce8a0b8755986b9f"
SRC_URI[sha256sum] = "09aca1174ea9d8b402f2231aa2277726174c30482710fc887ebbda7eb820f614"

include ${PYTHON_PN}-package-split.inc
