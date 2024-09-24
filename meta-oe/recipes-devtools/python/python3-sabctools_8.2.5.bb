SUMMARY = "SABCTools - C implementations of functions for use within SABnzbd"
DESCRIPTION = "This module implements three main sets of C implementations that are used within SABnzbd"
HOMEPAGE = "https://pypi.org/project/sabctools/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=892f569a555ba9c07a568a7c0c4fa63a"

inherit pypi setuptools3

SRC_URI:append = " file://remove-x64-instructions.patch"

SRC_URI[md5sum] = "b26e3c8d48797e6912546dc2519ec064"
SRC_URI[sha256sum] = "6440bcd77fc9a463c414bfa75ca1405c57d4aeb84408222a38d7b6ecbc12d348"

include ${PYTHON_PN}-package-split.inc
