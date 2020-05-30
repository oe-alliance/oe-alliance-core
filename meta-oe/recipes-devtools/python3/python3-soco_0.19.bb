SUMMARY = "SoCo (Sonos Controller) is a simple library to control Sonos speakers."
HOMEPAGE = "https://pypi.org/project/soco"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=07b0e2ca9ac77cd65cd4edf2e13367ea"

RDEPENDS_${PN} = "${PYTHON_PN}-requests"

SRC_URI = "https://files.pythonhosted.org/packages/26/c6/973220a9332982d9c37c0443ab98a03008d51cb39796b32309a58e977f89/soco-0.19.tar.gz"
SRC_URI[md5sum] = "285b6987c48e9c7b59dd7e1f208472c8"
SRC_URI[sha256sum] = "93e1f3de65c94199b7013a2b7098e0e697846621454a92495d2ac36d9050ec35"

S = "${WORKDIR}/soco-${PV}"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc

# Make clean requires sphinx which we don't have
CLEANBROKEN = "1"
