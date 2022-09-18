SUMMARY = "Pure python implementation of magic file detection"
HOMEPAGE = "https://github.com/cdgriffith/puremagic"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cf32b06f642ac9e14b16e9e2a8e9ebfb"

SRC_URI[md5sum] = "1f0e4187ca903d2804cd2c5d2e4644e8"
SRC_URI[sha256sum] = "3d5df26cc7ec9aebbf842a09115a2fa85dc59ea6414fa568572c44775d796cbc"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
