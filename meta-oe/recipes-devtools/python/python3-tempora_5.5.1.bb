SUMMARY = "Objects and routines pertaining to date and time (tempora)"
HOMEPAGE = "https://github.com/jaraco/tempora"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "a904fca738bc16b31f28472379987e6a"
SRC_URI[sha256sum] = "a2bb51e2121976d931347b3e433917c364b83fdd5f64ef27336c865bf1fb0f75"

inherit pypi python_setuptools_build_meta

include ${PYTHON_PN}-package-split.inc
