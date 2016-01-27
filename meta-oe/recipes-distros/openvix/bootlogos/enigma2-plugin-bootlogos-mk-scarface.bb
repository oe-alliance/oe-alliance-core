SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-scarface.zip"

SRC_URI[md5sum] = "afe284054621306695b3451a9ed73518"
SRC_URI[sha256sum] = "82f50cd61fbf97b5147723ace4e1d1b91355d8cec4099c42922c2a03a23b73b6"
