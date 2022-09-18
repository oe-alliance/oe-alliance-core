SUMMARY = "Objects and routines pertaining to date and time (tempora)"
HOMEPAGE = "https://github.com/jaraco/tempora"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI:append = " file://0001-add-setup.py.patch"

SRC_URI[md5sum] = "24b77c173a7e89084004a38fe44e3ce3"
SRC_URI[sha256sum] = "31fa5bb33b2641026211f23e808eb8bd351901988b167d45f323c8f450ecf211"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
