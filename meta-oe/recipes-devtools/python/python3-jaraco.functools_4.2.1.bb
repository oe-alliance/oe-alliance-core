SUMMARY = "Functools like those found in stdlib"
HOMEPAGE = "https://github.com/jaraco/jaraco.functools"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1aeae65f25a15b1e46d4381f2f094e0a"
DEPENDS += "python3-setuptools-scm-native python3-coherent-licensed-native"

PYPI_PACKAGE = "jaraco_functools"

SRC_URI[md5sum] = "23b95c0de9c5f97dcc033f25fdcc9d0c"
SRC_URI[sha256sum] = "be634abfccabce56fa3053f8c7ebe37b682683a4ee7793670ced17bab0087353"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
