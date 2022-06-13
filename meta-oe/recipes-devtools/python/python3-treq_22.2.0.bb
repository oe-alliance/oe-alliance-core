SUMMARY = "High-level Twisted HTTP Client API"
DESCRIPTION = "Provides simple, higher level API for making HTTP requests when using Twisted."
HOMEPAGE = "https://github.com/twisted/treq"
AUTHOR = "David Reid <dreid@dreid.org>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a5dd67fd17479587f7093769d95ef186"

DEPENDS += "${PYTHON_PN}-incremental-native"

RDEPENDS:${PN} += "${PYTHON_PN}-attrs ${PYTHON_PN}-hyperlink ${PYTHON_PN}-incremental ${PYTHON_PN}-requests ${PYTHON_PN}-twisted"

PYPI_PACKAGE = "treq"

SRC_URI[sha256sum] = "df757e3f141fc782ede076a604521194ffcb40fa2645cf48e5a37060307f52ec"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
