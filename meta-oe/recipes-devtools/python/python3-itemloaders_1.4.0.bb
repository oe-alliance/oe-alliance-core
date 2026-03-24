SUMMARY  = "itemloaders is a library that helps you collect data from HTML and XML sources."
HOMEPAGE = "https://github.com/scrapy/itemloaders"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=786239b0f3b0d9c9403f6eecf35820dd"

DEPENDS += "python3-hatch-vcs-native"

RDEPENDS:${PN} = "python3-itemadapter python3-parsel python3-jmespath"

SRC_URI[md5sum] = "d202bdce0b5fd068614f110e88f3715e"
SRC_URI[sha256sum] = "b5338308a819098f43525b7afc5f7d46ba338ba4710f5ebe7a21b3b47bb29929"

inherit pypi python_hatchling

include python3-package-split.inc
