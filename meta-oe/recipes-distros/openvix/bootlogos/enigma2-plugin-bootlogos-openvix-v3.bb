SUMMARY = "OpenViX 3.0 bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/openvix-v3.zip"

SRC_URI[md5sum] = "b91e91d6f982a683f6cb4230f41a24a7"
SRC_URI[sha256sum] = "1fdfede19320d6c9099aaa3a2643ea06b304c9d56d23485817a79c8f172a32e7"