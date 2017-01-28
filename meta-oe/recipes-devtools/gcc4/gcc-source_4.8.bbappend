FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " \
    file://fix-build-with-gcc-6.patch \
"

SRC_URI_append_sh4 = " \
    file://gcc-4.8.5-stm-160116.patch \
"
