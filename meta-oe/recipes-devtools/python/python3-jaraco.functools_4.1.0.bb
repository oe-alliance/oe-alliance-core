SUMMARY = "Functools like those found in stdlib"
HOMEPAGE = "https://github.com/jaraco/jaraco.functools"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

PYPI_PACKAGE = "jaraco_functools"

SRC_URI[md5sum] = "b8a8d165da986efa1966abd91c45348e"
SRC_URI[sha256sum] = "70f7e0e2ae076498e212562325e805204fc092d7b4c17e0e86c959e249701a9d"

inherit pypi python_setuptools_build_meta

include ${PYTHON_PN}-package-split.inc
