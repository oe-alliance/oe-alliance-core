FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:mipsel = "file://gcc-15-PROTOBUF_TAILCALL.patch"
