SUMMARY = "More routines for operating on iterables, beyond itertools"
HOMEPAGE = "https://github.com/more-itertools/more-itertools"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3396ea30f9d21389d7857719816f83b5"

SRC_URI:append = " file://0001-update-setup.py.patch"

SRC_URI[sha256sum] = "5a6257e40878ef0520b1803990e3e22303a41b5714006c32a3fd8304b26ea1ab"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
