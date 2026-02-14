FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.28.0"
SRC_URI[sha256sum] = "6c8676bc39a2b41084fd4b21d2c37985c69ac979c03ce59575db945a3a623afd"
