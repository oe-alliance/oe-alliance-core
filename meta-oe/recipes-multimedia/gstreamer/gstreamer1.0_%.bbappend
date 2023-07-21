FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.22.5"

SRC_URI[md5sum] = "b138307f1f84ed3a77a22a337e2af398"
SRC_URI[sha256sum] = "4408d7930f381809e85917acc19712f173261ba85bdf20c5567b2a21b1193b61"
