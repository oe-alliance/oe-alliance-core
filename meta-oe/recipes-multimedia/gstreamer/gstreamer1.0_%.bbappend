FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.24.13"
 
SRC_URI[md5sum] = "29ab3197d262d1a90692e2adeeb25a15"
SRC_URI[sha256sum] = "ed4678e1d0708db01a469ae5dd31c10cac73c0fb3f7c2c471b0d3cab0affc7d1"
