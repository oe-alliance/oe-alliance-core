SUMMARY = "Maxwell river bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_River.zip"

SRC_URI[md5sum] = "04bca4b298c8fcd64ff63e07480784ed"
SRC_URI[sha256sum] = "9b74e9c26ac7efb60cbfae3001322fca23ae2a424e2a78a54a01d5ca99e0a153"
