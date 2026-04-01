DESCRIPTION = "Fancontrol Settings inStandby Mode"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PN = "enigma2-plugin-systemplugins-fancontrol"

COMPATIBLE_MACHINE = "^(et5x00|et6x00|et9x00|et1x000|et7x00|et8500|et13000|et4x00|et8000|et10000|hd2400|odinm7|odinm9)$"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/XtrendFancontrol.git;protocol=https;branch=main"
