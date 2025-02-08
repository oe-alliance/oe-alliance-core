SUMMARY = "Python Levenshtein"
HOMEPAGE = "http://github.com/joncasdam/python-Levenshtein"
SECTION = "devel/python"
DEPENDS = "${PYTHON_PN}"
LICENSE = "LGPL-2.0-or-later"
LIC_FILES_CHKSUM = " file://COPYING;md5=e4dc568e8869637acb7c89f0889d8130"

DEPENDS = "ninja-native cmake-native python3-scikit-build-native python3-scikit-build-core-native"

PYPI_PACKAGE = "levenshtein"

SRC_URI[sha256sum] = "0d19ba22330d50609b2349021ec3cf7d905c6fe21195a2d0d876a146e7ed2575"

S = "${WORKDIR}/levenshtein-${PV}"

inherit pypi python_setuptools_build_meta

include ${PYTHON_PN}-package-split.inc
