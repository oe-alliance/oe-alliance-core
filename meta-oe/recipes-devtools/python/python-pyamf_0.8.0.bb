SUMMARY = "PyAMF provides Action Message Format (AMF) support for Python that is compatible with the Adobe Flash Player"
HOMEPAGE = "http://www.pyamf.org/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d8bf5ff31155bfe951a02be0c29215d3"

PR = "r3"

SRC_URI = "https://pypi.python.org/packages/source/P/PyAMF/PyAMF-${PV}.tar.gz"

SRC_URI[md5sum] = "51e810531a663b55e686286edb23e82a"
SRC_URI[sha256sum] = "0455d68983e3ee49f82721132074877428d58acec52f19697a88c03b5fba74e4"

S = "${WORKDIR}/PyAMF-${PV}"

inherit setuptools distutils

include python-package-split.inc
