SUMMARY = "Objects and routines pertaining to date and time (tempora)"
HOMEPAGE = "https://github.com/jaraco/tempora"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "e4ca48a06f4c8b9aa21b8081937353f2"
SRC_URI[sha256sum] = "888190a2dbe3255ff26dfa9fcecb25f4d38434c0f1943cd61de98bb41c410c50"

inherit pypi python_setuptools_build_meta

include ${PYTHON_PN}-package-split.inc
