SUMMARY = "More routines for operating on iterables, beyond itertools"
HOMEPAGE = "https://github.com/more-itertools/more-itertools"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3396ea30f9d21389d7857719816f83b5"

PYPI_PACKAGE = "more_itertools"

SRC_URI[md5sum] = "05fc46d7baf167d89c82a383c9f0c2fa"
SRC_URI[sha256sum] = "f638ddf8a1a0d134181275fb5d58b086ead7c6a72429ad725c67503f13ba30bd"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
