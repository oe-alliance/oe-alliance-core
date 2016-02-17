SUMMARY = "Maxwell ribbons bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/Maxwells_Ribbons.zip"

SRC_URI[md5sum] = "7a625e6728c2fbd906766028f75d1cea"
SRC_URI[sha256sum] = "7cd2b36d689d2333944c7191e6eb2aa3acdde997cb75e130976e161e6514a9f2"
