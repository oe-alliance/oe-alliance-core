SUMMARY = "sabyenc - yEnc Module for Python modified for SABnzbd"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=a38453d7b2ad2ce4cc0f83cc390ee0de"

PR = "r0"

SRC_URI = "https://files.pythonhosted.org/packages/source/s/sabyenc/sabyenc-${PV}.tar.gz"

SRC_URI[md5sum] = "7163fa97ff1c367899728e3c23805d14"
SRC_URI[sha256sum] = "0381c9e2755be00d8a88635415e212a6ee0a03a02c3e576274278a005c689052"

S = "${WORKDIR}/sabyenc-${PV}"

inherit setuptools

include python-package-split.inc
