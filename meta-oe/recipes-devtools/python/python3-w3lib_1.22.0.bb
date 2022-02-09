SUMMARY  = "This is a Python library of web-related functions"
HOMEPAGE = "https://github.com/scrapy/w3lib"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8a9dbf92a1904187a362d3fe098d4a1f"

SRC_URI[md5sum] = "2eceef8cc9e3be81ae3eb825c23449cb"
SRC_URI[sha256sum] = "0ad6d0203157d61149fd45aaed2e24f53902989c32fc1dccc2e2bfba371560df"

S = "${WORKDIR}/w3lib-${PV}"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
