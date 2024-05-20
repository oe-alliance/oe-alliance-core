SUMMARY = "SoCo (Sonos Controller) is a simple library to control Sonos speakers."
HOMEPAGE = "https://pypi.org/project/soco"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=07b0e2ca9ac77cd65cd4edf2e13367ea"

RDEPENDS:${PN} = "${PYTHON_PN}-requests"

SRC_URI[md5sum] = "12eed173bcb94e08d10379fe14cdbef1"
SRC_URI[sha256sum] = "97c77ad353f8233117659250c71113419d288bc5447148c6bd4a2486e9cfd3be"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc

# Make clean requires sphinx which we don't have
CLEANBROKEN = "1"
