FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= ".3"

SRC_URI_append = " \
    file://0001-tools-Add-support-for-rtk_h5-type.patch \
    file://0001-hciattach-add-QCA9377-Tuffello-support.patch \
"
