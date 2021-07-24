SUMMARY = "yEnc Module for Python3 modified for SABnzbd"
HOMEPAGE = "https://github.com/sabnzbd/sabnzbd-yenc"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=68dcf3baa84062cbac5a5b06aa406816"

SRC_URI[md5sum] = "a741209df7dd80247a40143b6df91149"
SRC_URI[sha256sum] = "dfaa0bdd01752a9cfde0d349a8f4e178b04b1cf9c1bc018b287961192cd2bb90"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
