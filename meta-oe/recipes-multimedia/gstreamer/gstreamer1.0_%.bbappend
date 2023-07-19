FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.22.1"

SRC_URI[md5sum] = "3ed9648be775514c516c5dd8a267b27b"
SRC_URI[sha256sum] = "cd3ca759f926763615fdfcea63c9761198c42889bc0615ceec73e22b24fde771"
