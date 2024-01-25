SUMMARY = "Collection objects similar to those in stdlib by jaraco"
HOMEPAGE = "https://github.com/jaraco/jaraco.collections"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI:append = " file://0001-add-setup.py.patch"

SRC_URI[md5sum] = "2b12f44216432f1ce2ead377fd7a2b93"
SRC_URI[sha256sum] = "1680e8d09f295f625c7ba926880175a26fdbe7092b4c76d198e30476b21cfe68"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
