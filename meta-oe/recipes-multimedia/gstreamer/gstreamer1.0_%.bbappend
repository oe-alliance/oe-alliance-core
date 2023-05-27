FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.22.3"

SRC_URI[md5sum] = "f812605a25fd38a7a36ee4a2d7a0ce28"
SRC_URI[sha256sum] = "9ffeab95053f9f6995eb3b3da225e88f21c129cd60da002d3f795db70d6d5974"
