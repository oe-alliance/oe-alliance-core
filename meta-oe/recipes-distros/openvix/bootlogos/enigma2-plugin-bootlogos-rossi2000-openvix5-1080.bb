SUMMARY = "1080 bootlogo by rossi2000"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/rossi2000-openvix5-1080.zip"

SRC_URI[md5sum] = "03d3dd3eca80d54f75737343fcfa5ce2"
SRC_URI[sha256sum] = "22f834ff4dd83b930531e21df2d4973f9d6985366e7acd7dd128cc461f127fbb"
