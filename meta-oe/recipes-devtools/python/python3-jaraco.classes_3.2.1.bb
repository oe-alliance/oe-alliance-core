SUMMARY = "Utility functions for Python class constructs"
HOMEPAGE = "https://github.com/jaraco/jaraco.classes"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "4d306b26c6856fb30b9ec22dc87e383b"
SRC_URI[sha256sum] = "ed54b728af1937dc16b7236fbaf34ba561ba1ace572b03fffa5486ed363ecf34"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
