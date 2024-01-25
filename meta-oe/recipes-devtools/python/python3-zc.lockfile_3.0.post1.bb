SUMMARY = "Basic inter-process locks"
HOMEPAGE = "https://github.com/zopefoundation/zc.lockfile"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=78ccb3640dc841e1baecb3e27a6966b2"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "5e902492de505a0f98e49b1e31cf2bc2"
SRC_URI[sha256sum] = "adb2ee6d9e6a2333c91178dcb2c9b96a5744c78edb7712dc784a7d75648e81ec"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
