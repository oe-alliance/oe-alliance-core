DESCRIPTION = "Extension for enigma2 webinterface to sort bouquets and other"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "enigma2-plugin-extensions-webinterface"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/WebBouquetEditor.git;protocol=https;branch=main"
