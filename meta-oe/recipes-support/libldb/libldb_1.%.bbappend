FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_sh4 = " file://strtoull.patch"
