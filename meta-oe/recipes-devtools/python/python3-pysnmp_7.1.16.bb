SUMMARY = "Python SNMP Toolkit"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=1e9586cd38e6fa7f15d53da03ebf70ec"

RDEPENDS:${PN} = "python3-core"

S = "${WORKDIR}/pysnmp-${PV}"

inherit pypi python_poetry_core

SRC_URI[md5sum] = "a6c2189839202b7ab76144743d7824b8"
SRC_URI[sha256sum] = "51581c70e410e456eb3faa24c42a094c82acfa961d16ad659b57c5818379dfcb"

include python3-package-split.inc
