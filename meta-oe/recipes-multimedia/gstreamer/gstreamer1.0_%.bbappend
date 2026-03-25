FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.26.11"
SRC_URI[sha256sum] = "2e0bd192d0438ea606a6f76a95c8e16542167656ffec2c2bc3aaf6ee0837fbf6"
