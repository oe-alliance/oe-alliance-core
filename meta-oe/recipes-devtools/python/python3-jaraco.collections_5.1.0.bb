SUMMARY = "Collection objects similar to those in stdlib by jaraco"
HOMEPAGE = "https://github.com/jaraco/jaraco.collections"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

PYPI_PACKAGE = "jaraco_collections"

SRC_URI[md5sum] = "33a136eb3dd4c36090270ea2d4d43e7c"
SRC_URI[sha256sum] = "0e4829409d39ad18a40aa6754fee2767f4d9730c4ba66dc9df89f1d2756994c2"

inherit pypi python_setuptools_build_meta

include ${PYTHON_PN}-package-split.inc
