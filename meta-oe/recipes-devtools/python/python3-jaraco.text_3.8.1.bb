SUMMARY = "Module for text manipulation"
HOMEPAGE = "https://github.com/jaraco/jaraco.text"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI:append = " file://0001-add-setup.py.patch"

SRC_URI[md5sum] = "dcb2cad70d159c971ea1ec486fc91157"
SRC_URI[sha256sum] = "450957c3f8fb9a553d9d3e60738733ab1c5cc27b36a463342adb937e9a70ab3e"

inherit pypi setuptools3


