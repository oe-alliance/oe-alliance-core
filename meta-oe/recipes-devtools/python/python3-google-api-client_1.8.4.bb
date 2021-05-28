SUMMARY = "Google API Client Library for Python"
DESCRIPTION = "The Google API Client for Python is a client library for accessing the Plus, Moderator, and many other Google APIs."
HOMEPAGE = "https://github.com/googleapis/google-api-python-client"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94023d14f6b58272fd885e4e3f2f08b3"

SRC_URI = "https://files.pythonhosted.org/packages/8d/c1/dcac1376ce4e247df687384d1417dc8fa9bfa13c3684a7ee9aae1729dd14/google-api-python-client-1.8.4.tar.gz"
SRC_URI[md5sum] = "99f1dc7475d90e8d7db624fbaf168e40"
SRC_URI[sha256sum] = "bbe212611fdc05364f3d20271cae53971bf4d485056e6c0d40748eddeeda9a19"

S = "${WORKDIR}/google-api-python-client-${PV}"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
