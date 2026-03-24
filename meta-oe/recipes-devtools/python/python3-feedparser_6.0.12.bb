SUMMARY = "Universal feed parser, handles RSS 0.9x, RSS 1.0, RSS 2.0, CDF, Atom 0.3, and Atom 1.0 feeds"
HOMEPAGE = "https://feedparser.readthedocs.io/en/latest/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1b8df8e9646d66df8f19d9b646bd1270"

SRC_URI[md5sum] = "5f3c26e0f17ce73c31f875cdb4324b66"
SRC_URI[sha256sum] = "64f76ce90ae3e8ef5d1ede0f8d3b50ce26bcce71dd8ae5e82b1cd2d4a5f94228"

inherit pypi setuptools3

include python3-package-split.inc
