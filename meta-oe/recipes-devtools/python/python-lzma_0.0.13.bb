SUMMARY  = "Backport of Python 3.3's 'lzma' module for XZ/LZMA compressed files"
HOMEPAGE = "https://github.com/peterjc/backports.lzma"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=d975688c6dbea8fc9be250a01d0c0209"

DEPENDS = "xz"

SRC_URI = " \
    https://files.pythonhosted.org/packages/5a/f3/7333f6797a72ad480e06c74c3be5498237617de6019469e25c320d2ccb38/backports.lzma-${PV}.tar.gz \
    file://0001-setup.py-Remove-usr-local-references-for-cross-compi.patch"

S = "${WORKDIR}/backports.lzma-${PV}"

inherit setuptools

SRC_URI[md5sum] = "b9f7d8e17729090adba6cac3f7d348aa"
SRC_URI[sha256sum] = "50829db66f0445442f6c796bba0ca62d1f87f54760c4682b6d1489e729a43744"

include python-package-split.inc
