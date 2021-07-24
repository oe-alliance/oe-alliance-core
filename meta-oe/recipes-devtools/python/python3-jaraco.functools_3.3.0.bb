SUMMARY = "Functools like those found in stdlib"
HOMEPAGE = "https://github.com/jaraco/jaraco.functools"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "253de2c7300904096f73b91cd5705ca1"
SRC_URI[sha256sum] = "bfcf7da71e2a0e980189b0744b59dba6c1dcf66dcd7a30f8a4413e478046b314"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
