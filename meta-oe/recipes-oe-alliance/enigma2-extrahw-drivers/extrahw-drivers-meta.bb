SUMMARY = "meta file for extra hardware drivers"
inherit packagegroup

require conf/license/license-gplv2.inc

DEPENDS = "\
	enigma2-plugin-drivers-extrahw-bluetooth \
	enigma2-plugin-drivers-extrahw-cardreader \
	enigma2-plugin-drivers-extrahw-keyboard \
	"
