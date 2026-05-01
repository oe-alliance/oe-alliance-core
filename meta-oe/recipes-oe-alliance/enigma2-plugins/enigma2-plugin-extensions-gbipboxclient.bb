DESCRIPTION = "GBIpboxClient"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "cifs-utils"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/GBIpboxClient.git;protocol=https;branch=main"

