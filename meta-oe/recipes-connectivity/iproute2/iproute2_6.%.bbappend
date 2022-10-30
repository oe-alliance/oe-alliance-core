inherit upx-compress

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " file://include_stdint.h_explicitly_for___kernel_ulong_t.patch"
