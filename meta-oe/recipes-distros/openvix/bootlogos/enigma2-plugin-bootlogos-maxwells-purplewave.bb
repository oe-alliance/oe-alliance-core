SUMMARY = "Maxwell purple wave bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_PurpleWave.zip"

SRC_URI[md5sum] = "aebfee8059eedfaf637c2d743bf653ea"
SRC_URI[sha256sum] = "87cdaf09e6f8fb4325046c8d0b8153f745d49dcfa00c2580b7ffadd209d5ae06"
