SUMMARY = "SoCo (Sonos Controller) is a simple library to control Sonos speakers."
HOMEPAGE = "https://pypi.org/project/soco"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=07b0e2ca9ac77cd65cd4edf2e13367ea"

RDEPENDS:${PN} = "${PYTHON_PN}-requests"

SRC_URI[md5sum] = "f844ccf1db4a4532ef7a9433ad19fa01"
SRC_URI[sha256sum] = "328a6fc0653d9f9e4883fd59674fb73feb61dc273f7c1f0a8212e91103112cb5"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc

# Make clean requires sphinx which we don't have
CLEANBROKEN = "1"
