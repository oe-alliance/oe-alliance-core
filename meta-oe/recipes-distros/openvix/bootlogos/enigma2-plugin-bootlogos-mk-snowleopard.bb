SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-snowleopard.zip"

SRC_URI[md5sum] = "1989f78833e25d409691a0c8d2aa0a1a"
SRC_URI[sha256sum] = "aedc8ce3aecc651bff66005cf41a49a0b44f4a1de8514c417889497508d62bd4"
