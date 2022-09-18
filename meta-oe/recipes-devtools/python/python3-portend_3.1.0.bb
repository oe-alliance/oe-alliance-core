SUMMARY = "TCP port monitoring and discovery"
HOMEPAGE = "https://cheroot.cherrypy.org/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "c37d676837505a6fc8dd33185c6d62a7"
SRC_URI[sha256sum] = "239e3116045ea823f6df87d6168107ad75ccc0590e37242af0cc1e98c5d224e4"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
