SUMMARY = "yEnc Module for Python3 modified for SABnzbd"
HOMEPAGE = "https://github.com/sabnzbd/sabnzbd-yenc"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=312b694d5f1e0b8f15ffce22799df2f6"

SRC_URI:append = " file://0001-disable-platformcheck.patch"

SRC_URI[md5sum] = "a8e788ff9388ebb7a924ac68e9417b79"
SRC_URI[sha256sum] = "7a2fd29d58383b2e9c0413f1835e2530c40181b351e0583469afbcb150536346"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
