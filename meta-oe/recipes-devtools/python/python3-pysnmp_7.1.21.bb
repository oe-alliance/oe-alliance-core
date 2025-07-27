SUMMARY = "Python SNMP Toolkit"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=d4aed8ba2a0ff36c85e2753b66c36b45"

RDEPENDS:${PN} = "python3-core"

S = "${UNPACKDIR}/pysnmp-${PV}"

inherit pypi python_poetry_core

SRC_URI[md5sum] = "c22169870bd1c36a2eb66801f5ed7e26"
SRC_URI[sha256sum] = "d5fa54cf2021af1c93a439eec66ce716fc8df425c55ecc7ed5bca9f35e8145b2"

include python3-package-split.inc
