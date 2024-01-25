SUMMARY = "functools.singledispatch from Python 3.4"
DESCRIPTION = "PEP 443 proposed to expose a mechanism in the functools standard library module \
in Python 3.4 that provides a simple form of generic programming known as single-dispatch \
generic functions.  This library is a backport of this functionality to Python 2.6 - 3.3"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.rst;md5=e3d2f2ef3ab5aabf960835703217d8d5"

DEPENDS += " ${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "4bf163cc626caa9450856a370815c65e"
SRC_URI[sha256sum] = "f3430b886d5b4213d07d715096a75da5e4a8105284c497b9aee6d6d48bfe90cb"

inherit pypi python_setuptools_build_meta
