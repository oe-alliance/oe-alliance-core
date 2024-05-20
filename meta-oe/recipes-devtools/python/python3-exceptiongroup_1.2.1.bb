SUMMARY = "Backport of PEP 654 (exception groups)"
HOMEPAGE = "https://github.com/agronholm/exceptiongroup"
AUTHOR = "Alex Grönholm <alex.gronholm@nextday.fi>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d5caa317463c433575efff1d2fe206d7"

DEPENDS += "${PYTHON_PN}-flit-scm-native"

PYPI_PACKAGE = "exceptiongroup"

inherit pypi python_setuptools_build_meta

SRC_URI[sha256sum] = "a4785e48b045528f5bfe627b6ad554ff32def154f42372786903b7abcfe1aa16"

BBCLASSEXTEND = "native nativesdk"

include ${PYTHON_PN}-package-split.inc
