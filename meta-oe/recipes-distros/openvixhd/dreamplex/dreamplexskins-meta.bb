SUMMARY = "dreamplex meta package for skin sets"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

ALLOW_EMPTY_${PN} = "1"
PV = "1.0"
PR = "r0"

DEPENDS = " \
        enigma2-plugin-dreamplex-youvix-blue \
"
