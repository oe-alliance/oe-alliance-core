SUMMARY = "Basic inter-process locks"
HOMEPAGE = "https://github.com/zopefoundation/zc.lockfile"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=78ccb3640dc841e1baecb3e27a6966b2"
DEPENDS += "python3-setuptools-scm-native"

SRC_URI[md5sum] = "fb4e4d2cc652aac97805e516ea287bf5"
SRC_URI[sha256sum] = "d3ab0f53974296a806db3219b9191ba0e6d5cbbd1daa2e0d17208cb9b29d2102"

PYPI_PACKAGE = "zc_lockfile"

inherit pypi setuptools3

include python3-package-split.inc
