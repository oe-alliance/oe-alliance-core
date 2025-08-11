DESCRIPTION = "PlutoTV for E2"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "enigma2-plugin-systemplugins-serviceapp"

inherit gittag

S = "${WORKDIR}/git/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance/PlutoTV.git;protocol=https;branch=main"
