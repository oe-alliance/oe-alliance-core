SUMMARY  = "This is a Python library of web-related functions"
HOMEPAGE = "https://github.com/scrapy/w3lib"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8a9dbf92a1904187a362d3fe098d4a1f"

SRC_URI[md5sum] = "d3b5d5012f3be4c9c3bb615eea57a6c9"
SRC_URI[sha256sum] = "ed5b74e997eea2abe3c1321f916e344144ee8e9072a6f33463ee8e57f858a4b1"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
