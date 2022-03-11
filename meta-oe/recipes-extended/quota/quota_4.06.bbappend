FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:sh4 = " file://fix_old_header.patch"

PACKAGE_NO_LOCALE = "1"
