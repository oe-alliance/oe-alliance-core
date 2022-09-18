SUMMARY = "Utility functions for Python class constructs"
HOMEPAGE = "https://github.com/jaraco/jaraco.classes"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI:append = " file://0001-add-setup.py.patch"

SRC_URI[md5sum] = "2a5cad50af98c16639116f87f2cced7d"
SRC_URI[sha256sum] = "6745f113b0b588239ceb49532aa09c3ebb947433ce311ef2f8e3ad64ebb74594"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
