FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.22.6"

SRC_URI[md5sum] = "62caa2c16550ea444f5d0eb09e0851f3"
SRC_URI[sha256sum] = "f500e6cfddff55908f937711fc26a0840de28a1e9ec49621c0b6f1adbd8f818e"
