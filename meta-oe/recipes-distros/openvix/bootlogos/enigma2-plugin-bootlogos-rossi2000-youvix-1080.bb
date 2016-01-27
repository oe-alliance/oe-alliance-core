SUMMARY = "1080 bootlogo by rossi2000"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/rossi2000-youvix-1080.zip"

SRC_URI[md5sum] = "c2751d7c29ddb2d9829d03d8b9b920d3"
SRC_URI[sha256sum] = "bff90177ff32a4509ed7180a641c2f197f09221d72a8611bace5b69b286608da"
