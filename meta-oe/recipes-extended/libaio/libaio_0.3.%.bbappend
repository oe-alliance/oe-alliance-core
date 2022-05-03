FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:sh4 = " \
    file://00_arches_sh.patch \
"
