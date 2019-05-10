SUMMARY = "A pure Python DAAP client implementation"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fbc093901857fcd118f065f900982c24"

RDEPENDS_${PN} = "python-compression"

inherit gitpkgv

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/tominsam/PythonDaap;protocol=https \
           file://python-daap.patch"

S = "${WORKDIR}/git"

inherit distutils

include python-package-split.inc
