SUMMARY  = "library that implements object collections which are stored in memory or persisted to disk, provide a simple API, and run fast"
HOMEPAGE = "https://github.com/scrapy/queuelib"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8a9dbf92a1904187a362d3fe098d4a1f"

DEPENDS += "python3-hatch-vcs-native"

SRC_URI[md5sum] = "a30a7c7600a403eef1108c6ed5477ae7"
SRC_URI[sha256sum] = "582bc65514481100b0539bd671da6b355b878869cfc77d92c63b75fcc9cf8e27"

S = "${UNPACKDIR}/queuelib-${PV}"

inherit pypi python_hatchling

include python3-package-split.inc
