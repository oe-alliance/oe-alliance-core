FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
           file://0001-revert-use-new-gst-adapter-get-buffer.patch \
"
