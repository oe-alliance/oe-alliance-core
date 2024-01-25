SUMMARY  = "Rebulk - Define simple search patterns in bulk to perform advanced matching on any string."
HOMEPAGE = "https://github.com/Toilal/rebulk/"
SECTION = "devel/python"
LICENSE = "BSD-4-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df5f9321c8784271adb6c95a3da69f82"

RDEPENDS:${PN} = "${PYTHON_PN}-regex"

SRC_URI += "file://disable-test.patch"

SRC_URI[md5sum] = "e2c88915303b311cea24b200ab332375"
SRC_URI[sha256sum] = "0d30bf80fca00fa9c697185ac475daac9bde5f646ce3338c9ff5d5dc1ebdfebc"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
