SUMMARY = "Google API Client Library for Python"
DESCRIPTION = "The Google API Client for Python is a client library for accessing the Plus, Moderator, and many other Google APIs."
HOMEPAGE = "http://google.github.io/google-api-python-client/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fda25bf118892c5553d74606cbce6e63"

SRC_URI = "https://google-api-python-client.googlecode.com/files/google-api-python-client-1.2.tar.gz"

SRC_URI[md5sum] = "031c69eacdd25606782d045b17f54934"
SRC_URI[sha256sum] = "3cb3f39c4a634950aee34f52e2a160b9a064b15210f7196ba364f670780aa675"

S = "${WORKDIR}/google-api-python-client-1.2/"

PR = "r0"

inherit setuptools
include python-package-split.inc
