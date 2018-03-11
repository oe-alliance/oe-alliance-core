SUMMARY = "SoCo (Sonos Controller) is a simple library to control Sonos speakers."
HOMEPAGE = "https://pypi.python.org/pypi/soco"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=07b0e2ca9ac77cd65cd4edf2e13367ea"

RDEPENDS_${PN} = "python-requests"

SRC_URI = "https://pypi.python.org/packages/b7/68/6ff725e61dd4cbd85d876b5e646b752ed04945e93725be0686a5d55ed3b9/soco-${PV}.tar.gz"

SRC_URI[md5sum] = "701990416213d98b77452b1f81409b85"
SRC_URI[sha256sum] = "d666186f9c69db8b764c1101dba78621698a09eff6ee89d3f4152ea377184907"

S = "${WORKDIR}/soco-${PV}"

inherit setuptools

include python-package-split.inc
