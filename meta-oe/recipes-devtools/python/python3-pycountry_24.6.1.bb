SUMMARY = "ISO country, subdivision, language, currency and script definitions and their translations"
HOMEPAGE = ""
AUTHOR = "Christian Theune <ct@flyingcircus.io>"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=8cf1799061bb2f68625332ab7039e11f"

include python3-package-split.inc

inherit pypi python_poetry_core

SRC_URI[md5sum] = "61111cc27f09e44aa3bace260a8b3089"
SRC_URI[sha256sum] = "b61b3faccea67f87d10c1f2b0fc0be714409e8fcdcc1315613174f6466c10221"
