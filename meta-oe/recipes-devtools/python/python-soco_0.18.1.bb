SUMMARY = "SoCo (Sonos Controller) is a simple library to control Sonos speakers."
HOMEPAGE = "https://pypi.org/project/soco"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=07b0e2ca9ac77cd65cd4edf2e13367ea"

RDEPENDS_${PN} = "python-requests"

SRC_URI = "https://files.pythonhosted.org/packages/27/8e/c9d2f3ca469cbcd9f0882e50cadf01176101c7edb92ac739ae3ac5c32298/soco-${PV}.tar.gz"
SRC_URI[md5sum] = "7a1435bcd55b51440f9b69bc5be29101"
SRC_URI[sha256sum] = "de033ad69f86a655f50d407648b3aa2dd9647c69fd7bb317e9adfcd38a1adf4b"

S = "${WORKDIR}/soco-${PV}"

inherit setuptools

include python-package-split.inc

# Make clean requires sphinx which we don't have
CLEANBROKEN = "1"
