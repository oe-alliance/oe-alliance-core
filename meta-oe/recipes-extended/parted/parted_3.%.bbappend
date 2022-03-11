FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:sh4 = " file://sh4-compile-fix.patch"

PACKAGE_NO_LOCALE = "1"
