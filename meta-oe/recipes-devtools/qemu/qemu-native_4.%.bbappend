FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-siocgstamp-errors-with-5.2.x-host-kernels.patch"

CFLAGS += "-Wno-stringop-truncation"
