DESCRIPTION = "this is dlna/upnp browser using djmount"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "djmount fuse-utils fuse libupnp1.6 neon"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/DLNABrowser.git;protocol=https;branch=main"
