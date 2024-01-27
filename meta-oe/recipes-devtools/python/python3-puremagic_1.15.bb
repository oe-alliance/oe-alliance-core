SUMMARY = "Pure python implementation of magic file detection"
HOMEPAGE = "https://github.com/cdgriffith/puremagic"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d0ddee7e1e04d50fd2600c9e3092e4fa"

SRC_URI[md5sum] = "421f523ec7be0c7cdb3cf600f6dfaae6"
SRC_URI[sha256sum] = "6e46aa78113a466abc9f69e6e8a4ce90eb57d908dafb809597012621061462bd"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
