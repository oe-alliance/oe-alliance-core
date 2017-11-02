SUMMARY = "Incremental library for versions your Python"
HOMEPAGE = "https://github.com/twisted/incremental"
DESCRIPTION = "Incremental is a small library that versions your Python projects."
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6ca9b07f08e2c72d48c74d363d1e0e15"

SRC_URI = "https://files.pythonhosted.org/packages/source/i/incremental/incremental-${PV}.tar.gz"

SRC_URI[md5sum] = "602746e0d438e075a5a9e0678140bba2"
SRC_URI[sha256sum] = "7b751696aaf36eebfab537e458929e194460051ccad279c72b755a167eebd4b3"

UPSTREAM_CHECK_URI = "https://pypi.python.org/pypi/incremental/"
UPSTREAM_CHECK_REGEX = "/incremental/(?P<pver>(\d+[\.\-_]*)+)"

S = "${WORKDIR}/incremental-${PV}"

inherit setuptools

include python-package-split.inc
