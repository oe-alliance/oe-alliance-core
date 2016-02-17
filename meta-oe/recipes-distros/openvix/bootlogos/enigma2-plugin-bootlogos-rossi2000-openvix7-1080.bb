SUMMARY = "1080 bootlogo by rossi2000"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/rossi2000-openvix7-1080.zip"

SRC_URI[md5sum] = "30d7ae8d34e435d7560dfe37ed8567c9"
SRC_URI[sha256sum] = "b4c66405b56930e89ffb848f236a256ef16f5b640acff030328fca8882dd50a5"
