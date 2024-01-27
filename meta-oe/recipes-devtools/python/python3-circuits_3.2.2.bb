SUMMARY = "Asynchronous Component based Event Application Framework"
HOMEPAGE = "http://circuitsframework.com/"
AUTHOR = "James Mills <prologic@shortcircuit.net.au>"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b209f6edbb40680bdf62b70a7c097101"

DEPENDS = "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "e6f4b1a29d129a135da8edc6ce624eb0"
SRC_URI[sha256sum] = "ce069ef42c02299a485e6871b19d7cddc9ed36f0e018869a09ad53b734d0044e"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
