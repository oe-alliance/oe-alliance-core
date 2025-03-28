FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

inherit upx-compress

SRC_URI += "file://build-with-gcc15.patch"
