SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-breakingbad.zip"

SRC_URI[md5sum] = "1f5e736f8106b4a7c788a2b9fbdc2c69"
SRC_URI[sha256sum] = "f17dac61bee83d8f3246b5b5c96fa5ba5ab3cc62f65820ab8b0788d57ad6e80a"
