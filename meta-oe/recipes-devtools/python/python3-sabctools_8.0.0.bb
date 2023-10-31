SUMMARY = "SABCTools - C implementations of functions for use within SABnzbd"
DESCRIPTION = "This module implements three main sets of C implementations that are used within SABnzbd"
HOMEPAGE = "https://pypi.org/project/sabctools/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=892f569a555ba9c07a568a7c0c4fa63a"

inherit pypi setuptools3

SRC_URI:append = " file://0001-disable-platformcheck.patch"

SRC_URI[md5sum] = "6277dd0d4f6319d22ca34b63fd8a880e"
SRC_URI[sha256sum] = "86b4691158669e6e00052a8dfc5dcc9650a0a090a0d4f74cfa856b411fae65b9"

include ${PYTHON_PN}-package-split.inc
