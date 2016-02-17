SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-terminatorgenisys.zip"

SRC_URI[md5sum] = "74bb4668d31c7556185b2b9192927f83"
SRC_URI[sha256sum] = "2a9d81e0fd5cfdba54624a51ca462cd9b8062786fce31d028f51e0063d0d5887"
