SUMMARY = "High-level Twisted HTTP Client API"
DESCRIPTION = "Provides simple, higher level API for making HTTP requests when using Twisted."
HOMEPAGE = "https://github.com/twisted/treq"
AUTHOR = "David Reid <dreid@dreid.org>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a5dd67fd17479587f7093769d95ef186"

DEPENDS += "python3-hatch-vcs-native python3-incremental-native"

RDEPENDS:${PN} += "python3-multipart python3-typing-extensions python3-attrs python3-hyperlink python3-incremental python3-requests python3-twisted"

PYPI_PACKAGE = "treq"

SRC_URI[md5sum] = "b0a3c2e315375388e820019afc59919d"
SRC_URI[sha256sum] = "25dde3a55ae85ec2f2c56332c99aef255ab14f997d0d00552ebff13538a9804a"

inherit pypi python_hatchling

include python3-package-split.inc
