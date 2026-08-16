FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI:append:octagon = " \
    file://input_for_hbbtv.patch \
"

EXTRA_OECONF:append:octagon = "\
    --with-inputdrivers=linuxinput \
    --disable-video4linux \
    --without-tools \
"
