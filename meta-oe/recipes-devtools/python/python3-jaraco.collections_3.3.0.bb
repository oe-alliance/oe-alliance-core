SUMMARY = "Collection objects similar to those in stdlib by jaraco"
HOMEPAGE = "https://github.com/jaraco/jaraco.collections"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "1079160741e1e5fc1a741f9ca58febf1"
SRC_URI[sha256sum] = "3662267424b55f10bf15b6f5dee6a6e48a2865c0ec50cc7a16040c81c55a98dc"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
