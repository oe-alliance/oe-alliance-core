SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-tiger.zip"

SRC_URI[md5sum] = "86e06f8de4b534847efd37ec793b47c8"
SRC_URI[sha256sum] = "e6186a6843b465ed3f0abe352ff3f60dc14be5ea619ff52320b31a4f102e1401"
