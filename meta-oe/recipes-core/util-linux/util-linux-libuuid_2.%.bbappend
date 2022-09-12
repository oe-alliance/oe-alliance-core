FILESEXTRAPATHS:prepend := "${THISDIR}/util-linux:"

SRC_URI:append:cube = " \
    file://util-linux-random.patch \
"
