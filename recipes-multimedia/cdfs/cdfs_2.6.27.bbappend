FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PRINC = "1"

SRC_URI_append = " file://add-3.8-support.patch"