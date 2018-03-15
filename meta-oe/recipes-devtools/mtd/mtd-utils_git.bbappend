FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://mkfs.ubifs-allow-output-file-creation-on-different-device.patch \
    file://no_deatach_check.patch \
    "

