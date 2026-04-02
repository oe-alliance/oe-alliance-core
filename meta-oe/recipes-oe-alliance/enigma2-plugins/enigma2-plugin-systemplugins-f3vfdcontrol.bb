DESCRIPTION = "vfd controller"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PN = "enigma2-plugin-systemplugins-vfdcontrol"

COMPATIBLE_MACHINE = "^(g100|g101|hd51|et1x000|hd1100|hd1200|hd1265|hd1500|hd500c|hd530c|formuler3|formuler4|formuler3ip|formuler4ip|formuler4turbo|tiviarmin|vg2000|vg5000|jj7362|7220s|7225s|7300s|7400s|sh1|h3|h5|h7|h17|lc|vs1000)$"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/F3LEDControl.git;protocol=https;branch=main"
