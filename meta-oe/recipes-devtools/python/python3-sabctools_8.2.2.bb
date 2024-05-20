SUMMARY = "SABCTools - C implementations of functions for use within SABnzbd"
DESCRIPTION = "This module implements three main sets of C implementations that are used within SABnzbd"
HOMEPAGE = "https://pypi.org/project/sabctools/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=892f569a555ba9c07a568a7c0c4fa63a"

inherit pypi setuptools3

SRC_URI:append = " file://0001-disable-platformcheck.patch"

SRC_URI[md5sum] = "da1d1d2d96c0b62951acfd546dcbf12d"
SRC_URI[sha256sum] = "ad023809282dd7707c246149d800dcc6195284fdb7cb4a55ecce52b1c98793e8"

include ${PYTHON_PN}-package-split.inc
