SUMMARY = "Symbolic constants in Python"
HOMEPAGE = "https://github.com/twisted/constantly"
DESCRIPTION = "A library that provides symbolic constant support. It includes collections and constants with text, numeric, and bit flag values."
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e393e4ddd223e3a74982efa784f89fd7"

SRC_URI = "https://files.pythonhosted.org/packages/source/c/constantly/constantly-${PV}.tar.gz"

SRC_URI[md5sum] = "f0762f083d83039758e53f8cf0086eef"
SRC_URI[sha256sum] = "586372eb92059873e29eba4f9dec8381541b4d3834660707faf8ba59146dfc35"

UPSTREAM_CHECK_URI = "https://pypi.python.org/pypi/constantly/"
UPSTREAM_CHECK_REGEX = "/constantly/(?P<pver>(\d+[\.\-_]*)+)"

S = "${WORKDIR}/constantly-${PV}"

inherit setuptools

include python-package-split.inc
