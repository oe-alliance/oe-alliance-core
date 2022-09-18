SUMMARY = "Cheroot is the high-performance, pure-Python HTTP server used by CherryPy"
HOMEPAGE = "https://cheroot.cherrypy.org/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=c4e17b64eab9c128f786f44f0dfb570a"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native ${PYTHON_PN}-setuptools-scm-git-archive-native"

SRC_URI[md5sum] = "70247d0948899f453b50e6181cddd0d7"
SRC_URI[sha256sum] = "366adf6e7cac9555486c2d1be6297993022eff6f8c4655c1443268cca3f08e25"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
