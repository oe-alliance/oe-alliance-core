FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.28.1"
SRC_URI[sha256sum] = "b65e2ffa35bdbf8798cb75c23ffc3d05e484e48346ff7546844ba85217664504"
