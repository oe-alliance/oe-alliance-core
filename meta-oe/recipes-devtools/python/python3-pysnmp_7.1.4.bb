SUMMARY = "Python SNMP Toolkit"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=1e9586cd38e6fa7f15d53da03ebf70ec"

RDEPENDS:${PN} = "${PYTHON_PN}-core"

S = "${WORKDIR}/pysnmp-${PV}"

inherit pypi python_poetry_core

SRC_URI[md5sum] = "9d1ba65a734a613e68d616f3f4c24b6f"
SRC_URI[sha256sum] = "4ec7fd78cb7437ad64644108dea603cad8e49f065da3dd3e0539dda68bad320c"

include ${PYTHON_PN}-package-split.inc
