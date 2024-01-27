SUMMARY = "More routines for operating on iterables, beyond itertools"
HOMEPAGE = "https://github.com/more-itertools/more-itertools"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3396ea30f9d21389d7857719816f83b5"

SRC_URI:append = " file://0001-update-setup.py.patch"

SRC_URI[md5sum] = "1737bb4d9d09501f75f498538efe3cf4"
SRC_URI[sha256sum] = "8fccb480c43d3e99a00087634c06dd02b0d50fbf088b380de5a41a015ec239e1"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
