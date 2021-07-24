SUMMARY = "Pure python implementation of magic file detection"
HOMEPAGE = "https://github.com/cdgriffith/puremagic"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fc1a0f75023ac50d9ca9336268a36f43"

SRC_URI[md5sum] = "dbc12ccaf0ff3b958b5a968ed6f23858"
SRC_URI[sha256sum] = "6ffea02b80ceec1381f9df513e0120b701a74b6efad92311ea80281c7081b108"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
