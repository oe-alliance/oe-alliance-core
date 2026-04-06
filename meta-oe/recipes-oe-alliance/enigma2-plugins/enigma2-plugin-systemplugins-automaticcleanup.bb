DESCRIPTION = "Automatic System Cleanup (setting backups, orphaned movie files, timer entries)"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc


inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/AutomaticCleanup.git;protocol=https;branch=main"
