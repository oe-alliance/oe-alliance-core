SUMMARY = "A pure Python DAAP client implementation"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fbc093901857fcd118f065f900982c24"

RDEPENDS_${PN} = "${PYTHON_PN}-compression"

inherit gitpkgv

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/abdelgmartinezl/PythonDaap;protocol=https \
           file://python-daap.patch \
           file://0001-The-member-ob_type-is-not-present-in-the-pcapobject-.patch \
"

S = "${WORKDIR}/git"

inherit distutils3

include ${PYTHON_PN}-package-split.inc
