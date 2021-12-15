FILESEXTRAPATHS:prepend := "${THISDIR}/patchelf:"

SRC_URI:append:sh4 = " file://sh4-readd-c11-support.patch"
