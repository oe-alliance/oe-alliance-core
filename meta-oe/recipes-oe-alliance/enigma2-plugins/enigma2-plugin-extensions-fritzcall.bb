DESCRIPTION = "Show Incoming and Outgoing Calls on your TV Screen with a Fritz!Box"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "python3-twisted-web python3-html"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/FritzCall.git;protocol=https;branch=main"
