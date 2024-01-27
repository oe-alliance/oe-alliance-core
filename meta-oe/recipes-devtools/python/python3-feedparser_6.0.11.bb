SUMMARY = "Universal feed parser, handles RSS 0.9x, RSS 1.0, RSS 2.0, CDF, Atom 0.3, and Atom 1.0 feeds"
HOMEPAGE = "https://feedparser.readthedocs.io/en/latest/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=62b06546e31ac3ab18cf54be0a1aa5ec"

SRC_URI[md5sum] = "47be8c47a93fff8994d50e7e1f1dd97e"
SRC_URI[sha256sum] = "c9d0407b64c6f2a065d0ebb292c2b35c01050cc0dc33757461aaabdc4c4184d5"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
