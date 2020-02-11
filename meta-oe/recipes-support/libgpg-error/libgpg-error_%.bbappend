FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_sh4 = " file://libgpg-error-1.35-gawk5-support.patch"
