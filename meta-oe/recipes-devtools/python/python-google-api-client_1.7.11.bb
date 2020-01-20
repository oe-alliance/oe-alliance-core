SUMMARY = "Google API Client Library for Python"
DESCRIPTION = "The Google API Client for Python is a client library for accessing the Plus, Moderator, and many other Google APIs."
HOMEPAGE = "https://github.com/googleapis/google-api-python-client"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94023d14f6b58272fd885e4e3f2f08b3"

SRC_URI = "https://files.pythonhosted.org/packages/5e/19/9fd511734c0dee8fa3d49f4109c75e7f95d3c31ed76c0e4a93fbba147807/google-api-python-client-1.7.11.tar.gz"
SRC_URI[md5sum] = "6e28e8caf2e4d55ed5b7c48a538a61c9"
SRC_URI[sha256sum] = "a8a88174f66d92aed7ebbd73744c2c319b4b1ce828e565f9ec721352d2e2fb8c"

S = "${WORKDIR}/google-api-python-client-${PV}"

inherit setuptools

include python-package-split.inc
