SUMMARY = "Versioning It with your Version In Git"
HOMEPAGE = "https://github.com/jwodder/versioningit"
AUTHOR = "John Thorvald Wodder II <versioningit@varonathe.org>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=98f24e40391b9cd0b42e3d703bd0c332"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native ${PYTHON_PN}-hatchling-native ${PYTHON_PN}-hatch-fancy-pypi-readme-native"

RDEPENDS:${PN} += "${PYTHON_PN}-packaging ${PYTHON_PN}-tomli"

PYPI_PACKAGE = "versioningit"

SRC_URI[md5sum] = "48bdfc0e9e899ba6e5deabb79e30e81a"
SRC_URI[sha256sum] = "b0ba586e5af08b87dbe3354082910a1d0502c36202d496e1ae60ef3b41ee29c1"

inherit pypi python_hatchling

require python3-package-split.inc

FILES:${PN}-src += "${bindir}/versioningit"

BBCLASSEXTEND = "native"
