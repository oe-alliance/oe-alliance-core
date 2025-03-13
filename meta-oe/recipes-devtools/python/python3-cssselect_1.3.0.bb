SUMMARY = "cssselect parses CSS3 Selectors and translates them to XPath 1.0"
HOMEPAGE = "https://github.com/scrapy/cssselect"
SECTION = "devel/python"
AUTHOR = "Ian Bicking <ianb@colorstudy.com>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=952026b3fd2f625f2a3c0aa21da2493d"

SRC_URI[md5sum] = "e0148abb13430399cbdbc173c3fa1c80"
SRC_URI[sha256sum] = "57f8a99424cfab289a1b6a816a43075a4b00948c86b4dcf3ef4ee7e15f7ab0c7"

inherit pypi setuptools3

include python3-package-split.inc
