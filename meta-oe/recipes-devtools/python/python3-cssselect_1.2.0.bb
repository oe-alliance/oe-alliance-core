SUMMARY = "cssselect parses CSS3 Selectors and translates them to XPath 1.0"
HOMEPAGE = "https://github.com/scrapy/cssselect"
SECTION = "devel/python"
AUTHOR = "Ian Bicking <ianb@colorstudy.com>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=952026b3fd2f625f2a3c0aa21da2493d"

SRC_URI[md5sum] = "27fbafacce5447cb867acb240d35002a"
SRC_URI[sha256sum] = "666b19839cfaddb9ce9d36bfe4c969132c647b92fc9088c4e23f786b30f1b3dc"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
