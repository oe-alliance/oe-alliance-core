SUMMARY = "Maxwell iceberg bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_Iceberg.zip"

SRC_URI[md5sum] = "a0bcfd55225f5194bc1f6383ae964177"
SRC_URI[sha256sum] = "8fc7711d2ec4b7d57e5afa00aca3457c0fd5f23c1cc534753e1e82a89628e3d7"
