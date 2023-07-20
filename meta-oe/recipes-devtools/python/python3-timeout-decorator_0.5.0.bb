SUMMARY = "Timeout decorator"
HOMEPAGE = "https://github.com/pnpnpn/timeout-decorator"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://MANIFEST.in;md5=4d9aa6a7d22a4cf4bd70d3f680ebf4c0"

SRC_URI[md5sum] = "8545649e70b3ca43bcff11f08a996c3e"
SRC_URI[sha256sum] = "6a2f2f58db1c5b24a2cc79de6345760377ad8bdc13813f5265f6c3e63d16b3d7"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
