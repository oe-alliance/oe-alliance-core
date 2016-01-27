SUMMARY = "1080 bootlogo by rossi2000"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/rossi2000-openvix1-1080.zip"

SRC_URI[md5sum] = "5e83afaef1fa5abd7799996bb3ef32b2"
SRC_URI[sha256sum] = "43a957a1e6386b32fae503a3fe111fe847d65829c399496d26d77efc2fd2193e"
