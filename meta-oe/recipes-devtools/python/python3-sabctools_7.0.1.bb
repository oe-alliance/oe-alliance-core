SUMMARY = "SABCTools - C implementations of functions for use within SABnzbd"
DESCRIPTION = "This module implements three main sets of C implementations that are used within SABnzbd"
HOMEPAGE = "https://pypi.org/project/sabctools/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=892f569a555ba9c07a568a7c0c4fa63a"

inherit pypi setuptools3

SRC_URI:append = " file://0001-disable-platformcheck.patch"

SRC_URI[md5sum] = "9a75fe6e68f1141ca1bd33cbde35b50a"
SRC_URI[sha256sum] = "d89744a46825db35e1c9b107bb97db5f1c5c0fd0245ea0fe76e3bbe08c3ca44f"

include ${PYTHON_PN}-package-split.inc
