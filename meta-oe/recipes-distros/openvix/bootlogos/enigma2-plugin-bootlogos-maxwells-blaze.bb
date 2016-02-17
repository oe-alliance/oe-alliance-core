SUMMARY = "Maxwell blaze bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_Blaze.zip"

SRC_URI[md5sum] = "0a141c09b2d5ad41d2bb96f8ccde8114"
SRC_URI[sha256sum] = "2df3635de9e824061005433467fd1cfe44703faa2e18b0ae21a260784964c616"
