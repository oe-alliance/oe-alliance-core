SUMMARY = "Maxwell curves bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_curves.zip"

SRC_URI[md5sum] = "0570b7ba77e58f769eb2568c189a69fe"
SRC_URI[sha256sum] = "44cae333e1fb6cc9f3ee65b2695e836075b059bd4633caf51c3bbfddac4643fc"
