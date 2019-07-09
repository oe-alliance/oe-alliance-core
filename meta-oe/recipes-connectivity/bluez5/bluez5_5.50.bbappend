FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
    file://0001-tools-Add-support-for-rtk_h5-type.patch \
    file://tools-Fix-build-after-y2038-changes-in-glibc.patch \
"
