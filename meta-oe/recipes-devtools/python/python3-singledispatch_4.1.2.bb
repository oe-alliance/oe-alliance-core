SUMMARY = "functools.singledispatch from Python 3.4"
DESCRIPTION = "PEP 443 proposed to expose a mechanism in the functools standard library module \
in Python 3.4 that provides a simple form of generic programming known as single-dispatch \
generic functions.  This library is a backport of this functionality to Python 2.6 - 3.3"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.rst;md5=3d63ce9e2827f94391b1dcb1ff53b3ac"

DEPENDS += " python3-setuptools-scm-native python3-coherent-licensed-native"

SRC_URI[md5sum] = "79cc273003ca494f442d3b04d450de6c"
SRC_URI[sha256sum] = "0ad18e5a55c54a836a695a4e7c12faa05adfe88c7eeb64dcd331a4b13934c3b6"

inherit pypi python_setuptools_build_meta
