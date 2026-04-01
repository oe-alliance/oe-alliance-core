DESCRIPTION = "Change Remote Control Code"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

PN = "enigma2-plugin-systemplugins-remotecontrolcode"

COMPATIBLE_MACHINE = "^(vusolo|vuultimo|vuuno|vuduo2|vusolo2|vusolose|vuzero|vusolo4k|vuultimo4k|vuuno4k|vuuno4kse|vuzero4k|vuduo4k|vuduo4kse)$"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/VuRemote.git;protocol=https;branch=main"
