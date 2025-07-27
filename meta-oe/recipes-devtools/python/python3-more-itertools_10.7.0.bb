SUMMARY = "More routines for operating on iterables, beyond itertools"
HOMEPAGE = "https://github.com/more-itertools/more-itertools"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3396ea30f9d21389d7857719816f83b5"

PYPI_PACKAGE = "more_itertools"

SRC_URI[md5sum] = "62eb0a088624e979ae594a2de094ca1c"
SRC_URI[sha256sum] = "9fddd5403be01a94b204faadcff459ec3568cf110265d3c54323e1e866ad29d3"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
