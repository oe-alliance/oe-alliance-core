SUMMARY  = "library to extract and remove data from HTML and XML using XPath and CSS selectors, optionally combined with regular expressions"
HOMEPAGE = "https://parsel.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e6f548d539cd10526f7256a0543f8c68"

DEPENDS = "${PYTHON_PN}-pytest-runner"

SRC_URI[md5sum] = "cd242530b761e477244eeac2caeadb85"
SRC_URI[sha256sum] = "14e00dc07731c9030db620c195fcae884b5b4848e9f9c523c6119f708ccfa9ac"

S = "${WORKDIR}/parsel-${PV}"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
