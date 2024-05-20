SUMMARY  = "The ItemAdapter class is a wrapper for data container objects, providing a common interface to handle objects of different types in an uniform manner, regardless of their underlying implementation."
HOMEPAGE = "https://github.com/scrapy/itemadapter"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a669d9fc44456c80ce6829e1b809742c"

SRC_URI[md5sum] = "9a2007a96edc40bfd9449b00f4597489"
SRC_URI[sha256sum] = "e4f958a6b6b6f5831fa207373010031a0bd7ed0429ddd09b51979c011475cafd"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
