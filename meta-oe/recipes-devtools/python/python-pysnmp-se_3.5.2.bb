SUMMARY = "Python SNMP Toolkit (Speed Enhanced)"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;beginline=13;endline=14;md5=fe0c3d0cab1e25fcec2777b0a8b8e4bf"

RDEPENDS_${PN} = "python-core"

SRC_URI = "https://files.pythonhosted.org/packages/f0/f3/4c0e4e1aef2972076333295f90d3651a01d18b0f6f7a6804d9ca6cdc1cc5/pysnmp-se-${PV}.tar.gz"

S = "${WORKDIR}/pysnmp-se-${PV}"

inherit distutils

SRC_URI[md5sum] = "445f5812110d33c72ad250798b9fdbb1"
SRC_URI[sha256sum] = "f6b4fd14b1e868fbb89e7d2bd9c5fbcd4ce4bd4fc1d4b460971cf810762eb17b"

include python-package-split.inc
