FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_sh4 = "\
    file://revert_new_hw_offload_mode.patch \
"

