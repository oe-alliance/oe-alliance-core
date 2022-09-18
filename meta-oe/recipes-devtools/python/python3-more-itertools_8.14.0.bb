SUMMARY = "More routines for operating on iterables, beyond itertools"
HOMEPAGE = "https://github.com/more-itertools/more-itertools"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3396ea30f9d21389d7857719816f83b5"

SRC_URI:append = " file://0001-update-setup.py.patch"

SRC_URI[md5sum] = "0f498e65f3cb8c793399f38952a41883"
SRC_URI[sha256sum] = "c09443cd3d5438b8dafccd867a6bc1cb0894389e90cb53d227456b0b0bccb750"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
