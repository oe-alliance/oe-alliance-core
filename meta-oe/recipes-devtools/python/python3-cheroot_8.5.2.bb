SUMMARY = "Cheroot is the high-performance, pure-Python HTTP server used by CherryPy"
HOMEPAGE = "https://cheroot.cherrypy.org/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=53e455722e37d6acfe57bf370663edb5"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native ${PYTHON_PN}-setuptools-scm-git-archive-native"

SRC_URI[md5sum] = "cd2ca8ee46476839817d95441a1a740c"
SRC_URI[sha256sum] = "f137d03fd5155b1364bea557a7c98168665c239f6c8cedd8f80e81cdfac01567"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
