SUMMARY = "attrs is the Python package that will bring back the joy of writing classes by relieving you from the drudgery of implementing object protocol"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d4ab25949a73fe7d4fdee93bcbdbf8ff"

PR = "r0"

SRC_URI = "https://pypi.io/packages/source/a/attrs/attrs-${PV}.tar.gz"

SRC_URI[md5sum] = "4ec003c49360853cf935113d1ae56151"
SRC_URI[sha256sum] = "80203177723e36f3bbe15aa8553da6e80d47bfe53647220ccaa9ad7a5e473ccc"

S = "${WORKDIR}/attrs-${PV}"

inherit setuptools

include python-package-split.inc
