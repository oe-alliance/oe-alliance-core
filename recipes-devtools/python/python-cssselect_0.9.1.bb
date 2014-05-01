SUMMARY = "cssselect parses CSS3 Selectors and translates them to XPath 1.0"
HOMEPAGE = "https://pypi.python.org/pypi/cssselect"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=952026b3fd2f625f2a3c0aa21da2493d"

PR = "r1"

SRC_URI = "https://pypi.python.org/packages/source/c/cssselect/cssselect-${PV}.tar.gz"

SRC_URI[md5sum] = "c74f45966277dc7a0f768b9b0f3522ac"
SRC_URI[sha256sum] = "0535a7e27014874b27ae3a4d33e8749e345bdfa62766195208b7996bf1100682"

S = "${WORKDIR}/cssselect-${PV}"

inherit setuptools

include python-package-split.inc
