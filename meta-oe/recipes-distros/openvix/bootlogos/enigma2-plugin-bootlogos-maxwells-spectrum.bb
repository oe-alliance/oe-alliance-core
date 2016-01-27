SUMMARY = "Maxwell spectrum bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_spectrum.zip"

SRC_URI[md5sum] = "70d5afe279a4083759c7fb3e62219659"
SRC_URI[sha256sum] = "ccf723b2936fd0752a5997453ff9a6561b45d9d7fdafb9980b12b695dfbfc853"
