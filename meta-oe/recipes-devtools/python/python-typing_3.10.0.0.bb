SUMMARY = "Simple Python module defined by PEP 484."
HOMEPAGE = "https://github.com/python/typing"
LICENSE = "PSF"
LIC_FILES_CHKSUM = "file://LICENSE;md5=64fc2b30b67d0a8423c250e0386ed72f"

SRC_URI[sha256sum] = "13b4ad211f54ddbf93e5901a9967b1e07720c1d1b78d596ac6a439641aa1b130"

BBCLASSEXTEND = "native nativesdk"

inherit pypi setuptools
include python-package-split.inc
