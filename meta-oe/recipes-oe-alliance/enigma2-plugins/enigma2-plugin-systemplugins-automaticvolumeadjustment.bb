DESCRIPTION = "Automatically adjusts the volume level."
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
inherit gitpkgv allarch gettext setuptools3-openplugins

S = "${UNPACKDIR}/${BP}/src"

DEPENDS += " gettext-native"

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/oe-alliance-plugins/AutomaticVolumeAdjustment.git;protocol=https;branch=main"
