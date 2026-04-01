DESCRIPTION = "E2 Chromium Plugin"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PN = "enigma2-plugin-extensions-chromium"

COMPATIBLE_MACHINE = "^(vusolo4k|vuuno4k|vuzero4k|vuuno4kse|vuultimo4k|vuduo4k|vuduo4kse)$"

RDEPENDS:${PN} = "chromium-browser-${MACHINE}"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/VuChromium.git;protocol=https;branch=main"
