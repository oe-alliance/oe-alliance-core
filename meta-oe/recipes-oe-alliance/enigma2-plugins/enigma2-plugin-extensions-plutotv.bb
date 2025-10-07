DESCRIPTION = "PlutoTV for E2"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "enigma2-plugin-systemplugins-serviceapp"

inherit gitpkgv

S = "${WORKDIR}/git/src"

SRCREV = "${AUTOREV}"
PV = "20250802+git"
PKGV = "20250802+git${GITPKGV}"
PR = "r0"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance/PlutoTV.git;protocol=https;branch=20250802"
