SUMMARY  = "This is a Python library of web-related functions"
HOMEPAGE = "https://github.com/scrapy/w3lib"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8a9dbf92a1904187a362d3fe098d4a1f"

SRC_URI[md5sum] = "33cb5a532eb5650ca2a65b2d05522d86"
SRC_URI[sha256sum] = "756ff2d94c64e41c8d7c0c59fea12a5d0bc55e33a531c7988b4a163deb9b07dd"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
