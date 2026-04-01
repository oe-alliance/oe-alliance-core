DESCRIPTION = "Change Remote Control Code, Switch ET5000 ET9000 DMM remote"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

PN = "enigma2-plugin-systemplugins-remotecontrolcode"

COMPATIBLE_MACHINE = "^(et5x00|et6x00|et9x00|et1x000|et7x00|et8500|et13000|et4x00|et8000|et10000|hd1100|hd1200|hd1265|hd500c|hd530c|hd2400|xp1000)$"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/XtrendRemote.git;protocol=https;branch=main"
