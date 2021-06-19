FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_sh4 = " file://0001-sh4-fix-getrandom-compile.patch"
