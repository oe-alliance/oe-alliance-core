SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-lykanhypersport.zip"

SRC_URI[md5sum] = "cac49840ee7b6213472e257671cc2432"
SRC_URI[sha256sum] = "6a8f92c854fdf40a822e33ab3e29a69cae049335af4c8242d094ac2954d39303"
