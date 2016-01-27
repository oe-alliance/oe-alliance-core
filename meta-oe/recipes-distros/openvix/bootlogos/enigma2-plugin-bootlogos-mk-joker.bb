SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-joker.zip"

SRC_URI[md5sum] = "ff7f8f205a826bb68a32a712558b2f0c"
SRC_URI[sha256sum] = "1a683fdd2ed168425565963284d1e571bfb23f64fe6f80d7ca056d1ed9541d98"
