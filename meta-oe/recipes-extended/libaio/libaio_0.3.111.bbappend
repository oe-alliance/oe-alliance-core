FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_sh4 = " \
    file://00_arches_sh.patch \
"
