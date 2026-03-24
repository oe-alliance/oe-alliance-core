SUMMARY  = "The ItemAdapter class is a wrapper for data container objects, providing a common interface to handle objects of different types in an uniform manner, regardless of their underlying implementation."
HOMEPAGE = "https://github.com/scrapy/itemadapter"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a669d9fc44456c80ce6829e1b809742c"

DEPENDS += "python3-hatch-vcs-native"

SRC_URI[md5sum] = "60271ce5d66910462c7475e35223a56e"
SRC_URI[sha256sum] = "fa139c7be2aa80f8874b2f23d165d5d4aa47c4b85c54ab530b567fd5f684f1b4"

inherit pypi python_hatchling

include python3-package-split.inc
