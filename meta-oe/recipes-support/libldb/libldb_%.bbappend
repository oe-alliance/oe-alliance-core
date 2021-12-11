FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:sh4 = " file://remove-rep_strtoull.patch"
