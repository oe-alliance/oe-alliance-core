SUMMARY = "SABCTools - C implementations of functions for use within SABnzbd"
DESCRIPTION = "This module implements three main sets of C implementations that are used within SABnzbd"
HOMEPAGE = "https://pypi.org/project/sabctools/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=892f569a555ba9c07a568a7c0c4fa63a"

inherit pypi setuptools3

SRC_URI:append = " file://remove-x64-instructions.patch"

SRC_URI[md5sum] = "eb0ffdd32354b23454f8df74fd50de78"
SRC_URI[sha256sum] = "70fdc60a9da61a2bed8f203beafaad3bb989c998c778d92f04b976917c4e4f9c"

include ${PYTHON_PN}-package-split.inc
