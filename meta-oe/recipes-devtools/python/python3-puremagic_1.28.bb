SUMMARY = "Pure python implementation of magic file detection"
HOMEPAGE = "https://github.com/cdgriffith/puremagic"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1f89f26c6b96eb604330943284cc7bb7"

SRC_URI[md5sum] = "de0256a7110744de7f2a3528e964a0ab"
SRC_URI[sha256sum] = "195893fc129657f611b86b959aab337207d6df7f25372209269ed9e303c1a8c0"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
