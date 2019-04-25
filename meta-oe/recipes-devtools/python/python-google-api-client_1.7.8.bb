SUMMARY = "Google API Client Library for Python"
DESCRIPTION = "The Google API Client for Python is a client library for accessing the Plus, Moderator, and many other Google APIs."
HOMEPAGE = "https://github.com/googleapis/google-api-python-client"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94023d14f6b58272fd885e4e3f2f08b3"

SRC_URI = "https://files.pythonhosted.org/packages/7a/84/ffde3c95d4162cdc8bae710fecc6873cccb05ac33afc3b2c925c74cc0150/google-api-python-client-${PV}.tar.gz"

SRC_URI[md5sum] = "96764d2652824f870449725fb6b155dc"
SRC_URI[sha256sum] = "06907006ed5ce831018f03af3852d739c0b2489cdacfda6971bcc2075c762858"

S = "${WORKDIR}/google-api-python-client-${PV}"

inherit setuptools

include python-package-split.inc
