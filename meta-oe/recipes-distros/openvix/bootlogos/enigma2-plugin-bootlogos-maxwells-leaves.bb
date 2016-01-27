SUMMARY = "Maxwell leaves bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_leaves.zip"

SRC_URI[md5sum] = "7d65ec4b526b520955a18b1d3c95699e"
SRC_URI[sha256sum] = "a653b71c241c9303d182f86a1eae15e83a82efba5ded77f96a28abdc4b9d5a85"
