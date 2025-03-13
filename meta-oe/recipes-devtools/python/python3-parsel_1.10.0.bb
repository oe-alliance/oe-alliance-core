SUMMARY  = "library to extract and remove data from HTML and XML using XPath and CSS selectors, optionally combined with regular expressions"
HOMEPAGE = "https://parsel.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e6f548d539cd10526f7256a0543f8c68"

DEPENDS = "python3-pytest-runner"

SRC_URI[md5sum] = "1bb9a48d97506dcd9297e05bffac4f83"
SRC_URI[sha256sum] = "14f17db9559f51b43357b9dfe43cec870a8efb5ea4857abb624ec6ff80d8a080"

S = "${WORKDIR}/parsel-${PV}"

inherit pypi setuptools3

include python3-package-split.inc
