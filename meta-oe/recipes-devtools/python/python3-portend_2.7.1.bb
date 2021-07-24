SUMMARY = "TCP port monitoring and discovery"
HOMEPAGE = "https://cheroot.cherrypy.org/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "b6dd535b35c1838fb1552ce19910a5d7"
SRC_URI[sha256sum] = "986ed9a278e64a87b5b5f4c21e61c25bebdce9919a92238d9c14c37a7416482b"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
