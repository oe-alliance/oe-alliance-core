FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_sh4 = " file://sys-linux-def-sol-netlink.patch"
