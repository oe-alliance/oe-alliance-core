SUMMARY = "OpenViX bootlogo Helios pack3"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/openvix-helios3.zip"

SRC_URI[md5sum] = "b7fdb0ed77c4ac3ff516ed90e0bacd06"
SRC_URI[sha256sum] = "7ff1535ce8fda9048f016d1bb772062100a3422cf2dcc43a9bae70a2ea42d902"