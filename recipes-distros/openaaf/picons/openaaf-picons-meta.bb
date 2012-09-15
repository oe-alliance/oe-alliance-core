DESCRIPTION = "openaaf meta package for enigma2 picon sets"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

ALLOW_EMPTY_${PN} = "1"
PV = "1.0"
PR = "r0"

DEPENDS = " \
		enigma2-plugin-picons-openaaf-black13e \
		enigma2-plugin-picons-openaaf-black19e \
		enigma2-plugin-picons-openaaf-black23e \
		enigma2-plugin-picons-openaaf-black28e \
		enigma2-plugin-picons-openaaf-white13e \
		enigma2-plugin-picons-openaaf-white19e \
		enigma2-plugin-picons-openaaf-white23e \
		enigma2-plugin-picons-openaaf-white28e \
"
