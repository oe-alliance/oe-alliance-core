SUMMARY = "Python 2 and 3 compatibility utilities."
HOMEPAGE = "http://pypi.python.org/pypi/six/1.9.0"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6f00d4a50713fa859858dd9abaa35b21"

SRCNAME = "six"

PR = "r0"

SRC_URI = "http://pypi.python.org/packages/source/s/six/six-${PV}.tar.gz"
SRC_URI[md5sum] = "476881ef4012262dfc8adc645ee786c4"
SRC_URI[sha256sum] = "e24052411fc4fbd1f672635537c3fc2330d9481b18c0317695b46259512c91d5"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils

include python-package-split.inc

