SUMMARY = "functools.singledispatch from Python 3.4"
DESCRIPTION = "PEP 443 proposed to expose a mechanism in the functools standard library module \
in Python 3.4 that provides a simple form of generic programming known as single-dispatch \
generic functions.  This library is a backport of this functionality to Python 2.6 - 3.3"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.rst;md5=709e842c0ab9a9626afcdb026b1c837a"

DEPENDS += " python3-setuptools-scm-native"

SRC_URI[md5sum] = "bb7892b76047a9a18d8d18051b5e8b02"
SRC_URI[sha256sum] = "f200caabe9ddf6e3072332f51ebd4e6780bec24fc265291fae9298af07705ce8"

inherit pypi python_setuptools_build_meta
