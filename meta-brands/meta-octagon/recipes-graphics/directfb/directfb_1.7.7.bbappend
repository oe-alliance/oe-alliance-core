FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += " \
    file://input_for_hbbtv.patch \
"

EXTRA_OECONF += "\
    --with-inputdrivers=linuxinput \
    --disable-video4linux \
    --without-tools \
"
