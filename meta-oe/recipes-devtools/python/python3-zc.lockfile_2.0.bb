SUMMARY = "Basic inter-process locks"
HOMEPAGE = "https://github.com/zopefoundation/zc.lockfile"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=78ccb3640dc841e1baecb3e27a6966b2"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "3895445752278ddcc4578658c3c9a492"
SRC_URI[sha256sum] = "307ad78227e48be260e64896ec8886edc7eae22d8ec53e4d528ab5537a83203b"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
