SUMMARY = "Universal feed parser, handles RSS 0.9x, RSS 1.0, RSS 2.0, CDF, Atom 0.3, and Atom 1.0 feeds"
HOMEPAGE = "https://feedparser.readthedocs.io/en/latest/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4dd024c5a568d41577d68c900f653171"

SRC_URI[md5sum] = "8d0ba773e049e8f1edc2541737593a92"
SRC_URI[sha256sum] = "5ce0410a05ab248c8c7cfca3a0ea2203968ee9ff4486067379af4827a59f9661"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
