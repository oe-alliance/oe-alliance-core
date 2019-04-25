SUMMARY = "Module for manipulating ID3 (v1 + v2) tags in Python"
SECTION = "devel/python"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "python"
RDEPENDS_${PN} = "python-shell"

SRC_URI = "https://files.pythonhosted.org/packages/30/4c/5ad1a6e1ccbcfaf6462db727989c302d9d721beedd9b09c11e6f0c7065b0/mutagen-1.42.0.tar.gz"

SRC_URI[md5sum] = "3729218f974c3a79ee9972ffa5ca5d12"
SRC_URI[sha256sum] = "bb61e2456f59a9a4a259fbc08def6d01ba45a42da8eeaa97d00633b0ec5de71c"

S = "${WORKDIR}/mutagen-${PV}"

inherit distutils

include python-package-split.inc
