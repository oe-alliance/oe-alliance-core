SUMMARY = "Universal encoding detector for Python 2 and 3"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a6f89e2100d9b6cdffcea4f398e37343"

inherit pypi setuptools

# setup.py of chardet needs this.
DEPENDS += "${PYTHON_PN}-pytest-runner-native"

SRC_URI[md5sum] = "bc9a5603d8d0994b2d4cbf255f99e654"
SRC_URI[sha256sum] = "0d6f53a15db4120f2b08c94f11e7d93d2c911ee118b6b30a04ec3ee8310179fa"

BBCLASSEXTEND = "native nativesdk"

PACKAGES =+ "${PN}-cli"
RDEPENDS_${PN}-cli = "${PN} "
FILES_${PN}-cli += " \
    ${PYTHON_SITEPACKAGES_DIR}/chardet/cli \
"
RDEPENDS_${PN} += "${PYTHON_PN}-argparse"

RDEPENDS_${PN}_class-target += " \
    ${PYTHON_PN}-logging \
"

include python-package-split.inc
