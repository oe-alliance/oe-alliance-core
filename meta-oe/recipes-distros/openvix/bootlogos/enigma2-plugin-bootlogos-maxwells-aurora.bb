SUMMARY = "Maxwell aurora bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_Aurora_r2.zip"

SRC_URI[md5sum] = "48c92e45d25d8e21ca44ead6e6fb63f1"
SRC_URI[sha256sum] = "0949254b912a5cf2d0049fefd3a16e3ee6edcb0dd200b762d019cab7c3cb9dc4"
