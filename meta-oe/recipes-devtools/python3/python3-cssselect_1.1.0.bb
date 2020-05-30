SUMMARY = "cssselect parses CSS3 Selectors and translates them to XPath 1.0"
HOMEPAGE = "https://github.com/scrapy/cssselect"
SECTION = "devel/python"
AUTHOR = "Ian Bicking <ianb@colorstudy.com>"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=952026b3fd2f625f2a3c0aa21da2493d"

SRC_URI = "https://files.pythonhosted.org/packages/70/54/37630f6eb2c214cdee2ae56b7287394c8aa2f3bafb8b4eb8c3791aae7a14/cssselect-${PV}.tar.gz"
SRC_URI[md5sum] = "fa57704c1cb66cc8e537b782bd6b227e"
SRC_URI[sha256sum] = "f95f8dedd925fd8f54edb3d2dfb44c190d9d18512377d3c1e2388d16126879bc"

S = "${WORKDIR}/cssselect-${PV}"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
