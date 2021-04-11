FILESEXTRAPATHS_prepend := "${THISDIR}/util-linux:"

SRC_URI_append_sh4 = " \
    file://util-linux-random.patch \
    file://util-linux-sh4.patch \
"

SRC_URI_append_cube = " \
    file://util-linux-random.patch \
"
