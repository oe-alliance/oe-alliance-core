FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.28.6"
 
SRC_URI[sha256sum] = "62b6b9f0ad3147a6dd6420ac64a91180b14e990695bddd353b96041611d052ca"
