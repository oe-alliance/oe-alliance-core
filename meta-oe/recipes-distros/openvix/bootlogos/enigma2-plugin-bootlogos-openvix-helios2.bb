SUMMARY = "OpenViX bootlogo Helios pack2"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/openvix-helios2.zip"

SRC_URI[md5sum] = "5a8d2b3bc06770a5820ca1858086c2be"
SRC_URI[sha256sum] = "cc654ea13f7873f2e06aedfd859c42c00f466618e6279c7343c3c3a73232d3b9"