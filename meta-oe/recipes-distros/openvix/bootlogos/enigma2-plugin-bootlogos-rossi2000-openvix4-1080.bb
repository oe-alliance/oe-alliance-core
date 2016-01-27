SUMMARY = "1080 bootlogo by rossi2000"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/rossi2000-openvix4-1080.zip"

SRC_URI[md5sum] = "651f2f47b4152eae63ab3935291d2837"
SRC_URI[sha256sum] = "dcba7c454a2ab34b8192fb79a006beb4584858b108fae836f90cbf38ffabf014"
