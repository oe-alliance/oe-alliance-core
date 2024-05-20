SUMMARY = "Functools like those found in stdlib"
HOMEPAGE = "https://github.com/jaraco/jaraco.functools"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

PYPI_PACKAGE = "jaraco_functools"

SRC_URI[md5sum] = "7bc3fe47d508077b0ddbc32f84dc1374"
SRC_URI[sha256sum] = "d33fa765374c0611b52f8b3a795f8900869aa88c84769d4d1746cd68fb28c3e8"

inherit pypi python_setuptools_build_meta

include ${PYTHON_PN}-package-split.inc
