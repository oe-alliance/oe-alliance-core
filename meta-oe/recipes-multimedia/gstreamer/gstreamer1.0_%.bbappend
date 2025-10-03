FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.26.6"
SRC_URI[sha256sum] = "42cde77277f1eee253449da213cb6e49f7fd2f792308e5579a8294c1518becac"
