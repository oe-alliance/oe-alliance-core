SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-gameofthrones.zip"

SRC_URI[md5sum] = "1fab52dc71bdcee1ed35df91c37eeffb"
SRC_URI[sha256sum] = "e41896c53e4e890cca312d662c5b6432c22fb3cde9ff31282a2a8a8c1f1b7f68"
