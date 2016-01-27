SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-goldenplate.zip"

SRC_URI[md5sum] = "2a9fc1eccd51cbacae0904ee82f13df9"
SRC_URI[sha256sum] = "02abb69a5b8d658008d7a670484e94d697e0547d54fcee63be95fd0b3b5a150d"
