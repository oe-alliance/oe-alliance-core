SUMMARY = "OpenViX bootlogo Zeus"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/openvix-zeus.zip"

SRC_URI[md5sum] = "25bb4a9526350cfaa28cfa9fe288eebe"
SRC_URI[sha256sum] = "30b68be08383209f831aa035df8acb0da2500e36f9c8d2ef786d049a7dadacd3"