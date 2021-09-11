SUMMARY = "Module for manipulating ID3 (v1 + v2) tags in Python"
SECTION = "devel/python"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "python python-setuptools-scm-native"
RDEPENDS_${PN} = "python-shell"

SRC_URI = "https://files.pythonhosted.org/packages/f4/a2/af03414b37e2e3c1fcdfadfbc692652198767c5d822ad3a3b3a949bac86d/mutagen-${PV}.tar.gz"
SRC_URI[md5sum] = "90e55bbd35517c2c93859f2d922969bd"
SRC_URI[sha256sum] = "d873baeb7815311d3420aab0a1d83f050f628228cbc2d6045a14a16460411bc9"

S = "${WORKDIR}/mutagen-${PV}"

inherit distutils

include python-package-split.inc
