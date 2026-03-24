SUMMARY = "ISO country, subdivision, language, currency and script definitions and their translations"
HOMEPAGE = ""
AUTHOR = "Christian Theune <ct@flyingcircus.io>"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=8cf1799061bb2f68625332ab7039e11f"

include python3-package-split.inc

inherit pypi python_poetry_core

SRC_URI[md5sum] = "06db682b3f2b43db29e96291f9bcefa6"
SRC_URI[sha256sum] = "5b6027d453fcd6060112b951dd010f01f168b51b4bf8a1f1fc8c95c8d94a0801"
