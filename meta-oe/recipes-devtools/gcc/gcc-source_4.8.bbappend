FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_sh4 = " \
    file://gcc-4.8.4-stm-150128.patch \
"
