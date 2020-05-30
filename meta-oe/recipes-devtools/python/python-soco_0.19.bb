SUMMARY = "SoCo (Sonos Controller) is a simple library to control Sonos speakers."
HOMEPAGE = "https://pypi.org/project/soco"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=07b0e2ca9ac77cd65cd4edf2e13367ea"

RDEPENDS_${PN} = "${PYTHON_PN}-requests"

SRC_URI = "https://files.pythonhosted.org/packages/27/8e/c9d2f3ca469cbcd9f0882e50cadf01176101c7edb92ac739ae3ac5c32298/soco-${PV}.tar.gz"
SRC_URI[md5sum] = "285b6987c48e9c7b59dd7e1f208472c8"
SRC_URI[sha256sum] = "93e1f3de65c94199b7013a2b7098e0e697846621454a92495d2ac36d9050ec35"

S = "${WORKDIR}/soco-${PV}"

inherit setuptools

include ${PYTHON_PN}-package-split.inc

# Make clean requires sphinx which we don't have
CLEANBROKEN = "1"
