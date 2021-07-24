SUMMARY = "GuessIt - a library for guessing information from video filenames."
HOMEPAGE = "https://doc.guessit.io/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=bb3ca60759f3202f1ae42e3519cd06bc"
DEPENDS += "${PYTHON_PN}-pytest-runner-native"

SRC_URI[md5sum] = "58a0576b5602760e68e984e333dced1c"
SRC_URI[sha256sum] = "8305e0086129614a8820a508303f98f56c584811489499bcc54a7ea6f1b0391e"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
