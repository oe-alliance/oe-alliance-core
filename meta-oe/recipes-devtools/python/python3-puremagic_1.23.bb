SUMMARY = "Pure python implementation of magic file detection"
HOMEPAGE = "https://github.com/cdgriffith/puremagic"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1f89f26c6b96eb604330943284cc7bb7"

SRC_URI[md5sum] = "7f534406e4004bd1b37b76e88f1ab1a3"
SRC_URI[sha256sum] = "e0bb7dc814b9d606225b57d4d49175d27c24fb745de1a7b3506067f2be54438f"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
