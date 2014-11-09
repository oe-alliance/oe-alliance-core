SUMMARY = "dreamplex-hd meta package for skin sets"
LICENSE = "MIT"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r6"

inherit packagegroup

DEPENDS = "enigma2 enigma2-plugin-skinpacks-dreamplex-youvix"

RDEPENDS_${PN} = "\
    enigma2-plugin-skinpacks-dreamplex-youvix-red \
	enigma2-plugin-skinpacks-dreamplex-youvix-green \
	enigma2-plugin-skinpacks-dreamplex-youvix-darkblue \
    "
