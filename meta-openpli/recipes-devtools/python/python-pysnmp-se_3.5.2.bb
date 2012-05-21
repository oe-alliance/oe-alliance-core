DESCRIPTION = "A Pure Python SNMP Package"
SECTION = "devel/python"
PRIORITY = "optional"
RDEPENDS_${PN} = "python-core"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;beginline=13;endline=14;md5=fe0c3d0cab1e25fcec2777b0a8b8e4bf"

SRCNAME = "pysnmp-se"
SRC_URI = "http://downloads.sourceforge.net/twistedsnmp/${SRCNAME}-${PV}.tar.gz"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils

SRC_URI[md5sum] = "445f5812110d33c72ad250798b9fdbb1"
SRC_URI[sha256sum] = "f6b4fd14b1e868fbb89e7d2bd9c5fbcd4ce4bd4fc1d4b460971cf810762eb17b"
