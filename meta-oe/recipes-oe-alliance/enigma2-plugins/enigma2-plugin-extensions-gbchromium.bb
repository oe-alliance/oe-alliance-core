DESCRIPTION = "E2 Chromium Plugin"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PN = "enigma2-plugin-extensions-chromium"

COMPATIBLE_MACHINE = "^(gb7252|gb72604|vuduo4klite)$"

RDEPENDS:${PN} = "chromium-browser-${MACHINE}"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/GigaBlueChromium.git;protocol=https;branch=main"
