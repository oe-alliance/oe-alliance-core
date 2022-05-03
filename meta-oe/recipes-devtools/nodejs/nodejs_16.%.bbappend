FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:mipsel = " file://fix-mips-build.patch"
