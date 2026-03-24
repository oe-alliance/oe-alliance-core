SUMMARY = "Backport of PEP 654 (exception groups)"
HOMEPAGE = "https://github.com/agronholm/exceptiongroup"
AUTHOR = "Alex Grönholm <alex.gronholm@nextday.fi>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d5caa317463c433575efff1d2fe206d7"

DEPENDS += "python3-flit-scm-native"

PYPI_PACKAGE = "exceptiongroup"

inherit pypi python_setuptools_build_meta

SRC_URI[md5sum] = "f88685ec75c5715111d65aeae28c9322"
SRC_URI[sha256sum] = "8b412432c6055b0b7d14c310000ae93352ed6754f70fa8f7c34141f91c4e3219"

BBCLASSEXTEND = "native nativesdk"

include python3-package-split.inc
