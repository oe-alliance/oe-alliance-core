DESCRIPTION = "Satip Client setup"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "satipclient"
DEPENDS = "satipclient"
REPLACES:${PN} = "enigma2-plugin-extensions-satipclient"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/SatipClient.git;protocol=https;branch=main"
