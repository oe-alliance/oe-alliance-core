FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_sh4 += "\
    file://define-RTNL_FAMILY_MAX.patch \
"
