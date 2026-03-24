SUMMARY = "SoCo (Sonos Controller) is a simple library to control Sonos speakers."
HOMEPAGE = "https://pypi.org/project/soco"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=07b0e2ca9ac77cd65cd4edf2e13367ea"

RDEPENDS:${PN} = "python3-requests"

SRC_URI[md5sum] = "b6b1591941b7c516c371f037c1ac299a"
SRC_URI[sha256sum] = "850a26481cb924dfb956fd020552c7df2bab3b321513101d6a804c24a598fa26"

inherit pypi setuptools3

include python3-package-split.inc

# Make clean requires sphinx which we don't have
CLEANBROKEN = "1"
