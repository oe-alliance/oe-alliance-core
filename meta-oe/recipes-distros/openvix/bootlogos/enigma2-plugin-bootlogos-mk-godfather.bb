SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-godfather.zip"

SRC_URI[md5sum] = "a9f65d163875bb203b33160d5f3e0a84"
SRC_URI[sha256sum] = "6639039f01dc6e8651badfb17c4be743cab8f1eeb99e1e944fc111c948e2b9eb"
