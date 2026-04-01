DESCRIPTION = "blindscan..."
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RRECOMMENDS:${PN} = "virtual-blindscan-dvbs"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/Blindscan.git;protocol=https;branch=main"
