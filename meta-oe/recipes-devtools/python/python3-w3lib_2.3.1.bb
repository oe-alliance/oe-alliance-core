SUMMARY  = "This is a Python library of web-related functions"
HOMEPAGE = "https://github.com/scrapy/w3lib"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8a9dbf92a1904187a362d3fe098d4a1f"

SRC_URI[md5sum] = "7ef432898835f79a3c11100a992dbd03"
SRC_URI[sha256sum] = "5c8ac02a3027576174c2b61eb9a2170ba1b197cae767080771b6f1febda249a4"

inherit pypi setuptools3

include python3-package-split.inc
