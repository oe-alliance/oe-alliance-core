SUMMARY = "yEnc module for Python"
SECTION = "devel/python"
DEPENDS = "python"
PRIORITY = "optional"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=5858eb949cc6db7a2879a5eb38b3423a"

SRC_URI = "https://files.pythonhosted.org/packages/77/ff/3b6c82f8df69c993b7155b7ed933073266802ca6ee6be57ca10f26791079/yenc-${PV}.tar.gz"

S = "${WORKDIR}/yenc-${PV}"

inherit setuptools

SRC_URI[md5sum] = "a54bc3ba4b1499de9426d704c73f34ad"
SRC_URI[sha256sum] = "2c24a249d627af8ce1ca5a4b5bd237a34b6c859f71f55f0b09ed369f749fd4d9"

include python-package-split.inc
