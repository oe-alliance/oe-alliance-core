SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-avengers.zip"

SRC_URI[md5sum] = "6b954afa107679f4e21dca52cbf6c663"
SRC_URI[sha256sum] = "3958b5e22317cf50767a415c97546a8e2a795e06732c6835e098ec4e59d466ed"
