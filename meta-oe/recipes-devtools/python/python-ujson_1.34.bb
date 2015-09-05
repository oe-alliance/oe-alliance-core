SUMMARY  = "Ultra fast JSON encoder and decoder for Python"
DESCRIPTION = "UltraJSON is an ultra fast JSON encoder and decoder written in pure C with bindings for Python 2.5+ and 3."
HOMEPAGE = "https://pypi.python.org/pypi/ujson"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=cbb7d7d409df43b6a129e9c06d33f931"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.34+git${SRCPV}"
PKGV = "1.34+git${GITPKGV}"
VER ="1.34"
PR = "r0"

SRC_URI = "git://github.com/esnme/ultrajson.git"

S = "${WORKDIR}/git"

inherit setuptools

RDEPENDS_${PN} += "python-numbers"

include python-package-split.inc
