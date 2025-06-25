SUMMARY = "Python Levenshtein"
HOMEPAGE = "http://github.com/joncasdam/python-Levenshtein"
SECTION = "devel/python"
DEPENDS = "${PYTHON_PN}"
LICENSE = "LGPL-2.0-or-later"
LIC_FILES_CHKSUM = " file://COPYING;md5=e4dc568e8869637acb7c89f0889d8130"

DEPENDS = "ninja-native cmake-native python3-scikit-build-native python3-scikit-build-core-native"

PYPI_PACKAGE = "levenshtein"

SRC_URI[sha256sum] = "3e18b73564cfc846eec94dd13fab6cb006b5d2e0cc56bad1fd7d5585881302e3"

S = "${UNPACKDIR}/levenshtein-${PV}"

inherit pypi python_setuptools_build_meta

include ${PYTHON_PN}-package-split.inc

INSANE_SKIP:${PN} = "already-stripped"
