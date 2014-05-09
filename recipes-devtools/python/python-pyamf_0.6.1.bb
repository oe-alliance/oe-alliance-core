SUMMARY = "PyAMF provides Action Message Format (AMF) support for Python that is compatible with the Adobe Flash Player"
HOMEPAGE = "http://www.pyamf.org/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d8bf5ff31155bfe951a02be0c29215d3"

PR = "r3"

SRC_URI = "http://pypi.python.org/packages/source/P/PyAMF/PyAMF-${PV}.tar.gz"

SRC_URI[md5sum] = "5ee33463974f37dfb5a043b5b773039f"
SRC_URI[sha256sum] = "d8affca77466289eb77eac408e444c7e0787c1d75133628c1461d06bc5538396"

S = "${WORKDIR}/PyAMF-${PV}"

inherit distutils

include python-package-split.inc
