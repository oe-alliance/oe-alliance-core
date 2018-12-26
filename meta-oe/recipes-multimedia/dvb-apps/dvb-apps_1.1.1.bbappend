FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
            file://util-DVBC_ANNEX_AC.patch \
"
