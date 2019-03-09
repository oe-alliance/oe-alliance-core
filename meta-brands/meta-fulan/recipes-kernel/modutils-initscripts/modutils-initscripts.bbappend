SRC_URI_append_sh4 += "\
    file://spark_modutils.patch \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
