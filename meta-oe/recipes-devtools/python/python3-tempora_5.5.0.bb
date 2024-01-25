SUMMARY = "Objects and routines pertaining to date and time (tempora)"
HOMEPAGE = "https://github.com/jaraco/tempora"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "39314c5843bc3f40f8ce4f5cc340c479"
SRC_URI[sha256sum] = "13e4fcc997d0509c3306d6841f03e9381b7e5e46b2bebfae9151af90085f0c26"

inherit pypi python_setuptools_build_meta

include ${PYTHON_PN}-package-split.inc
