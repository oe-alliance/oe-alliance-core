SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-tigerglow.zip"

SRC_URI[md5sum] = "431e5171d81be92b431186675e91a2cb"
SRC_URI[sha256sum] = "a377a6b9d999e1aeb0f5d76884630acb3969b872cb28a874422d9292b918a480"
