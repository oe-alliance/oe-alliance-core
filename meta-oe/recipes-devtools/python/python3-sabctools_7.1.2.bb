SUMMARY = "SABCTools - C implementations of functions for use within SABnzbd"
DESCRIPTION = "This module implements three main sets of C implementations that are used within SABnzbd"
HOMEPAGE = "https://pypi.org/project/sabctools/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=892f569a555ba9c07a568a7c0c4fa63a"

inherit pypi setuptools3

SRC_URI:append = " file://0001-disable-platformcheck.patch"

SRC_URI[md5sum] = "35d6bd261734f53b6658ae3a1d22a93f"
SRC_URI[sha256sum] = "c038055eec5c966a8c9515f2afdaa9aee24970e5df3a23964d95d7e77b98101f"

include ${PYTHON_PN}-package-split.inc
