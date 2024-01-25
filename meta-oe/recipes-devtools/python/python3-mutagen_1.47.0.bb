SUMMARY = "Module for manipulating ID3 (v1 + v2) tags in Python"
SECTION = "devel/python"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "${PYTHON_PN} ${PYTHON_PN}-setuptools-scm-native"
RDEPENDS:${PN} = "${PYTHON_PN}-shell"

SRC_URI[md5sum] = "aa2d0d73e71c5daa1a730f7b94272357"
SRC_URI[sha256sum] = "719fadef0a978c31b4cf3c956261b3c58b6948b32023078a2117b1de09f0fc99"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
