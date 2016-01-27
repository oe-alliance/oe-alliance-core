SUMMARY = "Maxwell abstract bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_Abstract.zip"

SRC_URI[md5sum] = "c68d58baa151aa15b39edfbd2ade280b"
SRC_URI[sha256sum] = "98ad2e5f73428c59b651ffe57c3d9e6238694532ef3455121a4353e2b920cf4c"
