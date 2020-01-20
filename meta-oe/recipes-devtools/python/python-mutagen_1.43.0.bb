SUMMARY = "Module for manipulating ID3 (v1 + v2) tags in Python"
SECTION = "devel/python"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "python python-setuptools-scm-native"
RDEPENDS_${PN} = "python-shell"

SRC_URI = "https://files.pythonhosted.org/packages/aa/fd/ed738775442e3614849fefdf41417d7bff3ccb010d49c8f729432cc3c1e5/mutagen-${PV}.tar.gz"
SRC_URI[md5sum] = "3154511015860685b3882c068258ae65"
SRC_URI[sha256sum] = "3a982d39f1b800520a32afdebe3543f972e83a6ddd0c0198739a161ee705b588"

S = "${WORKDIR}/mutagen-${PV}"

inherit distutils

include python-package-split.inc
