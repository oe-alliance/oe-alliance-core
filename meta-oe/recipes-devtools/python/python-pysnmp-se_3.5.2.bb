SUMMARY = "A Pure Python SNMP Package"
SECTION = "devel/python"
PRIORITY = "optional"
RDEPENDS_${PN} = "python-core"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;beginline=13;endline=14;md5=fe0c3d0cab1e25fcec2777b0a8b8e4bf"

PR = "r1"

SRCNAME = "pysnmp-se"
SRC_URI = "https://pypi.python.org/packages/f0/f3/4c0e4e1aef2972076333295f90d3651a01d18b0f6f7a6804d9ca6cdc1cc5/pysnmp-se-3.5.2.tar.gz"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils

SRC_URI[md5sum] = "445f5812110d33c72ad250798b9fdbb1"
SRC_URI[sha256sum] = "f6b4fd14b1e868fbb89e7d2bd9c5fbcd4ce4bd4fc1d4b460971cf810762eb17b"

include python-package-split.inc
