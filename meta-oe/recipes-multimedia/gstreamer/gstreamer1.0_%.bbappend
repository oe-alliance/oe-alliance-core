FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.22.9"

SRC_URI[md5sum] = "7468426fda993b466fc49f1799c34a1d"
SRC_URI[sha256sum] = "1e7124d347e8cdc80f08ec1d370c201be513002af1102bb20e83c5279cb48ebd"
