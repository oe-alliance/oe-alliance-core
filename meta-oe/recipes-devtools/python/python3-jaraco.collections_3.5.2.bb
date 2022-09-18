SUMMARY = "Collection objects similar to those in stdlib by jaraco"
HOMEPAGE = "https://github.com/jaraco/jaraco.collections"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI:append = " file://0001-add-setup.py.patch"

SRC_URI[md5sum] = "70babb1218fdc8b0f7f163f5eb9c68f0"
SRC_URI[sha256sum] = "072b93eb35f9e48508485755534e66a34ef1cc84af291fd27f39b44d4c0dd2c3"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
