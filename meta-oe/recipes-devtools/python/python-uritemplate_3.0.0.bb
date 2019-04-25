SUMMARY = "Simple python library to deal with URI Templates."
HOMEPAGE = "https://uritemplate.readthedocs.org"
SECTION = "devel/python"
LICENSE = "Apache-2.0 | BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f2e14cc8f5f696fd9d47092e992265c1"

RDEPENDS_${PN} = "python"

SRC_URI = "https://files.pythonhosted.org/packages/cd/db/f7b98cdc3f81513fb25d3cbe2501d621882ee81150b745cdd1363278c10a/uritemplate-${PV}.tar.gz"

S = "${WORKDIR}/uritemplate-${PV}"

SRC_URI[md5sum] = "1ec31e0d8a2eec72357b2cef4bce5945"
SRC_URI[sha256sum] = "c02643cebe23fc8adb5e6becffe201185bf06c40bda5c0b4028a93f1527d011d"

inherit distutils

include python-package-split.inc
