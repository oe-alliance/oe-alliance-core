SUMMARY = "Google API Client Library for Python"
DESCRIPTION = "The Google API Client for Python is a client library for accessing the Plus, Moderator, and many other Google APIs."
HOMEPAGE = "http://google.github.io/google-api-python-client/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94023d14f6b58272fd885e4e3f2f08b3"

SRC_URI = "https://pypi.python.org/packages/source/g/google-api-python-client/google-api-python-client-1.4.1.tar.gz"

SRC_URI[md5sum] = "3e2cf16cf5502f6315aaf2fc8f1d7437"
SRC_URI[sha256sum] = "63d3bad8b29337cfd1c8b96e403dc0fe61e437a118eeff0c1eaa109c96e13f9c"

S = "${WORKDIR}/google-api-python-client-1.4.1/"


inherit setuptools
include python-package-split.inc
