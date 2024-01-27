SUMMARY = "ISO country, subdivision, language, currency and script definitions and their translations"
HOMEPAGE = ""
AUTHOR = "Christian Theune <ct@flyingcircus.io>"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=8cf1799061bb2f68625332ab7039e11f"

include python3-package-split.inc

inherit pypi python_poetry_core

SRC_URI = "file://version.patch"

SRC_URI[md5sum] = "1bec84be647e68b3ef5f6ef8784bdce0"
SRC_URI[sha256sum] = "00569d82eaefbc6a490a311bfa84a9c571cff9ddbf8b0a4f4e7b4f868b4ad925"
