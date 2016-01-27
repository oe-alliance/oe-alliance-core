SUMMARY = "Maxwell constellation bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_Constellation.zip"

SRC_URI[md5sum] = "d3252fb5251eaeb5f95ff2b5cd8dfbfa"
SRC_URI[sha256sum] = "3995d88eea7bb19513a30d6c0a6f5d55fd8c8ada50bb2fdf901c45ef5968abb3"
