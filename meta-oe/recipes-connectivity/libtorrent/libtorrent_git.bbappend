FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = "\
    file://0001-Define-64bit-atomic-helpers-for-sh4.patch \
"

