DESCRIPTION = "Download and install new Picons for your current bouquet channels. PiconsUpdater coded by svox and jbleyel, idea by arn354 and picons by mike99"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "python3-pillow python3-io python3-compression pngquant"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/PiconsUpdater.git;protocol=https;branch=main"
