SUMMARY = "A simple wrapper around optparse for powerful command line utilities."
DESCRIPTION = "\
Click is a Python package for creating beautiful command line interfaces \
in a composable way with as little code as necessary. It's the "Command \
Line Interface Creation Kit". It's highly configurable but comes with \
sensible defaults out of the box."
HOMEPAGE = "http://click.pocoo.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=1fa98232fd645608937a0fdc82e999b8"

inherit pypi setuptools

SRC_URI[md5sum] = "53692f62cb99a1a10c59248f1776d9c0"
SRC_URI[sha256sum] = "d2b5255c7c6349bc1bd1e59e08cd12acbbd63ce649f2588755783aa94dfb6b1a"

UPSTREAM_CHECK_REGEX = "click/(?P<pver>\d+(\.\d+)+)/"

CLEANBROKEN = "1"

RDEPENDS_${PN} += "\
    ${PYTHON_PN}-io \
    ${PYTHON_PN}-threading \
    ${PYTHON_PN}-contextlib \
    "

BBCLASSEXTEND = "native nativesdk"

PYPI_PACKAGE = "click"

include python-package-split.inc
