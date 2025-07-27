SUMMARY = "Objects and routines pertaining to date and time (tempora)"
HOMEPAGE = "https://github.com/jaraco/tempora"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1aeae65f25a15b1e46d4381f2f094e0a"
DEPENDS += "python3-setuptools-scm-native python3-coherent-licensed-native"

SRC_URI[md5sum] = "208d59bfe14a913199d3e284c56f195c"
SRC_URI[sha256sum] = "abb5d9ec790cc5e4f9431778029ba3e3d9ba9bd50cb306dad824824b2b362dcd"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
