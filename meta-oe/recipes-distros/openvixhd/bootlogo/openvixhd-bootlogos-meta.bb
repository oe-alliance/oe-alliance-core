SUMMARY = "meta file for enigma2 bootlogo packages"

require conf/license/license-gplv2.inc

inherit allarch

DEPENDS = "\
    openvixhd-bootlogo-animated \
    openvixhd-bootlogo-apollo \
	enigma2-plugin-bootvideos-openvixhd-one \
	enigma2-plugin-bootvideos-openvixhd-two \
	enigma2-plugin-bootvideos-openvixhd-three \
    "

PR = "r0"
