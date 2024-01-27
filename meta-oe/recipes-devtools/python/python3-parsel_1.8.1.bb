SUMMARY  = "library to extract and remove data from HTML and XML using XPath and CSS selectors, optionally combined with regular expressions"
HOMEPAGE = "https://parsel.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e6f548d539cd10526f7256a0543f8c68"

DEPENDS = "${PYTHON_PN}-pytest-runner"

SRC_URI = "file://disable-test.patch"

SRC_URI[md5sum] = "0721c0c3e92cfd290e54ec7ba6ca2f74"
SRC_URI[sha256sum] = "aff28e68c9b3f1a901db2a4e3f158d8480a38724d7328ee751c1a4e1c1801e39"

S = "${WORKDIR}/parsel-${PV}"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
