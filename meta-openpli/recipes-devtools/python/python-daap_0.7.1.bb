SUMMARY = "A pure Python DAAP client implementation"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fbc093901857fcd118f065f900982c24"
PR = "r2"

inherit distutils

SRC_URI = "http://jerakeen.org/files/PythonDaap-${PV}.tar.gz"
S = "${WORKDIR}/PythonDaap-${PV}"

RDEPENDS_${PN} = "python-compression"


SRC_URI[md5sum] = "b3db3d60b0ee83f5f23101d2c3bb99e0"
SRC_URI[sha256sum] = "ea1d3a8141654781a0df31e6607c4722436fa33eb2e9934492770b3b61be8122"
