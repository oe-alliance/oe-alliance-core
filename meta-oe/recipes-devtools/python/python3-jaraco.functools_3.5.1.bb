SUMMARY = "Functools like those found in stdlib"
HOMEPAGE = "https://github.com/jaraco/jaraco.functools"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI:append = " file://0001-add-setup.py.patch"

SRC_URI[md5sum] = "bc956f774d9c6fe097d611326a6f6380"
SRC_URI[sha256sum] = "d0adcf91710a0853efe9f23a78fad586bf67df572f0d6d8e0fa36d289ae1c1d9"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
