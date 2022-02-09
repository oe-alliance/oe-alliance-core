SUMMARY  = "library to extract and remove data from HTML and XML using XPath and CSS selectors, optionally combined with regular expressions"
HOMEPAGE = "https://parsel.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e6f548d539cd10526f7256a0543f8c68"

DEPENDS = "${PYTHON_PN}-pytest-runner"

SRC_URI = "file://disable-test.patch"

SRC_URI[md5sum] = "524b9519a20f401cd44f06d7f725c856"
SRC_URI[sha256sum] = "70efef0b651a996cceebc69e55a85eb2233be0890959203ba7c3a03c72725c79"

S = "${WORKDIR}/parsel-${PV}"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
