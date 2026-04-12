DESCRIPTION = "timFS Custom Menu"
Maintainer = "shadowrider plugins@fs-plugins.de"
Homepage = "www.fs-plugins.de"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/timFS.git;protocol=https;branch=main"
