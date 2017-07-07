SUMMARY = "sabyenc - yEnc Module for Python modified for SABnzbd"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=921c00bb1b9a4ae722e356f1f7ac1258"

PR = "r0"

SRC_URI = "https://files.pythonhosted.org/packages/source/s/sabyenc/sabyenc-${PV}.tar.gz"

SRC_URI[md5sum] = "56b22cb1e26fda8a5fd40ce4b2ae865c"
SRC_URI[sha256sum] = "05a848ebf7e37775171f85d2b01e131af7384a35fedece73079ca52a2d687eaf"

S = "${WORKDIR}/sabyenc-${PV}"

inherit setuptools

include python-package-split.inc
