SUMMARY  = "The ItemAdapter class is a wrapper for data container objects, providing a common interface to handle objects of different types in an uniform manner, regardless of their underlying implementation."
HOMEPAGE = "https://github.com/scrapy/itemadapter"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a669d9fc44456c80ce6829e1b809742c"

SRC_URI[md5sum] = "098605736b3b553bb43b7119912b0819"
SRC_URI[sha256sum] = "3b0f27f4c5e2e8ae415d83e3d60d33adb7ba09b98c30638bc606fb1dff2ecdd2"

inherit pypi setuptools3

include python3-package-split.inc
