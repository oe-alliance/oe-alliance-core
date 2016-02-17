SUMMARY = "Maxwell smoke bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_Smoke.zip"

SRC_URI[md5sum] = "ca02681dd2064c4a132e35d025400b79"
SRC_URI[sha256sum] = "e762543080ba5acffc1945aa57f18020b1190ee28709b23f2bdd6f217cfcbccf"
