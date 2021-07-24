SUMMARY = "Objects and routines pertaining to date and time (tempora)"
HOMEPAGE = "https://github.com/jaraco/tempora"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "ab60cbeb6409d5b121595bc8057a0d0e"
SRC_URI[sha256sum] = "c54da0f05405f04eb67abbb1dff4448fd91428b58cb00f0f645ea36f6a927950"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
