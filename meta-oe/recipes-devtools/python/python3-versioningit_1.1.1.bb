SUMMARY = "Versioning It with your Version In Git"
HOMEPAGE = "https://github.com/jwodder/versioningit"
AUTHOR = "John Thorvald Wodder II <versioningit@varonathe.org>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c9c2eb279b50fd56de7da07a66d0032f"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

RDEPENDS:${PN} += "${PYTHON_PN}-packaging ${PYTHON_PN}-tomli"

SRC_URI[sha256sum] = "3b7565a2db92f1206b5b833a72cc6936c8a74d65e94224f0a9eb54799d1561d9"

PYPI_PACKAGE = "versioningit"

inherit pypi python_setuptools_build_meta

require python3-package-split.inc

FILES:${PN}-src += "${bindir}/versioningit"

BBCLASSEXTEND = "native"
