SUMMARY = "A pure Python DAAP client implementation"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fbc093901857fcd118f065f900982c24"

RDEPENDS:${PN} = "${PYTHON_PN}-compression"

inherit gitpkgv

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/abdelgmartinezl/PythonDaap;protocol=https;branch=master \
           file://python-daap.patch \
           file://0001-The-member-ob_type-is-not-present-in-the-pcapobject-.patch \
"

S = "${WORKDIR}/git"

inherit setuptools3_legacy

include ${PYTHON_PN}-package-split.inc

CFLAGS += "-Wno-error=int-conversion -Wno-error=implicit-function-declaration -Wno-error=incompatible-pointer-types"
