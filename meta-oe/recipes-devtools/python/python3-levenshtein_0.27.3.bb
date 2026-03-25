SUMMARY = "Python Levenshtein"
HOMEPAGE = "http://github.com/joncasdam/python-Levenshtein"
SECTION = "devel/python"
DEPENDS = "${PYTHON_PN}"
LICENSE = "LGPL-2.0-or-later"
LIC_FILES_CHKSUM = " file://LICENSE;md5=37addc8bb77be42f2f0a1a68b235556f"

DEPENDS = "ninja-native cmake-native python3-scikit-build-native python3-scikit-build-core-native"

PYPI_PACKAGE = "levenshtein"

SRC_URI[sha256sum] = "1ac326b2c84215795163d8a5af471188918b8797b4953ec87aaba22c9c1f9fc0"

inherit cmake pypi python_setuptools_build_meta

include ${PYTHON_PN}-package-split.inc

INSANE_SKIP:${PN} = "already-stripped"
