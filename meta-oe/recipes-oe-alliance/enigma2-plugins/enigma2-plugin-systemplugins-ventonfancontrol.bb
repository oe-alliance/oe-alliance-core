DESCRIPTION = "Fancontrol Settings inStandby Mode"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PN = "enigma2-plugin-systemplugins-fancontrol"

COMPATIBLE_MACHINE = "^(inihde|inihde2|inihdp|inihdx)$"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/VentonFancontrol.git;protocol=https;branch=main"
