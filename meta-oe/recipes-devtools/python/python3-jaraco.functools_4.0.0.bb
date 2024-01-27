SUMMARY = "Functools like those found in stdlib"
HOMEPAGE = "https://github.com/jaraco/jaraco.functools"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI:append = " file://0001-add-setup.py.patch"

SRC_URI[md5sum] = "6683d6f56930d4f0b34157b083813cf3"
SRC_URI[sha256sum] = "c279cb24c93d694ef7270f970d499cab4d3813f4e08273f95398651a634f0925"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
