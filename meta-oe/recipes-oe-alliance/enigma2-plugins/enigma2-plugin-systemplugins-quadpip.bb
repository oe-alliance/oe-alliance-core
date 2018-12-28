DESCRIPTION = "QuadPiP plugin for supported receivers"

require conf/license/license-gplv2.inc

inherit gitpkgv distutils-openplugins

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r5"

S = "${WORKDIR}/git"
SRC_URI = "git://github.com/OpenPLi/enigma2-plugin-systemplugins-quadpip.git;protocol=http"
