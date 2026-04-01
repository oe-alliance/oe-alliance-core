DESCRIPTION = "Control your internal system fan."
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PN = "enigma2-plugin-systemplugins-fancontrol"

COMPATIBLE_MACHINE = "^(dm8000|dm800se|dm800sev2|dm500hd|vuuno|vuultimo|vusolo2|vuduo2|dags7335|dags7362|dags73625|dags7356|dags7252|triplex)$"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/TempFanControl.git;protocol=https;branch=main"
