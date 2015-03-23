SUMMARY = "Foreign Function Interface for Python calling C code."
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5677e2fdbf7cdda61d6dd2b57df547bf"

PR = "r0"

SRC_URI = "http://pypi.python.org/packages/source/c/cffi/cffi-${PV}.tar.gz"

SRC_URI[md5sum] = "b1bf4625ae07a8a932f2f1a2eb200c54"
SRC_URI[sha256sum] = "1988ce7ff9c64ecd5077776175e90fd8f0a8c827cb241a23647175ce08126bb2"

S = "${WORKDIR}/cffi-${PV}"

inherit setuptools

export BUILD_SYS
export HOST_SYS

export STAGING_LIBDIR
export STAGING_INCDIR

include python-package-split.inc
