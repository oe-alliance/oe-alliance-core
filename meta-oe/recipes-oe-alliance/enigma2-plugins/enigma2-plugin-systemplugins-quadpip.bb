DESCRIPTION = "QuadPiP plugin for supported receivers"

require conf/license/license-gplv2.inc

inherit gitpkgv ${@bb.utils.contains("PYTHON_PN", "python", "distutils-openplugins", "distutils3-openplugins", d)}

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r6"

S = "${WORKDIR}/git"
SRC_URI = "git://github.com/oe-alliance/enigma2-plugin-systemplugins-quadpip.git;protocol=http"
