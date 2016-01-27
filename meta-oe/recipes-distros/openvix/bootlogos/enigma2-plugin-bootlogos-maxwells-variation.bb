SUMMARY = "Maxwell variation bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_variation.zip"

SRC_URI[md5sum] = "e389c9292f9d7a74f998e41511059523"
SRC_URI[sha256sum] = "6d62ee019df4b8d4281551a80487bdce3c51a4776a6cd91328ab8844b0598223"
