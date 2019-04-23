FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

SRC_URI_append_sh4 = "file://fix_old_header.patch"
