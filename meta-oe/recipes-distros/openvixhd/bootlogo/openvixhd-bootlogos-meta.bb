SUMMARY = "meta file for enigma2 bootlogo packages"

require conf/license/license-gplv2.inc

inherit allarch

DEPENDS = "\
    openvixhd-bootlogo-animated \
    openvixhd-bootlogo-apollo \
    "

PR = "r0"
