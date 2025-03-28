FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"

SRC_URI += "file://build-with-gcc15.patch"
