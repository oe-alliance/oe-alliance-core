DESCRIPTION = "rss viewer for the enigma2 gui"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "python3-twisted-web python3-codecs python3-xml enigma2-plugin-extensions-mediadownloader enigma2-plugin-systemplugins-toolkit"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/SimpleRSS.git;protocol=https;branch=main"
