SUMMARY = "C parser in Python"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d29d3ce07825100c58ca57eea171ab65"

PR = "r0"

SRC_URI = "http://pypi.python.org/packages/source/p/pycparser/pycparser-${PV}.tar.gz"

SRC_URI[md5sum] = "d87aed98c8a9f386aa56d365fe4d515f"
SRC_URI[sha256sum] = "957d98b661c0b64b580ab6f94b125e09b6714154ee51de40bca16d3f0076b86c"

S = "${WORKDIR}/pycparser-${PV}"

inherit setuptools

include python-package-split.inc
