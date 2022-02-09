SUMMARY = "Python SNMP Toolkit"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=b15d29f500f748d1c2a15709769090a8"

RDEPENDS:${PN} = "${PYTHON_PN}-core"

S = "${WORKDIR}/pysnmp-${PV}"

inherit pypi setuptools3

SRC_URI[md5sum] = "2222880259daf6e2cb322e938c818276"
SRC_URI[sha256sum] = "0c3dbef2f958caca96071fe5c19de43e9c1b0484ab02a0cf08b190bcee768ba9"

include ${PYTHON_PN}-package-split.inc
