SUMMARY = "Module for text manipulation"
HOMEPAGE = "https://github.com/jaraco/jaraco.text"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "1d56c0f797c93b432ada888d3f9d94e7"
SRC_URI[sha256sum] = "ede4e9103443b62b3d1d193257dfb85aab7c69a6cef78a0887d64bb307a03bc3"

inherit pypi setuptools3


