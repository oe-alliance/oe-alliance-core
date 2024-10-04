SUMMARY = "CherryPy is a pythonic, object-oriented HTTP framework"
HOMEPAGE = "https://cheroot.cherrypy.org/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=a5ad8f932e1fd3841133f20d3ffedda1"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"
RDEPENDS:${PN} = "${PYTHON_PN}-jaraco.collections ${PYTHON_PN}-jaraco.classes ${PYTHON_PN}-jaraco.text ${PYTHON_PN}-jaraco.functools ${PYTHON_PN}-zc.lockfile ${PYTHON_PN}-tempora"

SRC_URI[md5sum] = "3f7cf1b729467945218bf553f7232da8"
SRC_URI[sha256sum] = "6c70e78ee11300e8b21c0767c542ae6b102a49cac5cfd4e3e313d7bb907c5891"

inherit pypi setuptools3_legacy

include ${PYTHON_PN}-package-split.inc
