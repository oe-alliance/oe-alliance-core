SUMMARY = "TCP port monitoring and discovery"
HOMEPAGE = "https://cheroot.cherrypy.org/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "0c3a2e8073c384b826f87795766f1e0e"
SRC_URI[sha256sum] = "5250a352c19c959d767cac878b829d93e5dc7625a5143399a2a00dc6628ffb72"

inherit pypi python_setuptools_build_meta

include ${PYTHON_PN}-package-split.inc
