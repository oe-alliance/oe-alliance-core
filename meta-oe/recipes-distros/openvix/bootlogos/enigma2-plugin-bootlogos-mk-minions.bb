SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-minions.zip"

SRC_URI[md5sum] = "b9e1d8e2c86e3cc47ddb3ff9bcad088c"
SRC_URI[sha256sum] = "59c92b8d48aad30cc73ebb02bc3a588425f9c1db0b445d609526183bbe315fc3"
