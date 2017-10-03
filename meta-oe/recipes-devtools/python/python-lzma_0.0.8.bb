SUMMARY  = "Backport of Python 3.3's 'lzma' module for XZ/LZMA compressed files"
HOMEPAGE = "https://github.com/peterjc/backports.lzma"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=9f9c26dff09368a1c71a3dbfb70703f0"

DEPENDS = "xz"

SRC_URI = "\
	https://pypi.python.org/packages/ab/dc/6adfe7271ca096f24c25817cf3f68f7d2df9e8b3f54c03f82caf7388f103/backports.lzma-${PV}.tar.gz \
	file://0001-setup.py-Remove-usr-local-references-for-cross-compi.patch"

S = "${WORKDIR}/backports.lzma-${PV}"

inherit setuptools

SRC_URI[md5sum] = "49b626f282be13acfc0fe2bc24732b3d"

include python-package-split.inc
