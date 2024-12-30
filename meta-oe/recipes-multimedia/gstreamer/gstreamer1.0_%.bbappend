FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.24.10"
 
SRC_URI[md5sum] = "5ea216197ccb862648a377370d1ebf15"
SRC_URI[sha256sum] = "9fc45b1a332e8f812f09e95c281cd75969f6d1682d062a815db0e7bc047518fd"
