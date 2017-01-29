SUMMARY = "openatv meta package for enigma2 picon sets"
require conf/license/license-gplv2.inc
inherit packagegroup

PV = "1.0"
PR = "r1"

DEPENDS = " \
        enigma2-plugin-picons-openatv-black13e \
        enigma2-plugin-picons-openatv-black19e \
        enigma2-plugin-picons-openatv-black23e \
        enigma2-plugin-picons-openatv-black28e \
        enigma2-plugin-picons-openatv-white13e \
        enigma2-plugin-picons-openatv-white19e \
        enigma2-plugin-picons-openatv-white23e \
        enigma2-plugin-picons-openatv-white28e \
"
