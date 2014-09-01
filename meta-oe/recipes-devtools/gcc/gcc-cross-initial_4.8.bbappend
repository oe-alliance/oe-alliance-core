FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_sh4 = " \
    file://gcc-4.8.2-stm-140225.patch \
"
