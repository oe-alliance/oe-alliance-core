FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.24.8"
 
SRC_URI[md5sum] = "aed9681c3dcf4ccec6adba6e865ee9a3"
SRC_URI[sha256sum] = "b807dbf36c5d2b3ce1c604133ed0c737350f9523ce4d8d644a1177c5f9d6ded3"
