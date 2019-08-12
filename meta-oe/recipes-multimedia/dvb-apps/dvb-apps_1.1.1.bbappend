FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
            file://0007-util-DVBC_ANNEX_AC.patch \
            file://0008-util-add-defines-for-old-kernels.patch \
"
