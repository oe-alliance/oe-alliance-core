FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:sh4 = " file://sh4-getrandom.patch"
