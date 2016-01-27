SUMMARY = "Maxwell fire stick bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_FireStick.zip"

SRC_URI[md5sum] = "477cc64e7509a014cf5818653deb1066"
SRC_URI[sha256sum] = "278810c2447b52a1c9033b5d303e3c20fb7a084f3920f30d1ca656f36e172fcb"
