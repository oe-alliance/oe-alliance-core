SUMMARY = "Backport of PEP 654 (exception groups)"
HOMEPAGE = "https://github.com/agronholm/exceptiongroup"
AUTHOR = "Alex Grönholm <alex.gronholm@nextday.fi>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d5caa317463c433575efff1d2fe206d7"

DEPENDS += "${PYTHON_PN}-flit-scm-native"

PYPI_PACKAGE = "exceptiongroup"

inherit pypi python_setuptools_build_meta

SRC_URI[md5sum] = "e371f497eba3ca3fa6ac6f0dc2a08919"
SRC_URI[sha256sum] = "47c2edf7c6738fafb49fd34290706d1a1a2f4d1c6df275526b62cbb4aa5393cc"

BBCLASSEXTEND = "native nativesdk"

include ${PYTHON_PN}-package-split.inc
