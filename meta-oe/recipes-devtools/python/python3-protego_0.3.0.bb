SUMMARY  = "Protego is a pure-Python robots.txt parser with support for modern conventions."
HOMEPAGE = "https://pypi.org/project/Protego/#description"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4d4c85a6e830a1da71d07eebd4017802"

PYPI_PACKAGE = "Protego"

SRC_URI[md5sum] = "28b1ca092bda685de782a2b22d77d95a"
SRC_URI[sha256sum] = "04228bffde4c6bcba31cf6529ba2cfd6e1b70808fdc1d2cb4301be6b28d6c568"

inherit pypi setuptools3_legacy

include ${PYTHON_PN}-package-split.inc
