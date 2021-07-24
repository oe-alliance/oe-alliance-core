SUMMARY = "More routines for operating on iterables, beyond itertools"
HOMEPAGE = "https://github.com/more-itertools/more-itertools"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3396ea30f9d21389d7857719816f83b5"

SRC_URI[md5sum] = "4d06c23a027844e341c435d05facd947"
SRC_URI[sha256sum] = "83f0308e05477c68f56ea3a888172c78ed5d5b3c282addb67508e7ba6c8f813a"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
