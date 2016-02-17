SUMMARY = "Maxwell earth bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_Earth.zip"

SRC_URI[md5sum] = "fd6bf848863a636316fbcdc11e791661"
SRC_URI[sha256sum] = "a6b99ce13f9987bb41dfb76f9f70747a088a54da09be8831806fe02d456769bc"
