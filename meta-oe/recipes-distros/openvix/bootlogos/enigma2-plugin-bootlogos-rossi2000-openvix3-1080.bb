SUMMARY = "1080 bootlogo by rossi2000"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/rossi2000-openvix3-1080.zip"

SRC_URI[md5sum] = "7f31ab360732ad7a7fbbca9bfb179a0b"
SRC_URI[sha256sum] = "009bc214029bf764dd3e3e4d9b00740cb83f3f425d762ec9dffa95f4b413e7e1"
