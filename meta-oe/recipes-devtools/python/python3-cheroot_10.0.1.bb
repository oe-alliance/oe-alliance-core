SUMMARY = "Cheroot is the high-performance, pure-Python HTTP server used by CherryPy"
HOMEPAGE = "https://cheroot.cherrypy.org/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=c4e17b64eab9c128f786f44f0dfb570a"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native ${PYTHON_PN}-setuptools-scm-git-archive-native"

SRC_URI[md5sum] = "0fce2608fcb31c16c81945c73e283724"
SRC_URI[sha256sum] = "e0b82f797658d26b8613ec8eb563c3b08e6bd6a7921e9d5089bd1175ad1b1740"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
