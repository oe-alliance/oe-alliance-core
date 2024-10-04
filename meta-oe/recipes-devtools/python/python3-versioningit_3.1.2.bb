SUMMARY = "Versioning It with your Version In Git"
HOMEPAGE = "https://github.com/jwodder/versioningit"
AUTHOR = "John Thorvald Wodder II <versioningit@varonathe.org>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=98f24e40391b9cd0b42e3d703bd0c332"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native ${PYTHON_PN}-hatchling-native ${PYTHON_PN}-hatch-fancy-pypi-readme-native"

RDEPENDS:${PN} += "${PYTHON_PN}-packaging ${PYTHON_PN}-tomli"

PYPI_PACKAGE = "versioningit"

SRC_URI[md5sum] = "7e25b7db2b9d6c82d910d691b52b211c"
SRC_URI[sha256sum] = "4db83ed99f56b07d83940bee3445ca46ca120d13b6b304cdb5fb44e5aa4edec0"

inherit pypi python_hatchling

require python3-package-split.inc

FILES:${PN}-src += "${bindir}/versioningit"

BBCLASSEXTEND = "native"
