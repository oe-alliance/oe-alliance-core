SUMMARY = "MK Bootlogo"

require recipes-distros/openvix/bootlogos/openvix-bootlogos.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/mk-duo2.zip"

SRC_URI[md5sum] = "f017ad2f60ecde0401039497235b8c1f"
SRC_URI[sha256sum] = "08c0191da3b90dad76b52a6a4048ede7b06908561287571452e8747f9c6cb687"
