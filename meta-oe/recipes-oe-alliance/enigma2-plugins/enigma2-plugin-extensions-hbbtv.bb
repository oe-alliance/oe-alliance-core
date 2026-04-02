DESCRIPTION = "HbbTV player"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "vuplus-opera-browser-util"
DEPENDS = "vuplus-opera-browser-util"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/HbbTV.git;protocol=https;branch=main"
