SUMMARY = "SoCo (Sonos Controller) is a simple library to control Sonos speakers."
HOMEPAGE = "https://pypi.org/project/soco"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=07b0e2ca9ac77cd65cd4edf2e13367ea"

RDEPENDS:${PN} = "python3-requests"

SRC_URI[md5sum] = "dd3d516f93b2da3b2aa89ab1bd31d769"
SRC_URI[sha256sum] = "21f7a3b3f0e65aadfc90aaef69a5a428205597271b09c3d99bea8b5cb00df9da"

inherit pypi setuptools3

include python3-package-split.inc

# Make clean requires sphinx which we don't have
CLEANBROKEN = "1"
