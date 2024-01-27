SUMMARY = "Cheroot is the high-performance, pure-Python HTTP server used by CherryPy"
HOMEPAGE = "https://cheroot.cherrypy.org/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=c4e17b64eab9c128f786f44f0dfb570a"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native ${PYTHON_PN}-setuptools-scm-git-archive-native"

SRC_URI[md5sum] = "be96fa052c54892240f916fbb06cc571"
SRC_URI[sha256sum] = "59c4a1877fef9969b3c3c080caaaf377e2780919437853fc0d32a9df40b311f0"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
