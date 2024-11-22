SUMMARY = "High-level Twisted HTTP Client API"
DESCRIPTION = "Provides simple, higher level API for making HTTP requests when using Twisted."
HOMEPAGE = "https://github.com/twisted/treq"
AUTHOR = "David Reid <dreid@dreid.org>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a5dd67fd17479587f7093769d95ef186"

DEPENDS += "python3-incremental-native"

RDEPENDS:${PN} += "python3-attrs python3-hyperlink python3-incremental python3-requests python3-twisted"

PYPI_PACKAGE = "treq"

SRC_URI[md5sum] = "a48996ff540651deded304b638ba54ea"
SRC_URI[sha256sum] = "15da7fc404f3e4ed59d0abe5f8eef4966fabbe618039a2a23bc7c15305cefea8"

inherit pypi setuptools3

include python3-package-split.inc
