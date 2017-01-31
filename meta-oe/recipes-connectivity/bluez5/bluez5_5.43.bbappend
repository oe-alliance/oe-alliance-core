FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= ".2"

SRC_URI_append = " file://0001-tools-Add-support-for-rtk_h5-type.patch"

