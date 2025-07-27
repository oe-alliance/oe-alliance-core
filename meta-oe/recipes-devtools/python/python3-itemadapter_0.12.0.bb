SUMMARY  = "The ItemAdapter class is a wrapper for data container objects, providing a common interface to handle objects of different types in an uniform manner, regardless of their underlying implementation."
HOMEPAGE = "https://github.com/scrapy/itemadapter"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a669d9fc44456c80ce6829e1b809742c"

DEPENDS += "python3-hatch-vcs-native"

SRC_URI[md5sum] = "60f9143ef897b4605ef612c0abc94e59"
SRC_URI[sha256sum] = "a5088243e88efe3098f1720855e707176e7355ad87d1d20e2b03297f5d15d1be"

inherit pypi python_hatchling

include python3-package-split.inc
