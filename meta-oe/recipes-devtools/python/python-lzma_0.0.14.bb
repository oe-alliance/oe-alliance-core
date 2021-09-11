SUMMARY  = "Backport of Python 3.3's 'lzma' module for XZ/LZMA compressed files"
HOMEPAGE = "https://github.com/peterjc/backports.lzma"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=33789ba23611cb59ce83140837f87508"

DEPENDS = "xz"

SRC_URI = " \
    https://files.pythonhosted.org/packages/21/0f/1a9990233076d48aa2084100ba289ca162975e73a688f3a56c0ee2bb441a/backports.lzma-${PV}.tar.gz \
    file://0001-setup.py-Remove-usr-local-references-for-cross-compi.patch"

S = "${WORKDIR}/backports.lzma-${PV}"

inherit setuptools

SRC_URI[md5sum] = "ce194b48429f51778e2c391ca4fd1fd4"
SRC_URI[sha256sum] = "16d8b68e4d3cd4e6c9ddb059850452946da3914c8a8e197a7f2b0954559f2df4"

include python-package-split.inc
