SUMMARY = "SABCTools - C implementations of functions for use within SABnzbd"
DESCRIPTION = "This module implements three main sets of C implementations that are used within SABnzbd"
HOMEPAGE = "https://pypi.org/project/sabctools/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=892f569a555ba9c07a568a7c0c4fa63a"

inherit pypi setuptools3

SRC_URI:append = " file://0001-disable-platformcheck.patch"

SRC_URI[md5sum] = "0acda41a34f0743d5e2c98abc47ec757"
SRC_URI[sha256sum] = "3d87db991f704f74874fea05c90176175de0ec581dbd8fe5f69d03eb973ff915"

include ${PYTHON_PN}-package-split.inc
