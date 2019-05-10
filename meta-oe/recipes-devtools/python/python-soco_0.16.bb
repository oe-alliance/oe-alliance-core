SUMMARY = "SoCo (Sonos Controller) is a simple library to control Sonos speakers."
HOMEPAGE = "https://pypi.org/project/soco"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=07b0e2ca9ac77cd65cd4edf2e13367ea"

RDEPENDS_${PN} = "python-requests"

SRC_URI = "https://files.pythonhosted.org/packages/60/4b/ecbf9a4fb4c0042fb77e782476d961dfd9c761fa617b9d8883a0a16785ea/soco-${PV}.tar.gz"

SRC_URI[md5sum] = "459c5800f7416cab0e4389b2d574eab5"
SRC_URI[sha256sum] = "7bed4475e3f134283af1f520a9b2e6ce2a8e69bdc1b58ee68528b3d093972424"

S = "${WORKDIR}/soco-${PV}"

inherit setuptools

include python-package-split.inc

# Make clean requires sphinx which we don't have
CLEANBROKEN = "1"
