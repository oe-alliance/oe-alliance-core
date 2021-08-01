FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
            file://0007-util-DVBC_ANNEX_AC.patch \
            file://0008-util-add-defines-for-old-kernels.patch \
"
