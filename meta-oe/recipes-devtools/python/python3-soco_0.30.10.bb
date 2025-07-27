SUMMARY = "SoCo (Sonos Controller) is a simple library to control Sonos speakers."
HOMEPAGE = "https://pypi.org/project/soco"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=07b0e2ca9ac77cd65cd4edf2e13367ea"

RDEPENDS:${PN} = "python3-requests"

SRC_URI[md5sum] = "770929f66be76dcbae363ac97238ff6e"
SRC_URI[sha256sum] = "a9c8ddb53836d18a0bbb881224cc6818e1ef1b28791637378ab25ff1eb1a87c3"

inherit pypi setuptools3

include python3-package-split.inc

# Make clean requires sphinx which we don't have
CLEANBROKEN = "1"
