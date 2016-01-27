SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-vixsteel.zip"

SRC_URI[md5sum] = "5125fbfd9bd4e168f9bca56814ffd22d"
SRC_URI[sha256sum] = "40de8fcae55a9aed11a94ef5c8dee6ac7b944c21bb0eeb83708621edd1698dae"
