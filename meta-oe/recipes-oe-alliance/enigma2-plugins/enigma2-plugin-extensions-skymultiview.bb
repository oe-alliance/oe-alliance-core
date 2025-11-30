DESCRIPTION = "SkyMultiview for E2"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc


inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance/SkyMultiview.git;protocol=https;branch=main"
