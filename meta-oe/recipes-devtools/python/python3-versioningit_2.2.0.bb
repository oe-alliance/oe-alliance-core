SUMMARY = "Versioning It with your Version In Git"
HOMEPAGE = "https://github.com/jwodder/versioningit"
AUTHOR = "John Thorvald Wodder II <versioningit@varonathe.org>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f50f611f98ab86be42339ced21a10fdc"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

RDEPENDS:${PN} += "${PYTHON_PN}-packaging ${PYTHON_PN}-tomli"

PYPI_PACKAGE = "versioningit"

SRC_URI[sha256sum] = "eb18e7ba7268a880bf1ccfe92e534e96ab34e0397f28dcbcb3fc0da4f6a5b6bd"

inherit pypi python_setuptools_build_meta

require python3-package-split.inc

FILES:${PN}-src += "${bindir}/versioningit"

BBCLASSEXTEND = "native"
