FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://receive_timeout.patch \
    file://no_read_restart_on_eagain.patch \
"
