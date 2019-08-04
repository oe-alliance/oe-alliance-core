FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
            file://util-DVBC_ANNEX_AC.patch \
            file://0008-util-add-defines-for-old-kernels.patch \
"
