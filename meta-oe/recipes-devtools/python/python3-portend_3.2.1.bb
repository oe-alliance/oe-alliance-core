SUMMARY = "TCP port monitoring and discovery"
HOMEPAGE = "https://cheroot.cherrypy.org/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1aeae65f25a15b1e46d4381f2f094e0a"
DEPENDS += "python3-setuptools-scm-native python3-coherent-licensed-native"

SRC_URI[md5sum] = "c52510e0488592005243209246b1d4f9"
SRC_URI[sha256sum] = "aa9d40ab1f9e14bdb7d401f42210df35d017c9b97991baeb18568cedfb8c6489"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
