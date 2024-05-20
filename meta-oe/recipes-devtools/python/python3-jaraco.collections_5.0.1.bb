SUMMARY = "Collection objects similar to those in stdlib by jaraco"
HOMEPAGE = "https://github.com/jaraco/jaraco.collections"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "c04e0e8f98081467f4fdf1c407c22aec"
SRC_URI[sha256sum] = "808631b174b84a4e2a592490d62f62dfc15d8047a0f715726098dc43b81a6cfa"

inherit pypi python_setuptools_build_meta

include ${PYTHON_PN}-package-split.inc
