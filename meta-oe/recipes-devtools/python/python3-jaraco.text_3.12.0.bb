SUMMARY = "Module for text manipulation"
HOMEPAGE = "https://github.com/jaraco/jaraco.text"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI:append = " file://0001-add-setup.py.patch"

SRC_URI[md5sum] = "9f63a5cdd56061ebd84d8175d1df3b70"
SRC_URI[sha256sum] = "389e25c8d4b32e9715bf530596fab0f5cd3aa47296e43969392e18a541af592c"

inherit pypi setuptools3


