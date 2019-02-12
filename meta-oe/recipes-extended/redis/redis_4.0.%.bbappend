FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mipsel = " file://remove-atomics.patch"
