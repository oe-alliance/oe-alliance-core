SUMMARY = "Functools like those found in stdlib"
HOMEPAGE = "https://github.com/jaraco/jaraco.functools"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1aeae65f25a15b1e46d4381f2f094e0a"
DEPENDS += "python3-setuptools-scm-native python3-coherent-licensed-native"

PYPI_PACKAGE = "jaraco_functools"

SRC_URI[md5sum] = "501918900674199182b1bfa39db200b6"
SRC_URI[sha256sum] = "da21933b0417b89515562656547a77b4931f98176eb173644c0d35032a33d6bb"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
