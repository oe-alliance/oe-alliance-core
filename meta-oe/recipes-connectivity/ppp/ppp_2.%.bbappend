FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:sh4 = " file://sys-linux-def-sol-netlink.patch"
