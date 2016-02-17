SUMMARY = "Maxwell sound bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_sound.zip"

SRC_URI[md5sum] = "5a14abfbc38392c701c60cff9ed5f86d"
SRC_URI[sha256sum] = "2388725a6cf91e53ea16927a522422550f4735fdfda18fd1da75681c508958d0"
