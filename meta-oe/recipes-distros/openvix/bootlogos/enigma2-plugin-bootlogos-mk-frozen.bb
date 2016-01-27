SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-frozen.zip"

SRC_URI[md5sum] = "c56d0c2d8fe72e825d348bb5c8c748b8"
SRC_URI[sha256sum] = "a8cb13c9dda8382ae59b0864298673b7b56b033fd5000618177da8be93ab04c9"
