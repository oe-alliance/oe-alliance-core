SUMMARY = "Python interface to DBus notifications"
HOMEPAGE = "https://bitbucket.org/takluyver/pynotify2/src/master/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=71a2cbec11611051cab37ac4f58ff05b"

SRC_URI[md5sum] = "ffccaed9330787c7442b453f6520a474"
SRC_URI[sha256sum] = "33fa108d50c42f3cd3407cc437518ad3f6225d1bb237011f16393c9dd3ce197d"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
