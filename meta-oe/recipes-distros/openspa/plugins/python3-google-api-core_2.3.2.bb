SUMMARY = "Google API client core library"
DESCRIPTION = "The Google API core for Python is a library for accessing the Plus, Moderator, and many other Google APIs."
HOMEPAGE = "https://github.com/googleapis/python-api-core"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "https://files.pythonhosted.org/packages/5c/64/30a91e03a7c80463d21423d81bf77116cffa02166d642f754b8fcb0df729/google-api-core-${PV}.tar.gz"
SRC_URI[md5sum] = "a9abd6b09b9f0ff32f5f617c0f7448e2"
SRC_URI[sha256sum] = "c8889f45cf58deca522888ae1d39b2a25e93e7d1b019ae8cee6456d5c726a40c"

S = "${WORKDIR}/google-api-core-${PV}"

inherit setuptools3 ${PYTHON_PN}-dir

#include ${PYTHON_PN}-package-split.inc
