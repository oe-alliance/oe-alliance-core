SUMMARY = "PyAMF provides Action Message Format (AMF) support for Python that is compatible with the Adobe Flash Player"
HOMEPAGE = "http://www.pyamf.org/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d8bf5ff31155bfe951a02be0c29215d3"

SRC_URI = "https://files.pythonhosted.org/packages/a0/06/43976c0e3951b9bf7ba0d7d614a8e3e024eb5a1c6acecc9073b81c94fb52/PyAMF-${PV}.tar.gz"

SRC_URI[md5sum] = "51e810531a663b55e686286edb23e82a"
SRC_URI[sha256sum] = "0455d68983e3ee49f82721132074877428d58acec52f19697a88c03b5fba74e4"

S = "${WORKDIR}/PyAMF-${PV}"

inherit setuptools distutils

include python-package-split.inc
