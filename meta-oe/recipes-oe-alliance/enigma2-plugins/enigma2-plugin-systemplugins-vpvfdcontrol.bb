DESCRIPTION = "vfd controller"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PN = "enigma2-plugin-systemplugins-vfdcontrol"

COMPATIBLE_MACHINE = "^(sf8008|sf8008m|sfx6008|sx88v2|viper4k|xc7362|xc7358|xc7358ci|osnino|osninoplus)$"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/VPLEDControl.git;protocol=https;branch=main"
