SUMMARY = "1080 bootlogo by rossi2000"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/rossi2000-openvix6-1080.zip"

SRC_URI[md5sum] = "be2ebba3856be58713d6faa79ee9aff1"
SRC_URI[sha256sum] = "d3d2ae109158d490332312ed741f8650584721a0e0c2f096381adf482739235b"
