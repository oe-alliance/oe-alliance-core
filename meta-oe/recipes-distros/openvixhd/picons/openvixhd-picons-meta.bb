SUMMARY = "openvixhd meta package for picon sets"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

ALLOW_EMPTY_${PN} = "1"
PV = "2.3"
PR = "r1"

DEPENDS = " \
        enigma2-plugin-picons-crokers \
"
