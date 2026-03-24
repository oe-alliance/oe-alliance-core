SUMMARY = "Context managers by jaraco"
HOMEPAGE = "https://github.com/jaraco/jaraco.context"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2e21c3f75eb8d9427c8a611a8e83e9d6"
DEPENDS += "python3-setuptools-scm-native python3-coherent-licensed-native"

PYPI_PACKAGE = "jaraco_context"

SRC_URI[md5sum] = "3832a9304c95b6a209bd218411c6d696"
SRC_URI[sha256sum] = "f1a6c9d391e661cc5b8d39861ff077a7dc24dc23833ccee564b234b81c82dfe3"

inherit pypi python_setuptools_build_meta

