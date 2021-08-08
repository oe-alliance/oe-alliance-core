FILESEXTRAPATHS:prepend := "${THISDIR}/util-linux:"

SRC_URI:append:sh4 = " \
    file://util-linux-random.patch \
    file://util-linux-sh4.patch \
"

SRC_URI:append:cube = " \
    file://util-linux-random.patch \
"
