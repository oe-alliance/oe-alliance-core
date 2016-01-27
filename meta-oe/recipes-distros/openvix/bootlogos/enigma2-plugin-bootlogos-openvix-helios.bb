SUMMARY = "OpenViX bootlogo Helios pack1"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/openvix-helios.zip"

SRC_URI[md5sum] = "8cf095009dd1dbd84aecf19082782713"
SRC_URI[sha256sum] = "5b8d6a615a1a364b49c33d37ed0f1f406ff8dcb19c6d6c12c2cbdc54b7abe85b"