FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:sh4 = " file://0001-sh4-fix-getrandom-compile.patch"
