DESCRIPTION = "QuadPiP plugin for supported receivers"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv setuptools3-openplugins

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"
PR = "r7"

SRC_URI = "git://github.com/oe-alliance/enigma2-plugin-systemplugins-quadpip.git;protocol=https;branch=master"
