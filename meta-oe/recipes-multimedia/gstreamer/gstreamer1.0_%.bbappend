FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.22.4"

SRC_URI[md5sum] = "04e1a3ce2a5b107b67892a7c7de5b326"
SRC_URI[sha256sum] = "11cb0498bc16b93d8b99d22f75f829b8d0abfd8254840b2120618db5532dc655"
