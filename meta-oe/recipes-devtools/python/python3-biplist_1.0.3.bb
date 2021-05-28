SUMMARY = "Binary Property List (plist) files provide a faster and smaller\
           serialization format for property lists on OS X. This is a\
           library for generating binary plists which can be read by OS X,\
           iOS, or other clients."
SECTION = "devel/python"
PRIORITY = "optional"
HOMEPAGE = "https://pypi.org/project/biplist"
LICENSE = "BSD"
require conf/license/license-gplv2.inc

SRCNAME = "biplist"

DEPENDS = "${PYTHON_PN}"

SRC_URI = " https://bitbucket.org/wooster/biplist/downloads/${SRCNAME}-${PV}.tar.gz"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools3

SRC_URI[md5sum] = "e2ff83d5f66e5798801d142eb7c1a07a"
SRC_URI[sha256sum] = "4c0549764c5fe50b28042ec21aa2e14fe1a2224e239a1dae77d9e7f3932aa4c6"


include ${PYTHON_PN}-package-split.inc
