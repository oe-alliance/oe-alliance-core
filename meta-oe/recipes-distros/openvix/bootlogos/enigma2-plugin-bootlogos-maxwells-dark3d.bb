SUMMARY = "Maxwell dark 3d bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_dark3d.zip"

SRC_URI[md5sum] = "a49520ef0c80828d8f0024ef01599741"
SRC_URI[sha256sum] = "7965246d6d9921f9e23a429b9042096271e215ff65f7704d834e953307a57527"
