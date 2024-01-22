FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.22.8"

SRC_URI[md5sum] = "d5bc4ceaeec93442534a14ccb5e8f845"
SRC_URI[sha256sum] = "ad4e3db1771139b1db17b1afa7c05db083ae0100bd4da244b71f162dcce41bfc"
