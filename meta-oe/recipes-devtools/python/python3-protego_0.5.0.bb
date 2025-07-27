SUMMARY  = "Protego is a pure-Python robots.txt parser with support for modern conventions."
HOMEPAGE = "https://pypi.org/project/Protego/#description"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4d4c85a6e830a1da71d07eebd4017802"

DEPENDS += "python3-hatch-vcs-native"

SRC_URI[md5sum] = "6cc33ac9047fa0460c12764c902a64a5"
SRC_URI[sha256sum] = "225dee0acfcc71de8c6f7cef9c618e5a9d3e7baa7ae1470b8d076a064033c463"

inherit pypi python_hatchling

include python3-package-split.inc
