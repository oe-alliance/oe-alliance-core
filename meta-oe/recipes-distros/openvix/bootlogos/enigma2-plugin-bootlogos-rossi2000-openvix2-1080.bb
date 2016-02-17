SUMMARY = "1080 bootlogo by rossi2000"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/rossi2000-openvix2-1080.zip"

SRC_URI[md5sum] = "882f77b31de8b3c1b2ca7051777e7fc5"
SRC_URI[sha256sum] = "4bbadaebf16b2ee4e99882af687cfe7304bdeba860bdc613bdd379249726a82b"
